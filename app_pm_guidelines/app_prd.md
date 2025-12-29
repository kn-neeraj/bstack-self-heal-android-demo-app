# PRD: Self-Healing Demo App (Android)

## Description
A native Android e-commerce app demonstrating BrowserStack's Self-Healing agent. Features a visible toggle to switch element IDs at runtime, simulating locator changes for automated test healing.

## Self-Healing Demo Mode

**Toggle Bar (visible at top of all screens):**
- "Self-Heal Mode" switch with status indicator
- When enabled: Element IDs change from `element_name` to `element_name_modified`
- Orange notification banners show which element IDs have changed
- Configurable via `HealingElement` enum: SELECT_USER, EMAIL, PASSWORD, ALL, NONE

## App Screens &  Features

### 1. Login Screen

**Features:**
- Self-Heal Mode toggle bar (at top)
- "Quick Select User" dropdown with pre-populated demo users:
    - demo1@example.com
    - demo2@example.com
    - testuser@example.com
- Auto-fill email and password fields when user selected from dropdown
- Email input field (placeholder: "Enter your email")
- Password input field with show/hide toggle (placeholder: "Enter your password")
- "Sign In" button (full-width, dark background)
- "Welcome Back" header with subtitle "Sign in to your account"
- Healing notifications appear above modified elements when demo mode active

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
- **Search:**
    - Search bar (placeholder: "Search products...")
- **Product List (vertical scroll):**
    - Product image
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
    - Samsung Galaxy Note 20 - Android
    - OnePlus 6T - Android
    - OnePlus 8 - Android

**Screenshot:**

Products Home Screen:
![Products Home](./images/products_home.png)

---

## Design & Style Guidelines

### Color Palette
- **Primary:** Dark navy (#1E1E2E) for navigation bar and buttons
- **Background:** Light gray/white (#F5F5F5)
- **Text:** Black for headers, gray (#757575) for secondary text
- **Accent:** Orange (#FF7043) for toggle bar and notifications
- **Platform Badges:** Android Green (#4CAF50), iOS Blue (#007AFF)

### Typography
- **Headers:** Bold, large font (24-28sp)
- **Body:** Regular, 14-16sp
- **Buttons:** Medium weight, 16sp

### Components
- Rounded corners on cards (8-12dp radius)
- Drop shadows on cards for depth
- Material Design input fields with bottom borders
- Full-width buttons with rounded corners
- Product cards with consistent padding and spacing

---

## Technical Requirements

### Platform
- Native Android app (Jetpack Compose)
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- testTagsAsResourceId enabled for Appium UiAutomator2

### Element IDs (testTag)

**Login Screen:**
- `demo_mode_bar`, `demo_mode_switch` (toggle bar)
- `user_dropdown` → `user_dropdown_modified`
- `email_input` → `email_input_modified`
- `password_input` → `password_input_modified`
- `password_toggle` → `password_toggle_modified`
- `sign_in_button` → `sign_in_button_modified`

**Products Screen:**
- `navigation_bar` → `navigation_bar_modified`
- `logout_button` → `logout_button_modified`
- `search_bar` → `search_bar_modified`
- `products_list` → `products_list_modified`
- `product_card_{id}` → `product_card_{id}_modified`
- `add_to_cart_{id}` → `add_to_cart_{id}_modified`

**Default Configuration:**
- `HealingElement.SELECT_USER` is set by default (only user dropdown shows notification)

---

## User Flows

### Flow 1: Login
1. User opens app → sees Login screen
2. User selects demo user from dropdown → email and password auto-fill
3. User clicks "Sign In" → navigates to Products Home screen

### Flow 2: Browse Products
1. User lands on Products Home screen
2. User can scroll through product list
3. User can search for products using search bar
4. User can adjust quantity and add products to cart

### Flow 3: Logout
1. User clicks Logout button in top navigation
2. App returns to Login screen

### Flow 4: Toggle Self-Heal Mode
1. User toggles "Self-Heal Mode" switch at top
2. Element IDs change to `*_modified` versions
3. Orange notification banners appear (based on HealingElement setting)
4. Automated tests can now demonstrate self-healing by finding new IDs