package com.saurav.boozebuddy.impl.favourites_impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.api_services.FirestoreHelper
import com.saurav.boozebuddy.app_interface.favourites_interface.FavouritesInterface
import com.saurav.boozebuddy.models.UserFavouritesModel
import kotlinx.coroutines.tasks.await
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

    suspend fun deleteFavourite(favourite: UserFavouritesModel, callback: (Boolean, String?) -> Unit) {
        try {
            Log.d("FavouritesImpl", "Attempting to delete favourite with id: ${favourite.id}")
            firestore.collection("user_favourites")
                .document(favourite.id)
                .delete()
                .await()
            Log.d("FavouritesImpl", "Successfully deleted favourite with id: ${favourite.id}")
            callback(true, null)
        } catch (e: Exception) {
            Log.e("FavouritesImpl", "Error deleting favourite: ${e.message}")
            callback(false, e.message)
        }
    }
}