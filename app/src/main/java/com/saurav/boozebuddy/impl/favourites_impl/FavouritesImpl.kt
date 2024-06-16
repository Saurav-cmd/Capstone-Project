package com.saurav.boozebuddy.impl.favourites_impl

import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.api_services.FirestoreHelper
import com.saurav.boozebuddy.app_interface.favourites_interface.FavouritesInterface
import com.saurav.boozebuddy.models.UserFavouritesModel
import javax.inject.Inject

class FavouritesImpl @Inject constructor(private val firestoreHelper: FirestoreHelper):FavouritesInterface {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
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

    override suspend fun getUserFavourites(): List<UserFavouritesModel> {
        return firestoreHelper.fetchUserFavourites()
    }

    override suspend fun deleteUserFavourites(
        favouriteId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        firestoreHelper.deleteFavouriteForUser(favouriteId, callback)
    }

}