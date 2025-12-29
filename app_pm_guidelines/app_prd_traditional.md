# PRD: Self-Healing Demo App - Traditional Android Views Version

## Description
A native Android e-commerce app built with **traditional Android Views (XML layouts)** demonstrating BrowserStack's Self-Healing agent. Features a visible toggle to switch element IDs at runtime, simulating locator changes for automated test healing.

**This version uses traditional Android Views instead of Jetpack Compose for better Appium UiAutomator2 compatibility.**

## Why Traditional Views?

### Key Benefits
- **Direct Appium compatibility** - Works with standard UiAutomator2 driver without any special configuration
- **Standard resource IDs** - Element IDs are properly exposed through Android's view hierarchy
- **No driver switching needed** - No need to use Espresso driver or Compose-specific settings
- **BrowserStack friendly** - Standard Android automation works out of the box
- **Better debugging** - Layout Inspector and Hierarchy Viewer work seamlessly

### Technical Differences from Compose Version
| Aspect | Compose Version | Traditional Views Version |
|--------|----------------|---------------------------|
| **UI Framework** | Jetpack Compose | XML Layouts + ViewBinding |
| **State Management** | StateFlow | LiveData |
| **Navigation** | Compose Navigation | Fragment Transactions |
| **Element IDs** | testTag (requires testTagsAsResourceId) | accessibility identifiers via ViewCompat |
| **Appium Driver** | Requires Espresso driver | Works with UiAutomator2 |
| **ID Access** | `driver.setSetting("driver", "compose")` | Standard `AppiumBy.id()` |

---

## Self-Healing Demo Mode

**Toggle Bar (visible at top of all screens):**
- "Self-Heal Mode" switch with status indicator
- When enabled: Element accessibility identifiers change from `element_name` to `element_name_modified`
- Orange notification banners show which element IDs have changed
- Configurable via `HealingElement` enum: SELECT_USER, EMAIL, PASSWORD, ALL, NONE

**Implementation:**
- Uses `ViewCompat.setAccessibilityPaneTitle()` to set accessibility identifiers that Appium can find
- Dynamic ID switching happens in real-time when toggle is activated
- ViewModel manages state using LiveData

---

## App Screens & Features

### 1. Login Screen

**Features:**
- Self-Heal Mode toggle bar (at top)
- "Quick Select User" dropdown (Spinner) with pre-populated demo users:
    - demo1@example.com
    - demo2@example.com
    - testuser@example.com
- Auto-fill email and password fields when user selected from dropdown
- Email input field (EditText, hint: "Enter your email")
- Password input field (EditText, hint: "Enter your password")
- "Sign In" button (full-width, dark background)
- "Welcome Back" header with subtitle "Sign in to your account"
- Healing notifications appear above modified elements when demo mode active

**Layout File:** `fragment_login.xml`

**Screenshots:**

Login Screen - Default State:
![Login Screen](./images/login_screen.png)

Login Screen - Dropdown Open:
![Login Dropdown](./images/login_dropdown.png)

---

### 2. Products Home Screen

**Features:**
- Self-Heal Mode toggle bar (at top)
- **Top Navigation Bar:**
    - "Home" title (center)
    - Logout button (right)
- **Product List (RecyclerView with vertical scroll):**
    - Product image (ImageView)
    - Product name
    - Platform badge (Android/iOS with color coding)
    - Star rating with review count
    - Product description
    - Price
    - Quantity selector (+/- buttons)
    - "Add to Cart" button
- **Sample Products:**
    - Google Pixel 4 ($499.99) - Android
    - iPhone 11 ($599.99) - iOS
    - iPhone 12 Pro ($899.99) - iOS
    - Samsung Galaxy Note 20 ($749.99) - Android
    - OnePlus 6T ($399.99) - Android
    - OnePlus 8 ($549.99) - Android

**Layout Files:**
- `fragment_products.xml` (main layout)
- `item_product.xml` (RecyclerView item)

**Screenshot:**

Products Home Screen:
![Products Home](./images/products_home.png)

---

## Design & Style Guidelines

### Color Palette
- **Primary:** Dark navy (#1E1E2E) for navigation bar and buttons
- **Background:** White (#FFFFFF)
- **Text:** Black for headers, gray (#757575) for secondary text
- **Accent:** Orange (#FF7043) for toggle bar and notifications
- **Platform Badges:** Android Green (#4CAF50), iOS Blue (#007AFF)

### Typography
- **Headers:** Bold, large font (24sp)
- **Body:** Regular, 14-16sp
- **Buttons:** Medium weight, 16sp

### Components
- Material Design components (TextInputLayout, MaterialButton)
- Rounded corners on cards (8dp radius)
- CardView with elevation for product cards
- ConstraintLayout for flexible layouts
- ScrollView for scrollable content

---

## Technical Stack

### Platform
- **Native Android app** (Traditional Views)
- **Minimum SDK:** API 24 (Android 7.0)
- **Target SDK:** API 34 (Android 14)

### Key Libraries & Components

```kotlin
// View System
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("com.google.android.material:material:1.9.0")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")

// View Binding
buildFeatures {
    viewBinding = true
}

// Architecture Components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
implementation("androidx.fragment:fragment-ktx:1.6.1")

// RecyclerView & CardView
implementation("androidx.recyclerview:recyclerview:1.3.1")
implementation("androidx.cardview:cardview:1.0.0")
```

### Architecture

**MVVM Pattern:**
- **Model:** Data classes (`Product`, `User`, `Platform` enum)
- **View:** XML layouts + Fragments (`LoginFragment`, `ProductsFragment`)
- **ViewModel:** `SelfHealViewModel` (manages demo mode state with LiveData)

**File Structure:**
```
app/src/main/
├── java/com/example/self_healdemoapplication/
│   ├── MainActivity.kt
│   ├── data/
│   │   ├── DemoUsers.kt
│   │   └── Product.kt
│   ├── ui/
│   │   ├── adapters/
│   │   │   └── ProductsAdapter.kt
│   │   └── fragments/
│   │       ├── LoginFragment.kt
│   │       └── ProductsFragment.kt
│   └── viewmodel/
│       └── SelfHealViewModel.kt
└── res/
    ├── layout/
    │   ├── activity_main.xml
    │   ├── fragment_login.xml
    │   ├── fragment_products.xml
    │   └── item_product.xml
    ├── values/
    │   ├── colors.xml
    │   ├── strings.xml
    │   └── themes.xml
    └── drawable/
        └── [product images]
```

---

## Element ID System for Appium

### How Element IDs Work

Instead of using Android's standard `android:id`, this app uses **accessibility identifiers** set via `ViewCompat.setAccessibilityPaneTitle()`. This allows dynamic ID changes at runtime for the self-healing demo.

### Implementation Pattern

```kotlin
// In Fragment or Adapter
private fun updateElementIds(isDemoMode: Boolean) {
    ViewCompat.setAccessibilityPaneTitle(
        binding.userDropdown,
        if (isDemoMode) "user_dropdown_modified" else "user_dropdown"
    )
}
```

### Appium Access

```kotlin
// Appium test code
driver.findElement(AppiumBy.accessibilityId("user_dropdown"))  // Normal mode
driver.findElement(AppiumBy.accessibilityId("user_dropdown_modified"))  // Demo mode ON
```

### Element IDs Reference

**Login Screen:**
- `demo_mode_switch` (toggle switch - does not change)
- `demo_mode_status` (status text - does not change)
- `user_dropdown` → `user_dropdown_modified`
- `email_input` → `email_input_modified`
- `password_input` → `password_input_modified`
- `sign_in_button` → `sign_in_button_modified`

**Products Screen:**
- `logout_button` → `logout_button_modified`
- `products_list` → `products_list_modified`
- `product_card_{id}` → `product_card_{id}_modified` (e.g., `product_card_1`)
- `add_to_cart_{id}` → `add_to_cart_{id}_modified`

**Default Configuration:**
- `HealingElement.SELECT_USER` is set by default (only user dropdown shows notification)

---

## Self-Healing Mechanism Details

### ViewModel State Management

```kotlin
class SelfHealViewModel : ViewModel() {
    private val _isDemoModeEnabled = MutableLiveData(false)
    val isDemoModeEnabled: LiveData<Boolean> = _isDemoModeEnabled

    private val _healingElement = MutableLiveData(HealingElement.SELECT_USER)
    val healingElement: LiveData<HealingElement> = _healingElement

    fun toggleDemoMode() {
        _isDemoModeEnabled.value = _isDemoModeEnabled.value?.not() ?: true
    }
}
```

### HealingElement Enum

```kotlin
enum class HealingElement {
    SELECT_USER,  // Show notification only on user dropdown
    EMAIL,        // Show notification only on email field
    PASSWORD,     // Show notification only on password field
    ALL,          // Show notifications on all elements
    NONE          // Hide all notifications
}
```

### Observer Pattern in Fragments

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.isDemoModeEnabled.observe(viewLifecycleOwner) { isDemoMode ->
        updateElementIds(isDemoMode)
        updateHealingNotifications(isDemoMode)
    }
}
```

---

## User Flows

### Flow 1: Login
1. User opens app → sees Login screen
2. User selects demo user from dropdown → email and password auto-fill
3. User clicks "Sign In" → navigates to Products Home screen

### Flow 2: Browse Products
1. User lands on Products Home screen
2. User can scroll through product list (RecyclerView)
3. User can adjust quantity using +/- buttons
4. User can add products to cart (placeholder functionality)

### Flow 3: Logout
1. User clicks Logout button in top right
2. App returns to Login screen via Fragment transaction

### Flow 4: Toggle Self-Heal Mode
1. User toggles "Self-Heal Mode" switch at top
2. Element accessibility IDs change to `*_modified` versions
3. Orange notification banners appear (based on HealingElement setting)
4. Automated tests can now demonstrate self-healing by finding new IDs

---

## Building & Running

### Build APK

```bash
# Debug build
./gradlew assembleDebug

# Output location
app/build/outputs/apk/debug/selfHealDemoAndroidApp.apk
```

### Install on Device

```bash
adb install app/build/outputs/apk/debug/selfHealDemoAndroidApp.apk
```

### Running Appium Tests

```kotlin
// Sample Appium test code
val driver = AndroidDriver(
    URL("http://localhost:4723"),
    UiAutomator2Options()
        .setApp("/path/to/selfHealDemoAndroidApp.apk")
        .setPlatformName("Android")
)

// Find elements using accessibility IDs
val dropdown = driver.findElement(AppiumBy.accessibilityId("user_dropdown"))
dropdown.click()

// After toggling demo mode
val modifiedDropdown = driver.findElement(
    AppiumBy.accessibilityId("user_dropdown_modified")
)
```

---

## Git Branch Information

- **Main Branch:** Contains Jetpack Compose version (preserved)
- **Traditional Views Branch:** `traditional-views` (this version)

### Switch Between Versions

```bash
# Switch to traditional views
git checkout traditional-views

# Switch back to Compose version
git checkout main
```

---

## Appium Compatibility Advantages

### Standard UiAutomator2 Support
✅ Works with standard `UiAutomator2Options()`
✅ No need for Espresso driver
✅ No need for `driver.setSetting("driver", "compose")`
✅ Direct element finding via `AppiumBy.accessibilityId()`

### Element Finding Examples

```kotlin
// Traditional Views (this version) ✅
driver.findElement(AppiumBy.accessibilityId("user_dropdown"))

// Compose version would require ❌
driver.setSetting("driver", "compose")
driver.findElement(AppiumBy.id("com.example.app:id/user_dropdown"))
```

### BrowserStack Compatibility
- Standard Android automation capabilities work immediately
- No special device configurations needed
- Better test reliability and performance
- Easier debugging with standard Android tools

---

## Key Code Files

### MainActivity.kt
Main activity using ViewBinding with toggle bar and fragment container.

**Key Methods:**
- `setupToggleBar()` - Observes ViewModel for demo mode changes
- `replaceFragment()` - Handles fragment navigation
- `getViewModel()` - Provides ViewModel to fragments

### LoginFragment.kt
Login screen with dropdown, email, password fields, and sign-in button.

**Key Methods:**
- `setupUserDropdown()` - Populates Spinner with demo users
- `updateElementIds()` - Changes accessibility IDs based on demo mode
- `updateHealingNotifications()` - Shows/hides healing banners
- `setupSignInButton()` - Handles authentication and navigation

### ProductsFragment.kt
Products listing screen with RecyclerView.

**Key Methods:**
- `setupRecyclerView()` - Initializes ProductsAdapter
- `setupDemoModeObserver()` - Refreshes adapter when demo mode changes

### ProductsAdapter.kt
RecyclerView adapter for product list with dynamic IDs.

**Key Methods:**
- `bind()` - Binds product data and sets dynamic accessibility IDs
- Handles quantity controls and add-to-cart actions

### SelfHealViewModel.kt
ViewModel managing demo mode state using LiveData.

**Key Properties:**
- `isDemoModeEnabled: LiveData<Boolean>` - Demo mode state
- `healingElement: LiveData<HealingElement>` - Which element to show notification for

---

## Testing Checklist

### Manual Testing
- [ ] Toggle Self-Heal Mode switch
- [ ] Verify element IDs change (use Appium Inspector)
- [ ] Select user from dropdown → verify auto-fill
- [ ] Sign in successfully
- [ ] Navigate to products screen
- [ ] Scroll through products list
- [ ] Adjust product quantities
- [ ] Logout returns to login screen
- [ ] Verify healing notifications appear correctly

### Appium Testing
- [ ] Install APK on device/emulator
- [ ] Launch app via Appium
- [ ] Find elements using `AppiumBy.accessibilityId()`
- [ ] Toggle demo mode
- [ ] Verify elements found with `*_modified` IDs
- [ ] Complete login flow
- [ ] Verify products screen elements

---

## Future Enhancements

### Potential Features
- Shopping cart functionality (currently placeholder)
- Product search implementation
- Product filtering by platform
- Add to favorites
- User profile screen
- Order history
- More self-healing scenarios (navigation elements, etc.)

### Technical Improvements
- Unit tests for ViewModel
- UI tests with Espresso
- CI/CD integration
- APK size optimization
- Performance profiling

---

## Support & Documentation

### Related Files
- `app_prd.md` - Original Compose version PRD
- `MainActivity.kt` - Main entry point
- `SelfHealViewModel.kt` - State management

### Key Resources
- [Android Views Documentation](https://developer.android.com/guide/topics/ui/declaring-layout)
- [ViewBinding Guide](https://developer.android.com/topic/libraries/view-binding)
- [LiveData Overview](https://developer.android.com/topic/libraries/architecture/livedata)
- [Appium Android Documentation](http://appium.io/docs/en/drivers/android-uiautomator2/)
- [BrowserStack Appium Guide](https://www.browserstack.com/docs/app-automate/appium/getting-started/java)
