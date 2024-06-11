package com.saurav.boozebuddy.models

import kotlinx.serialization.Serializable

@Serializable
data class BrandModel(
    val brandId: String = "",
    val brandDescription: String? = null,
    val brandLogo: String? = null,
    val brandName: String? = null,
    val products: List<Product>? = emptyList()
)
@Serializable
data class Product(
    val productId: String = "",
    val productName: String? = null,
    val productDescription: String? = null,
    val productImage: String? = null,
    val productPrice: Double? = 0.0,
    val productQuantity: Int? = 0,
)

