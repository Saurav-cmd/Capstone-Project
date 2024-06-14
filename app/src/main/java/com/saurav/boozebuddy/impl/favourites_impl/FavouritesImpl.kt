package com.saurav.boozebuddy.impl.favourites_impl

import com.saurav.boozebuddy.api_services.FirebaseHelper
import com.saurav.boozebuddy.api_services.FirestoreHelper
import com.saurav.boozebuddy.app_interface.favourites_interface.FavouritesInterface
import javax.inject.Inject

class FavouritesImpl @Inject constructor(private val firestoreHelper: FirestoreHelper, private val firebaseHelper: FirebaseHelper):FavouritesInterface {
    override suspend fun addToFavourites(
        productName: String,
        brandName: String,
        productImage: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        firestoreHelper.storeUSerFavourites(productName, brandName, productImage, productId, brandId){success, errMsg->
            callback(success,errMsg)
        }
    }

    override suspend fun getUserFavourites() {
        TODO("Not yet implemented")
    }
}