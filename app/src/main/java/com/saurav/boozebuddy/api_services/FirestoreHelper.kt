package com.saurav.boozebuddy.api_services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.Product
import kotlinx.coroutines.tasks.await

class FirestoreHelper(private val firestore: FirebaseFirestore) {
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
}
