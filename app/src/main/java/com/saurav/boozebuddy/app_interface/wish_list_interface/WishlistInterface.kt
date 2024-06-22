package com.saurav.boozebuddy.app_interface.wish_list_interface

import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.WishlistModel

interface WishlistInterface {
    suspend fun addWishList(
        folderName: String,
        productName: String,
        productDesc: String,
        productImage: String,
        brandName: String,
        productId: String,
        brandId: String,
        product: Product,
        callback: (Boolean, String?) -> Unit
    )

    suspend fun getWishList(): List<WishlistModel>
    suspend fun deleteWishList(wishListId: String, callback: (Boolean, String?) -> Unit)

    suspend fun deleteWishlistProduct(
        wishListId: String,
        wishlistProductId: String,
        wishListName:String,
        callback: (Boolean, String?) -> Unit
    )

}