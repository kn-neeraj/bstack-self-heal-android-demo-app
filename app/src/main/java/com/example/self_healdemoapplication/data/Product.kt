package com.example.self_healdemoapplication.data

data class Product(
    val id: Int,
    val name: String,
    val platform: Platform,
    val price: Double,
    val rating: Float,
    val reviewCount: Int,
    val description: String,
    val imageResId: Int? = null
)

enum class Platform(val displayName: String) {
    ANDROID("android"),
    IOS("ios")
}

object ProductsData {
    val products = listOf(
        Product(
            id = 1,
            name = "Google Pixel 4",
            platform = Platform.ANDROID,
            price = 499.99,
            rating = 4.5f,
            reviewCount = 289,
            description = "Google Pixel 4 with Smartphone 64GB, 5.7-inch OLED display, 16MP Dual Camera, and 6GB RAM",
            imageResId = null
        ),
        Product(
            id = 2,
            name = "iPhone 11",
            platform = Platform.IOS,
            price = 599.99,
            rating = 4.7f,
            reviewCount = 512,
            description = "Apple iPhone 11 with A13 Bionic chip, 6.1-inch Liquid Retina HD display, Dual 12MP cameras",
            imageResId = null
        ),
        Product(
            id = 3,
            name = "iPhone 12 Pro",
            platform = Platform.IOS,
            price = 899.99,
            rating = 4.8f,
            reviewCount = 673,
            description = "Apple iPhone 12 Pro with A14 Bionic chip, 6.1-inch Super Retina XDR display, Pro camera system",
            imageResId = null
        ),
        Product(
            id = 4,
            name = "Samsung Galaxy Note 20",
            platform = Platform.ANDROID,
            price = 749.99,
            rating = 4.6f,
            reviewCount = 401,
            description = "Samsung Galaxy Note 20 with Snapdragon 865+, 6.7-inch Dynamic AMLOED display, S Pen included",
            imageResId = null
        ),
        Product(
            id = 5,
            name = "OnePlus 6T",
            platform = Platform.ANDROID,
            price = 399.99,
            rating = 4.4f,
            reviewCount = 325,
            description = "OnePlus 6T with Snapdragon 845, 6.41-inch Optic AMOLED display, In-display fingerprint sensor",
            imageResId = null
        ),
        Product(
            id = 6,
            name = "OnePlus 8",
            platform = Platform.ANDROID,
            price = 549.99,
            rating = 4.5f,
            reviewCount = 298,
            description = "OnePlus 8 with Snapdragon 865, 6.55-inch Fluid AMOLED display, 48MP Triple camera setup",
            imageResId = null
        )
    )
}
