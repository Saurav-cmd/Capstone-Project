package com.saurav.boozebuddy.api_services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.UserModel
import kotlinx.coroutines.tasks.await

class FirestoreHelper(private val firestore: FirebaseFirestore, private val auth: FirebaseAuth) {
    suspend fun fetchBrandsWithProducts(): List<BrandModel> {
        return try {
            val brandsCollection = firestore.collection("brands")
            val brandsSnapshot = brandsCollection.get().await()
            brandsSnapshot.documents.mapNotNull { document ->
                val brand = document.toObject(BrandModel::class.java)
                if (brand != null) {
                    val productsSnapshot = document.reference.collection("products").get().await()
                    val products = productsSnapshot.toObjects(Product::class.java)
                    brand.copy(products = products)
                } else {
                    Log.e("FirestoreHelper", "Brand document is null")
                    null
                }
            }
        } catch (e: Exception) {
            Log.e("FirestoreHelper", "Error fetching brands with products: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun fetchUserInfo() : UserModel? {
        return try{
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
        }catch (e: Exception){
            ErrorHandler.getErrorMessage(e)
            UserModel()
        }
    }
}
