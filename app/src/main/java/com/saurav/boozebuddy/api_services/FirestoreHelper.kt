package com.saurav.boozebuddy.api_services

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.saurav.boozebuddy.models.BannerModel
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.UserFavouritesModel
import com.saurav.boozebuddy.models.UserModel
import com.saurav.boozebuddy.models.WishListProducts
import com.saurav.boozebuddy.models.WishlistModel
import kotlinx.coroutines.tasks.await

class FirestoreHelper(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) {

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
                    Log.e("THis is brands data", "${brand.brandName}")
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
        product: Product,
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
                "brandId" to brandId.trim(),
                "productABV" to product.productABV,
                "productVolume" to product.productVolume,
                "productOrigin" to product.productOrigin,
                "productIngredients" to product.productIngredients
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


    suspend fun deleteFavouriteForUser(favouriteId: String, callback: (Boolean, String?) -> Unit) {
        try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            // Get a reference to the user's document
            val userRef = firestore.collection("users").document(uid)

            // Get a reference to the favorite document to be deleted
            val favouriteDocRef = userRef.collection("favourites").document(favouriteId)

            // Delete the favorite document
            favouriteDocRef.delete().await()
            callback(true, null)
        } catch (e: Exception) {
            Log.e("FavouritesImpl", "Error deleting favourite: ${e.message}")
            callback(false, e.message)
        }
    }

    fun fetchBannerData(callback: (List<BannerModel>?) -> Unit) {
        val bannerReference = database.getReference("banner")
        bannerReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val banners =
                    dataSnapshot.children.mapNotNull { it.getValue(BannerModel::class.java) }
                callback(banners)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(
                    "RealtimeDatabaseHelper",
                    "Error fetching banner data",
                    databaseError.toException()
                )
                callback(null)
            }
        })
    }

    suspend fun storeUserWishlist(
        folderName: String,
        productName: String,
        productDesc: String,
        productImage: String,
        brandName: String,
        productId: String,
        brandId: String,
        product: Product,
        dateStamp:String,
        timeStamp: String,
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            // Check if the user has selected an existing wishlist or provided a new one
            val existingWishlistQuery = firestore.collection("users")
                .document(uid)
                .collection("wishlist")
                .whereEqualTo("folderName", folderName)
                .get()
                .await()

            val wishlistItemId = if (existingWishlistQuery.isEmpty) {
                // Create a new document under 'wishlist' collection with auto-generated ID
                firestore.collection("users")
                    .document(uid)
                    .collection("wishlist")
                    .add(mapOf("folderName" to folderName))
                    .await()
                    .id
            } else {
                // Use the existing document ID if the wishlist already exists
                existingWishlistQuery.documents.first().id
            }

            // Prepare product data to store under the selected wishlist
            val productData = hashMapOf(
                "productId" to productId.trim(),
                "productName" to productName,
                "productDescription" to productDesc,
                "brandName" to brandName,
                "productImage" to productImage,
                "brandId" to brandId.trim(),
                "productABV" to product.productABV,
                "productVolume" to product.productVolume,
                "productOrigin" to product.productOrigin,
                "productIngredients" to product.productIngredients,
                "dateStamp" to dateStamp,
                "timeStamp" to timeStamp
            )

            // Add productData under folderName inside the wishlistItemId
            firestore.collection("users")
                .document(uid)
                .collection("wishlist")
                .document(wishlistItemId)
                .collection(folderName)
                .add(productData)
                .await()

            callback(true, "Wishlist item added successfully")
        } catch (e: Exception) {
            callback(false, "$e")
            ErrorHandler.getErrorMessage(e)
        }
    }


    suspend fun fetchWishList(): List<WishlistModel> {
        return try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            val wishListSnapshot = firestore.collection("users")
                .document(uid)
                .collection("wishlist")
                .get()
                .await()

            val wishListData = wishListSnapshot.documents.mapNotNull { document ->
                val wishId = document.id // Get the wishlist item ID
                val wishName = document.getString("folderName") ?: return@mapNotNull null

                val productsSnapshot = document.reference.collection(wishName).get().await()
                val products = productsSnapshot.documents.mapNotNull { productDoc ->
                    productDoc.toObject(WishListProducts::class.java)
                }

                WishlistModel(wishId, wishName, products)
            }
            wishListData
        } catch (e: Exception) {
            Log.e("Fetch Wishlist", "Error fetching user wishlist: ${e.message}", e)
            emptyList()
        }
    }


    suspend fun deleteWishList(wishListId: String, callback: (Boolean, String?) -> Unit) {
        try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            // Get a reference to the user's document
            val userRef = firestore.collection("users").document(uid)

            // Get a reference to the wishlist document to be deleted
            val wishListDocRef = userRef.collection("wishlist").document(wishListId)

            // Check if the document exists
            val documentSnapshot = wishListDocRef.get().await()
            if (documentSnapshot.exists()) {
                // Delete the document
                wishListDocRef.delete().await()
                callback(true, null)
            } else {
                callback(false, "Document not found")
            }
        } catch (e: Exception) {
            Log.e("WishList Impl", "Error deleting wishlist: ${e.message}")
            callback(false, e.message)
        }
    }

    suspend fun deleteWishListProduct(
        wishListId: String,
        wishListProductId: String,
        wishListName: String,
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            // Get a reference to the user's document
            val userRef = firestore.collection("users").document(uid)

            // Get a reference to the product within the specific wishlist
            val wishListProductRef = userRef.collection("wishlist")
                .document(wishListId)
                .collection("products")
                .document(wishListProductId)

            // Check if the document exists
            val documentSnapshot = wishListProductRef.get().await()
            if (documentSnapshot.exists()) {
                // Delete the document
                wishListProductRef.delete().await()
                callback(true, null)
            } else {
                callback(false, "Document not found")
            }
        } catch (e: Exception) {
            Log.e("WishList Impl", "Error deleting wishlist: ${e.message}", e)
            callback(false, e.message)
        }
    }

    suspend fun clearAllAppData(callback: (Boolean, String?) -> Unit) {
        try {
            val currentUser = auth.currentUser ?: throw Exception("Not logged in or user not found")
            val uid = currentUser.uid

            // Get references to the user's collections
            val userRef = firestore.collection("users").document(uid)
            val wishListCollection = userRef.collection("wishlist")
            val favouritesCollection = userRef.collection("favourites")

            // Delete documents in 'wishlist' collection and its subcollections
            val wishListDeleteResult = deleteCollectionRecursively(wishListCollection)

            // Delete documents in 'favourites' collection and its subcollections
            val favouritesDeleteResult = deleteCollectionRecursively(favouritesCollection)

            if (!wishListDeleteResult || !favouritesDeleteResult) {
                callback(false, "Failed to delete wishlist or favourites")
                return
            }

            callback(true, "Collections deleted successfully")
        } catch (e: Exception) {
            callback(false, e.message)
        }
    }

    // Helper function to delete a collection recursively
    private suspend fun deleteCollectionRecursively(collection: CollectionReference): Boolean {
        return try {
            // Get all documents in the collection
            val querySnapshot = collection.get().await()

            // Delete each document and its subcollections
            for (document in querySnapshot.documents) {
                deleteDocumentAndSubcollections(document.reference)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    // Helper function to delete a document and its subcollections recursively
    private suspend fun deleteDocumentAndSubcollections(documentRef: DocumentReference) {
        try {
            // Recursively delete all subcollections
            val subCollections = documentRef.listSubCollections()
            for (subCollection in subCollections) {
                deleteCollectionRecursively(subCollection)
            }

            // Delete the document itself
            documentRef.delete().await()
        } catch (e: Exception) {
            // Handle error if needed
        }
    }

    // Extension function to list subcollections of a document
    private suspend fun DocumentReference.listSubCollections(): List<CollectionReference> {
        return try {
            firestore.collection(this.path).get().await().map { it.reference.collection(it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }


}

