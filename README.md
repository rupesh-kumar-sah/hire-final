# HireNepal (Android · Kotlin + Jetpack Compose)

HireNepal connects homeowners and businesses across Kathmandu, Lalitpur, Bhaktapur, and Kirtipur with verified local professionals for painting, plumbing, electrical, carpentry, and home cleaning services.

## Architecture & Tech Stack
- **UI**: Jetpack Compose + Material Design 3 (`HireNepalTheme` with custom `DM Sans` and `Manrope` typography)
- **State Management**: MVVM with `HireNepalViewModel` and `StateFlow`
- **Build System**: Gradle (Kotlin DSL) targeting Android SDK 36

## Core Features
- **Authentication & Role Routing**: Sign in, 2-step registration for Customers and Workers, and 2-question security challenge for Super Admins.
- **Customer Experience**: Location switcher, notifications sheet, service category discovery, verified professional directory with filters/sorting/saved favourites, detailed professional profiles, multi-step service booking with Android Photo Picker support, live chat with auto-reply,booking timeline/rescheduling/cancellation, and Hiré Wallet top-ups.
- **Worker & Admin Dashboards**: Real-time worker availability toggle, upcoming jobs, earnings overview, and Super Admin operations portal with worker verification approval and booking analytics.
