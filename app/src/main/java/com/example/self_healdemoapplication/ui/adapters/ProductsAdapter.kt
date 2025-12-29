package com.example.self_healdemoapplication.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.self_healdemoapplication.R
import com.example.self_healdemoapplication.data.Product
import com.example.self_healdemoapplication.data.sampleProducts
import com.example.self_healdemoapplication.databinding.ItemProductBinding
import com.example.self_healdemoapplication.viewmodel.SelfHealViewModel

class ProductsAdapter(private val viewModel: SelfHealViewModel) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val products = sampleProducts
    private val quantities = MutableList(products.size) { 1 }

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product, position: Int) {
            val isDemoMode = viewModel.isDemoMode()

            binding.productName.text = product.name
            binding.productPrice.text = "$${product.price}"
            binding.productDescription.text = product.description
            binding.productRating.text = "★ ${product.rating}"
            binding.productReviews.text = "(${product.reviewCount} reviews)"
            binding.productPlatform.text = product.platform.displayName

            // Set platform badge color
            val platformColor = if (product.platform.displayName == "android") {
                ContextCompat.getColor(binding.root.context, R.color.android_green)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.ios_blue)
            }
            binding.productPlatform.setBackgroundColor(platformColor)

            // Set product image
            val imageRes = when (product.name) {
                "Google Pixel 4" -> R.drawable.google_pixel_4
                "iPhone 11" -> R.drawable.iphone_11
                "iPhone 12 Pro" -> R.drawable.iphone_12_pro
                "Samsung Galaxy Note 20" -> R.drawable.samsung_galaxy_note_20
                "OnePlus 6T" -> R.drawable.oneplus_6t
                "OnePlus 8" -> R.drawable.oneplus_8
                else -> R.drawable.iphone_11
            }
            binding.productImage.setImageResource(imageRes)

            //  Update quantity
            binding.quantityText.text = quantities[position].toString()

            // Set dynamic IDs for Appium
            val productCardId = if (isDemoMode) "product_card_${product.id}_modified" else "product_card_${product.id}"
            val addToCartId = if (isDemoMode) "add_to_cart_${product.id}_modified" else "add_to_cart_${product.id}"

            ViewCompat.setAccessibilityPaneTitle(binding.root, productCardId)
            ViewCompat.setAccessibilityPaneTitle(binding.addToCartButton, addToCartId)

            // Quantity controls
            binding.decreaseButton.setOnClickListener {
                if (quantities[position] > 1) {
                    quantities[position]--
                    binding.quantityText.text = quantities[position].toString()
                }
            }

            binding.increaseButton.setOnClickListener {
                if (quantities[position] < 10) {
                    quantities[position]++
                    binding.quantityText.text = quantities[position].toString()
                }
            }

            binding.addToCartButton.setOnClickListener {
                // Handle add to cart
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], position)
    }

    override fun getItemCount() = products.size
}
