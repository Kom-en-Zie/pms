# syntax=docker/dockerfile:1
#
# Multi-target build for the whole stack. The build context is the repository root
# because both images depend on the :shared module.
#
#   docker build --target backend  -t pms-backend  .
#   docker build --target frontend -t pms-frontend .
#
# Normally you do not call this directly: `docker compose up --build` builds both.

ARG JDK_IMAGE=eclipse-temurin:25-jdk-noble
ARG JRE_IMAGE=eclipse-temurin:25-jre-noble
ARG NGINX_IMAGE=nginxinc/nginx-unprivileged:1.30-alpine

# ----------------------------------------------------------------------------
# Sources (shared by both build stages)
# ----------------------------------------------------------------------------
FROM ${JDK_IMAGE} AS source
WORKDIR /workspace
COPY gradlew settings.gradle.kts build.gradle.kts gradle.properties ./
COPY gradle/ gradle/
COPY kotlin-js-store/ kotlin-js-store/
COPY shared/ shared/
COPY backend/ backend/
COPY composeApp/ composeApp/

# ----------------------------------------------------------------------------
# Backend: Quarkus fast-jar on a JRE
# ----------------------------------------------------------------------------
FROM source AS backend-build
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon :backend:quarkusBuild

FROM ${JRE_IMAGE} AS backend
RUN useradd --system --uid 10001 --no-create-home --shell /usr/sbin/nologin pms
WORKDIR /app
# Copied in four layers so dependency changes do not invalidate the application layer.
COPY --from=backend-build --chown=pms /workspace/backend/build/quarkus-app/lib/ ./lib/
COPY --from=backend-build --chown=pms /workspace/backend/build/quarkus-app/*.jar ./
COPY --from=backend-build --chown=pms /workspace/backend/build/quarkus-app/app/ ./app/
COPY --from=backend-build --chown=pms /workspace/backend/build/quarkus-app/quarkus/ ./quarkus/
USER pms
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "-jar", "quarkus-run.jar"]

# ----------------------------------------------------------------------------
# Frontend: Compose Multiplatform (Wasm) static bundle served by nginx
# ----------------------------------------------------------------------------
FROM source AS frontend-build
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon :composeApp:wasmJsBrowserDistribution

FROM ${NGINX_IMAGE} AS frontend
COPY docker/nginx/default.conf /etc/nginx/conf.d/default.conf
COPY docker/nginx/security-headers.conf /etc/nginx/snippets/security-headers.conf
COPY --from=frontend-build /workspace/composeApp/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html/
EXPOSE 8080
