# PRD: Self-Healing Demo App (Android)

## Description
A simple native Android e-commerce app designed to demonstrate BrowserStack's Self-Healing agent. The app allows toggling of DOM properties (IDs, accessibility IDs) to trigger and showcase healing capabilities when automated tests encounter changed locators.

## App Screens &  Features

### 1. Login Screen

**Features:**
- "Quick Select User" dropdown with pre-populated demo users:
    - demo1@example.com
    - demo2@example.com
    - testuser@example.com
- Auto-fill email and password fields when user selected from dropdown
- Email input field (placeholder: "Enter your email")
- Password input field with show/hide toggle (placeholder: "Enter your password")
- "Sign In" button (full-width, dark background)
- "Welcome Back" header with subtitle "Sign in to your account"

**Screenshots:**

Login Screen - Default State:
![Login Screen](./images/login_screen.png)

Login Screen - Dropdown Open:
![Login Dropdown](./images/login_dropdown.png)

---

### 2. Products Home Screen

**Features:**
- **Top Navigation Bar:**
    - BrowserStack logo (left)
    - "Home" title (center)
    - Logout button (right)
- **Search & Filter:**
    - Search bar (placeholder: "Search products...")
    - Category filter dropdown (All, Android, iOS)
- **Product List (vertical scroll):**
    - Product image
    - Product name
    - Platform badge (android/ios)
    - Star rating with review count
    - Product description (truncated)
    - Price
    - Quantity selector
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
- **Text:** Black for headers, gray for secondary text
- **Accent:** Orange (#FF7043) for CTA buttons
- **Badges:** Blue for platform tags

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

### Icons
- Eye icon for password visibility toggle
- Star icons for ratings
- Search icon in search bar
- BrowserStack logo in navigation

---

## Technical Requirements

### Platform
- Native Android app
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)

### Key Implementation Notes
- All UI elements must have unique, stable resource IDs
- Support for toggling resource IDs to demonstrate self-healing
- Accessibility IDs must be properly set for all interactive elements
- Use standard Android UI components (RecyclerView for product list, Spinner for dropdowns)

### Self-Healing Demo Mode
- Ability to toggle DOM properties (resource IDs, content descriptions) at runtime
- When demo mode is activated, specific element IDs should change to simulate locator failures
- This allows BrowserStack's Self-Healing agent to detect and heal the changed locators during test execution

---

## User Flows

### Flow 1: Login
1. User opens app → sees Login screen
2. User selects demo user from dropdown → email and password auto-fill
3. User clicks "Sign In" → navigates to Products Home screen

### Flow 2: Browse Products
1. User lands on Products Home screen
2. User can scroll through product list
3. User can filter by category (All/Android/iOS)
4. User can search for products using search bar

### Flow 3: Logout
1. User clicks Logout button in top navigation
2. App returns to Login screen