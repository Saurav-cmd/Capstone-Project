package com.saurav.boozebuddy.models

import android.accounts.AuthenticatorDescription

data class WishlistModel(
    val wishName: String = "" ,
    val wishListProducts: List<WishListProducts> = emptyList()
)

data class WishListProducts(
    val wishListProductId:String = "",
    val brandId:String = "",
    val brandName:String = "",
    val productDescription: String = "",
    val productId: String = "",
    val productImage:String = "",
    val productName:String = ""
)