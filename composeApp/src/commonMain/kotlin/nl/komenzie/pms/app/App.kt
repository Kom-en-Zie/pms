package nl.komenzie.pms.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.komenzie.pms.app.resources.Res
import nl.komenzie.pms.app.resources.app_name
import org.jetbrains.compose.resources.stringResource

/**
 * Root composable shared by every client platform.
 *
 * Placeholder only: theming, navigation and screens are added during implementation
 * (see docs/ux-guidelines.md).
 */
@Composable
fun App() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize().safeContentPadding(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }
    }
}
