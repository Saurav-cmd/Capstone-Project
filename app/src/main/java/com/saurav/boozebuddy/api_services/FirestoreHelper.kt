package com.saurav.boozebuddy.api_services

import android.util.Log

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.UserFavouritesModel
import com.saurav.boozebuddy.models.UserModel
import kotlinx.coroutines.tasks.await

class FirestoreHelper(private val firestore: FirebaseFirestore, private val auth: FirebaseAuth) {

    suspend fun fetchBrandsWithProducts(): List<BrandModel> {
        return try {
            val brandsCollection = firestore.collection("brands")
            val brandsSnapshot = brandsCollection.get().await()
            brandsSnapshot.documents.mapNotNull { document ->
                val brand = document.toObject(BrandModel::class.java)?.copy(brandId = document.id)
                if (brand != null) {
                    val productsSnapshot = document.reference.collection("products").get().await()
                    val products = productsSnapshot.documents.mapNotNull { productDocument ->
                        productDocument.toObject(Product::class.java)
                            ?.copy(productId = productDocument.id)
                    }
                    brand.copy(products = products)
                } else {
                    //brand is empty
                    null
                }
            }
        } catch (e: Exception) {
            ErrorHandler.getErrorMessage(e)
            emptyList()
        }
    }


    suspend fun fetchUserInfo(): UserModel? {
        return try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in")
            val uid = currentUser.uid

            val documentSnapshot = firestore.collection("users")
                .document(uid)
                .get()
                .await()
            if (documentSnapshot.exists()) {
                documentSnapshot.toObject(UserModel::class.java)
            } else {
                throw Exception("User not found")
            }
        } catch (e: Exception) {
            ErrorHandler.getErrorMessage(e)
            UserModel()
        }
    }

    suspend fun storeUSerFavourites(
        productName: String,
        brandName: String,
        productImage: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            val favourite = hashMapOf(
                "productId" to productId.trim(),
                "productName" to productName,
                "brandName" to brandName,
                "productImage" to productImage,
                "brandId" to brandId.trim()
            )
            Log.e("This is the body of favourites: ", "$favourite")
            firestore.collection("users")
                .document(uid)
                .collection("favourites")
                .add(favourite)
                .await()

            callback(true, "Favourite added successfully")
        } catch (e: Exception) {
            callback(false, "$e")
            ErrorHandler.getErrorMessage(e)
        }
    }

    suspend fun fetchUserFavourites(): List<UserFavouritesModel> {
        return try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            val favouritesSnapshot = firestore.collection("users")
                .document(uid)
                .collection("favourites")
                .get()
                .await()

            // Map the documents to UserFavouritesModel and check if it's null before adding to the list
            val favouritesList = favouritesSnapshot.documents.mapNotNull { document ->
                val favourite = document.toObject(UserFavouritesModel::class.java)
                favourite?.copy(id = document.id)
            }

            Log.d("Favourite data", favouritesList.toString()) // Log the fetched favourites list
            favouritesList
        } catch (e: Exception) {
            Log.e("Fetch Favourites", "Error fetching user favourites: ${e.message}")
            emptyList() // Return an empty list in case of an error
        }
    }




}
