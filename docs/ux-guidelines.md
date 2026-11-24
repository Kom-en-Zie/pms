# UX & UI guidelines

PMS is used by volunteers of the Kom en Zie technical teams, often on a phone, often in a hurry,
and sometimes in a dimmed auditorium during a service. Two goals drive every design decision:

1. **Simplicity.** Someone who opens PMS once a month should get their task done without help.
2. **Mobile first.** Design for a phone, then make good use of the extra space on tablets and
   desktops. Desktop is still a first-class experience, not an afterthought.

## Principles

- **One primary action per screen.** Make it visually obvious (filled button or FAB). Secondary
  actions go in overflow menus or detail screens.
- **Fewer fields, smart defaults.** Only ask for what is needed now. Pre-fill what we can know
  (today's date, the current user, the last used value).
- **Plain language.** Use the words the team uses, not database or technical terms. Short labels,
  and no jargon in error messages: say what happened and what to do next.
- **Forgiving.** Prefer undo (snackbar) over "Are you sure?" dialogs. Only confirm actions that
  are destructive and cannot be undone.
- **Always show state.** Every screen has designed loading, empty, error and offline states.
- **Consistent.** Reuse components from `nl.komenzie.pms.app.designsystem` rather than styling
  one-offs.

## Layout: mobile first, adaptive

Use **Material 3 window size classes** (`currentWindowAdaptiveInfo()` from
`material3-adaptive`) to adapt the layout, never the platform. A narrow desktop window should look
like a phone.

| Width class | Breakpoint | Typical device | Layout |
|-------------|------------|----------------|--------|
| Compact     | < 600 dp   | Phone (portrait) | Single pane, **bottom navigation bar** |
| Medium      | 600–839 dp | Tablet, foldable, small window | Single or two panes, **navigation rail** |
| Expanded+   | ≥ 840 dp   | Desktop, large tablet | **List-detail** two panes, navigation rail or drawer |

- Use `NavigationSuiteScaffold` (`material3-adaptive-navigation-suite`). It switches between bottom
  bar and rail automatically.
- Keep **3 to 5 top-level destinations**.
- On large screens, use the space for **more context** (list + detail side by side), not for
  stretched forms. Limit text and form content to a readable width (about 600–700 dp).
- Design every screen at **360 × 640 dp** first. It must work there without horizontal scrolling.

## Touch & interaction

- Touch targets are at least **48 × 48 dp**.
- Put primary actions **within thumb reach** (bottom of the screen) on compact layouts.
- Support **keyboard and mouse** on desktop: tab order, Enter to submit, Esc to close dialogs,
  hover states, and right-click where it is natural.
- Never rely on gestures alone (swipe to delete) without a visible alternative.

## Visual design

- **Material 3** (`compose-material3`) is the component library and design language.
- Support **light and dark themes**, following the system setting. Dark mode is not a luxury
  for a team that often works in a dark auditorium.
- Colour contrast meets **WCAG 2.2 AA**: 4.5:1 for text, 3:1 for large text and UI components.
- Never use colour as the only signal. Pair it with an icon or text (e.g. status chips).
- Use the Material type scale. Don't set custom font sizes in screens.

## Accessibility

- Every icon-only button has a `contentDescription`.
- Text scales with the user's font size setting. Test at 200%.
- Make semantics explicit for custom components (`Modifier.semantics`).
- Show focus clearly for keyboard users.

## Language & text

- UI text lives in Compose resources (`composeResources/values/strings.xml`), never hard-coded.
- The default UI language is **Dutch**, since the users are Dutch-speaking volunteers. Add other
  languages as `values-<lang>/strings.xml` when needed.
- Format dates, times and numbers for the user's locale.

## Performance on mobile

- The web app's first load downloads the Wasm runtime, which is several MB compressed. Later
  visits use the browser cache. Keep the first screen lightweight and show progress quickly.
- Avoid loading large lists in one go. Page or lazy-load (`LazyColumn`).
