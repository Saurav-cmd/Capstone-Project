package com.saurav.boozebuddy.impl.wishlist_impl

import com.saurav.boozebuddy.api_services.FirestoreHelper
import com.saurav.boozebuddy.app_interface.wish_list_interface.WishlistInterface
import com.saurav.boozebuddy.models.WishlistModel
import javax.inject.Inject

class WishlistImplementation @Inject constructor(val firestoreHelper: FirestoreHelper) : WishlistInterface {
    override suspend fun addWishList(
        folderName: String,
        productName: String,
        productDesc: String,
        productImage: String,
        brandName: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        firestoreHelper.storeUserWishlist(folderName, productName, productDesc,productImage, brandName, productId, brandId){
            success, errMsg ->
            callback(success, errMsg)
        }
    }

    override suspend fun getWishList(): List<WishlistModel> {
        return firestoreHelper.fetchWishList()
    }
}