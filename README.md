# Self-Healing Demo Android App

A native Android e-commerce application built with Jetpack Compose to demonstrate BrowserStack's Self-Healing agent capabilities.

## Overview

This app is designed specifically to showcase how BrowserStack's Self-Healing agent can automatically detect and adapt to changed element locators during automated testing. The app includes a toggle feature that changes UI element identifiers at runtime, simulating real-world scenarios where element IDs change during development.

## Features

### Login Screen
- **Quick Select User Dropdown**: Pre-populated with 3 demo users
  - demo1@example.com
  - demo2@example.com
  - testuser@example.com
- **Auto-fill Functionality**: Email and password fields auto-populate when a user is selected
- **Password Visibility Toggle**: Show/Hide password text
- **Clean Material Design**: Card-based layout with rounded corners and elevation

### Products Home Screen
- **Top Navigation Bar**: BrowserStack logo, Home title, and Logout button
- **Search Functionality**: Real-time product search
- **Product Grid**: Scrollable list of 6 products
  - Google Pixel 4 ($499.99) - Android
  - iPhone 11 ($599.99) - iOS
  - iPhone 12 Pro ($899.99) - iOS
  - Samsung Galaxy Note 20 ($749.99) - Android
  - OnePlus 6T ($399.99) - Android
  - OnePlus 8 ($549.99) - Android
- **Product Cards** with:
  - Product name and platform badge
  - Star rating and review count
  - Description
  - Price
  - Quantity selector
  - Add to Cart button

### Self-Healing Demo Mode Toggle

**This is the key feature for demonstrating BrowserStack's Self-Healing agent.**

- **Toggle Bar**: Visible orange bar at the top of the app
- **Real-time Switching**: Changes all element testTags when toggled
- **Original Mode**: Elements have standard identifiers (e.g., `sign_in_button`, `email_input`)
- **Demo Mode Active**: Elements get alternate identifiers (e.g., `sign_in_button_demo`, `email_input_demo`)
- **Visual Indicators**: When demo mode is enabled, the login screen shows:
  - An orange banner stating "Self-Heal mode active - Elements are modified"
  - Individual notifications for each changed element showing the ID transformation
  - Example: "Healing Demo: Email element Id changed from 'email_input' to 'email_input_demo'"

This simulates a scenario where developers rename element IDs, which would normally break automated tests. The Self-Healing agent can detect these changes and update test scripts automatically.

## Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM pattern with ViewModel
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36 (Android 14)
- **State Management**: StateFlow for reactive demo mode state

## Project Structure

```
app/src/main/java/com/example/self_healdemoapplication/
├── data/
│   ├── User.kt              # User data model and demo users
│   └── Product.kt           # Product data model and sample products
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt   # Login UI with dropdown and auto-fill
│   │   └── ProductsHomeScreen.kt  # Products list with search
│   └── theme/
│       ├── Color.kt         # App color palette
│       ├── Theme.kt         # Material 3 theme configuration
│       └── Type.kt          # Typography definitions
├── viewmodel/
│   └── SelfHealViewModel.kt # State management for demo mode
└── MainActivity.kt          # Main entry point with demo toggle
```

## Color Scheme

Following the design specifications:
- **Primary (Dark Navy)**: #1E1E2E - Navigation bars and buttons
- **Secondary (Orange Accent)**: #FF7043 - CTA buttons and demo mode bar
- **Background**: #F5F5F5 - Light gray background
- **Platform Blue**: #2196F3 - Platform badges
- **Text**: Black (#000000) for headers, Gray (#757575) for secondary text

## Test Tags for Automation

All interactive elements have unique testTag identifiers that support the self-healing demo:

### Login Screen
| Element | Normal Tag | Demo Mode Tag |
|---------|-----------|---------------|
| User Dropdown | `user_dropdown` | `user_dropdown_demo` |
| Email Input | `email_input` | `email_input_demo` |
| Password Input | `password_input` | `password_input_demo` |
| Password Toggle | `password_toggle` | `password_toggle_demo` |
| Sign In Button | `sign_in_button` | `sign_in_button_demo` |

### Products Home Screen
| Element | Normal Tag | Demo Mode Tag |
|---------|-----------|---------------|
| Navigation Bar | `navigation_bar` | `navigation_bar_demo` |
| Logout Button | `logout_button` | `logout_button_demo` |
| Search Bar | `search_bar` | `search_bar_demo` |
| Products List | `products_list` | `products_list_demo` |
| Product Card | `product_card_{id}` | `product_card_{id}_demo` |
| Add to Cart | `add_to_cart_{id}` | `add_to_cart_{id}_demo` |

### Demo Mode Toggle
| Element | Tag |
|---------|-----|
| Demo Mode Bar | `demo_mode_bar` |
| Toggle Switch | `demo_mode_switch` |

## Appium UiAutomator2 Support

This app is **fully configured for Appium testing with UiAutomator2 driver**.

The app enables `testTagsAsResourceId = true`, which means all `testTag` identifiers are exposed as standard Android resource IDs that Appium can access using `driver.findElement(AppiumBy.id("..."))`.

**See [APPIUM_GUIDE.md](APPIUM_GUIDE.md) for complete Appium testing instructions with code examples.**

## How to Use for Self-Healing Demo

1. **Run the App**: Launch on an Android device or emulator
2. **Run Automated Tests**: Execute your Appium/BrowserStack test suite targeting the normal element IDs
3. **Enable Demo Mode**: Toggle the switch at the top of the app
4. **Watch Self-Healing**: Run tests again - BrowserStack's Self-Healing agent should detect the changed IDs and adapt automatically
5. **Verify Results**: Check BrowserStack dashboard to see self-healing events logged

## Building the App

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run on connected device
./gradlew installDebug

# Build project
./gradlew build
```

The APK files will be generated in:
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

## Demo User Credentials

All demo users have the same password: `password123`

- demo1@example.com
- demo2@example.com
- testuser@example.com

## Future Enhancements

While this is a demo app, potential enhancements could include:
- Product images (currently using placeholder boxes)
- Shopping cart functionality
- Checkout flow
- More complex self-healing scenarios
- Additional UI element variations

## License

This is a demonstration application for BrowserStack's Self-Healing agent.

## Support

For questions about BrowserStack's Self-Healing capabilities, visit [BrowserStack Documentation](https://www.browserstack.com/docs).
