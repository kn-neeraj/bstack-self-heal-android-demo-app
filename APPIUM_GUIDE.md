# Appium UiAutomator2 Testing Guide

This guide explains how to use Appium with UiAutomator2 driver to test the Self-Healing Demo Android App.

## Prerequisites

- Appium Server (v2.x recommended)
- UiAutomator2 Driver installed: `appium driver install uiautomator2`
- Java Development Kit (JDK 8 or higher)
- Android SDK Platform Tools
- Appium Client Library for your language (Java, Python, JavaScript, etc.)

## App Configuration

The app has been configured with **`testTagsAsResourceId = true`** in the root composable ([MainActivity.kt:47](app/src/main/java/com/example/self_healdemoapplication/MainActivity.kt#L47)), which enables all `testTag` identifiers to be accessible as standard Android resource IDs.

This means you can use Appium's standard `By.id()` locator strategy to find elements.

## Appium Capabilities

### Java Example

```java
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class SelfHealDemoTest {
    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
            .setPlatformName("Android")
            .setAutomationName("UiAutomator2")
            .setDeviceName("emulator-5554")  // or your device name
            .setApp("/path/to/app-debug.apk")
            .setAppPackage("com.example.self_healdemoapplication")
            .setAppActivity("com.example.self_healdemoapplication.MainActivity")
            .setNoReset(false);

        driver = new AndroidDriver(
            new URL("http://127.0.0.1:4723"),
            options
        );
    }
}
```

### Python Example

```python
from appium import webdriver
from appium.options.android import UiAutomator2Options

options = UiAutomator2Options()
options.platform_name = 'Android'
options.automation_name = 'UiAutomator2'
options.device_name = 'emulator-5554'
options.app = '/path/to/app-debug.apk'
options.app_package = 'com.example.self_healdemoapplication'
options.app_activity = 'com.example.self_healdemoapplication.MainActivity'
options.no_reset = False

driver = webdriver.Remote('http://127.0.0.1:4723', options=options)
```

## Finding Elements by ID

All UI elements in the app are accessible using their `testTag` values as resource IDs.

### Login Screen Elements

```java
// User dropdown
WebElement userDropdown = driver.findElement(AppiumBy.id("user_dropdown"));

// Email input
WebElement emailInput = driver.findElement(AppiumBy.id("email_input"));

// Password input
WebElement passwordInput = driver.findElement(AppiumBy.id("password_input"));

// Password visibility toggle
WebElement passwordToggle = driver.findElement(AppiumBy.id("password_toggle"));

// Sign in button
WebElement signInButton = driver.findElement(AppiumBy.id("sign_in_button"));
```

### Products Home Screen Elements

```java
// Search bar
WebElement searchBar = driver.findElement(AppiumBy.id("search_bar"));

// Logout button
WebElement logoutButton = driver.findElement(AppiumBy.id("logout_button"));

// Products list
WebElement productsList = driver.findElement(AppiumBy.id("products_list"));

// Product card (replace {id} with product ID: 1, 2, 3, etc.)
WebElement productCard = driver.findElement(AppiumBy.id("product_card_1"));

// Add to cart button for product
WebElement addToCartButton = driver.findElement(AppiumBy.id("add_to_cart_1"));
```

### Demo Mode Toggle

```java
// Demo mode switch
WebElement demoModeSwitch = driver.findElement(AppiumBy.id("demo_mode_switch"));

// Demo mode bar
WebElement demoModeBar = driver.findElement(AppiumBy.id("demo_mode_bar"));
```

## Complete Test Example (Java)

```java
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class LoginFlowTest {
    private AndroidDriver driver;

    @Before
    public void setUp() {
        // Setup code from above
    }

    @Test
    public void testLoginFlow() {
        // Find and click user dropdown
        WebElement userDropdown = driver.findElement(AppiumBy.id("user_dropdown"));
        userDropdown.click();

        // Select first user option
        WebElement userOption = driver.findElement(
            AppiumBy.id("user_option_demo1@example.com")
        );
        userOption.click();

        // Email and password should auto-fill, click sign in
        WebElement signInButton = driver.findElement(AppiumBy.id("sign_in_button"));
        signInButton.click();

        // Verify we're on products screen
        WebElement searchBar = driver.findElement(AppiumBy.id("search_bar"));
        assert searchBar.isDisplayed();
    }

    @Test
    public void testProductSearch() {
        // Assuming already logged in

        // Enter search text
        WebElement searchBar = driver.findElement(AppiumBy.id("search_bar"));
        searchBar.sendKeys("iPhone");

        // Verify product cards are filtered
        // Add assertions here
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

## Complete Test Example (Python)

```python
from appium import webdriver
from appium.webdriver.common.appiumby import AppiumBy
import unittest

class LoginFlowTest(unittest.TestCase):

    def setUp(self):
        # Setup code from above
        pass

    def test_login_flow(self):
        # Find and click user dropdown
        user_dropdown = self.driver.find_element(AppiumBy.ID, "user_dropdown")
        user_dropdown.click()

        # Select first user option
        user_option = self.driver.find_element(
            AppiumBy.ID,
            "user_option_demo1@example.com"
        )
        user_option.click()

        # Click sign in
        sign_in_button = self.driver.find_element(AppiumBy.ID, "sign_in_button")
        sign_in_button.click()

        # Verify we're on products screen
        search_bar = self.driver.find_element(AppiumBy.ID, "search_bar")
        assert search_bar.is_displayed()

    def test_product_search(self):
        # Enter search text
        search_bar = self.driver.find_element(AppiumBy.ID, "search_bar")
        search_bar.send_keys("iPhone")

        # Verify product cards are filtered
        # Add assertions here

    def tearDown(self):
        if self.driver:
            self.driver.quit()

if __name__ == '__main__':
    unittest.main()
```

## Testing Self-Healing Functionality

The app includes a demo mode toggle that changes all element IDs from their normal form to a `_demo` suffix.

### Element IDs Change Pattern

| Element | Normal Mode | Demo Mode Active |
|---------|-------------|------------------|
| Sign In Button | `sign_in_button` | `sign_in_button_demo` |
| Email Input | `email_input` | `email_input_demo` |
| Password Input | `password_input` | `password_input_demo` |
| Search Bar | `search_bar` | `search_bar_demo` |
| Logout Button | `logout_button` | `logout_button_demo` |
| Add to Cart (Product 1) | `add_to_cart_1` | `add_to_cart_1_demo` |

### Test Self-Healing Scenario

```java
@Test
public void testSelfHealing() {
    // Step 1: Login with normal IDs
    driver.findElement(AppiumBy.id("sign_in_button")).click();

    // Step 2: Toggle demo mode
    WebElement demoModeSwitch = driver.findElement(AppiumBy.id("demo_mode_switch"));
    demoModeSwitch.click();

    // Step 3: Now all IDs have changed - try to find with new IDs
    try {
        // Old ID won't work
        driver.findElement(AppiumBy.id("logout_button"));
        fail("Should not find element with old ID");
    } catch (NoSuchElementException e) {
        // Expected
    }

    // Step 4: Use new ID
    WebElement logoutButton = driver.findElement(AppiumBy.id("logout_button_demo"));
    logoutButton.click();

    // This demonstrates the scenario where BrowserStack's Self-Healing
    // agent would detect the ID change and update the test automatically
}
```

## Element ID Reference

### All Available Element IDs

**Login Screen:**
- `user_dropdown` → `user_dropdown_demo`
- `user_option_demo1@example.com` (static)
- `user_option_demo2@example.com` (static)
- `user_option_testuser@example.com` (static)
- `email_input` → `email_input_demo`
- `password_input` → `password_input_demo`
- `password_toggle` → `password_toggle_demo`
- `sign_in_button` → `sign_in_button_demo`

**Products Home Screen:**
- `navigation_bar` → `navigation_bar_demo`
- `home_title` (static)
- `app_logo` (static)
- `logout_button` → `logout_button_demo`
- `search_bar` → `search_bar_demo`
- `products_list` → `products_list_demo`

**Product Cards (1-6):**
- `product_card_1` → `product_card_1_demo`
- `product_card_2` → `product_card_2_demo`
- `product_card_3` → `product_card_3_demo`
- `product_card_4` → `product_card_4_demo`
- `product_card_5` → `product_card_5_demo`
- `product_card_6` → `product_card_6_demo`
- `add_to_cart_1` → `add_to_cart_1_demo`
- `add_to_cart_2` → `add_to_cart_2_demo`
- ... (and so on for products 3-6)

**Demo Mode:**
- `demo_mode_bar` (static)
- `demo_mode_switch` (static)

## Troubleshooting

### Element Not Found

If you cannot find an element:

1. **Check if demo mode is active** - The IDs change when demo mode is toggled
2. **Verify the app is installed** - Ensure you're testing the correct APK
3. **Check Appium logs** - Look for detailed error messages
4. **Use Appium Inspector** - Visually inspect the element hierarchy

### Finding Elements with Appium Inspector

1. Start Appium Server
2. Open Appium Inspector
3. Configure desired capabilities as shown above
4. Click "Start Session"
5. You'll see all elements with their resource IDs (matching testTag values)

### Verifying testTagsAsResourceId is Working

Run this command to inspect the UI hierarchy:

```bash
adb shell uiautomator dump /sdcard/ui.xml && adb pull /sdcard/ui.xml
```

Open `ui.xml` and verify elements have `resource-id` attributes matching your testTag values.

## BrowserStack Integration

For BrowserStack Self-Healing demonstration:

1. Upload the APK to BrowserStack App Automate
2. Configure your test script with BrowserStack capabilities
3. Run tests initially with normal element IDs
4. Toggle demo mode in the app (or modify testTags)
5. BrowserStack Self-Healing agent will detect changed IDs and adapt automatically

## Additional Resources

- [Appium Documentation](https://appium.io/docs/en/latest/)
- [UiAutomator2 Driver](https://github.com/appium/appium-uiautomator2-driver)
- [Android Testing Documentation](https://developer.android.com/develop/ui/compose/testing/interoperability)
- [BrowserStack App Automate](https://www.browserstack.com/app-automate)

## Support

For issues with:
- **Appium setup**: Check Appium documentation
- **App functionality**: See main [README.md](README.md)
- **BrowserStack features**: Visit BrowserStack support
