package com.example.self_healdemoapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.self_healdemoapplication.data.Product
import com.example.self_healdemoapplication.data.ProductsData
import com.example.self_healdemoapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsHomeScreen(
    onLogout: () -> Unit,
    viewModel: com.example.self_healdemoapplication.viewmodel.SelfHealViewModel,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val isDemoMode by viewModel.isDemoModeEnabled.collectAsState()

    val filteredProducts = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            ProductsData.products
        } else {
            ProductsData.products.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBackground)
    ) {
        // Top Navigation Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(if (isDemoMode) "navigation_bar_demo" else "navigation_bar"),
            color = DarkNavy,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Empty spacer for left side to keep Home centered
                Spacer(modifier = Modifier.width(48.dp))

                // Home Title (centered)
                Text(
                    text = "Home",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.testTag("home_title")
                )

                // Logout Button
                IconButton(
                    onClick = onLogout,
                    modifier = Modifier.testTag(
                        if (isDemoMode) "logout_button_demo" else "logout_button"
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = White
                    )
                }
            }
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Products Header
            Text(
                text = "Products",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search products...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = LightGray,
                    focusedBorderColor = DarkNavy,
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .testTag(if (isDemoMode) "search_bar_demo" else "search_bar")
            )

            // Products List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.testTag(
                    if (isDemoMode) "products_list_demo" else "products_list"
                )
            ) {
                items(filteredProducts) { product ->
                    ProductCard(product = product, isDemoMode = isDemoMode)
                }
            }
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    isDemoMode: Boolean,
    modifier: Modifier = Modifier
) {
    var quantity by remember { mutableStateOf(1) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag(
                if (isDemoMode) "product_card_${product.id}_demo" else "product_card_${product.id}"
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Product Name
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        modifier = Modifier.testTag("product_name_${product.id}")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Platform Badge
                    Surface(
                        color = PlatformBlue,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.testTag("product_platform_${product.id}")
                    ) {
                        Text(
                            text = product.platform.displayName,
                            color = White,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Product Image
                product.imageResId?.let { imageResId ->
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = product.name,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .testTag("product_image_${product.id}"),
                        contentScale = ContentScale.Fit
                    )
                } ?: Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(LightGray, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "IMG",
                        color = Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.testTag("product_rating_${product.id}")
            ) {
                Text(
                    text = "★ ${product.rating}",
                    fontSize = 16.sp,
                    color = OrangeAccent,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = " (${product.reviewCount})",
                    fontSize = 14.sp,
                    color = Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = product.description,
                fontSize = 14.sp,
                color = Gray,
                lineHeight = 20.sp,
                modifier = Modifier.testTag("product_description_${product.id}")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Price
            Text(
                text = "$${String.format("%.2f", product.price)}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                modifier = Modifier.testTag("product_price_${product.id}")
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quantity Selector and Add to Cart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quantity Selector
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.testTag("quantity_selector_${product.id}")
                ) {
                    Button(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("quantity_decrease_${product.id}"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightGray,
                            contentColor = Black
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("-", fontSize = 20.sp)
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.testTag("quantity_value_${product.id}")
                    )

                    Button(
                        onClick = { quantity++ },
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("quantity_increase_${product.id}"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LightGray,
                            contentColor = Black
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("+", fontSize = 20.sp)
                    }
                }

                // Add to Cart Button
                Button(
                    onClick = { /* Add to cart logic */ },
                    modifier = Modifier.testTag(
                        if (isDemoMode) "add_to_cart_${product.id}_demo" else "add_to_cart_${product.id}"
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkNavy
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Add to Cart",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
