package com.saurav.boozebuddy.app_interface.wish_list_interface

interface WishlistInterface {
    suspend fun addWishList(
        folderName: String,
        productName: String,
        productDesc: String,
        productImage: String,
        brandName: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    )
}