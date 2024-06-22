package com.saurav.boozebuddy.models

import kotlinx.serialization.Serializable

@Serializable
data class UserFavouritesModel(
    val id: String = "",
    val brandId: String = "",
    val brandName: String = "",
    val productId: String = "",
    val productImage: String = "",
    val productName: String = "",
    val productABV: Int = 0,
    val productVolume: Int = 0,
    val productIngredients: List<String> = emptyList(),
    val productOrigin: String = ""
)
