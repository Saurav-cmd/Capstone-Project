package com.saurav.boozebuddy.app_interface.favourites_interface

import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.UserFavouritesModel

interface FavouritesInterface {
    suspend fun addToFavourites(productName:String, brandName:String, productImage:String, productId: String,
                                brandId: String, product: Product,callback: (Boolean, String?) -> Unit)
    suspend fun getUserFavourites() : List<UserFavouritesModel>

    suspend fun deleteUserFavourites(favouriteId: String, callback: (Boolean, String?) -> Unit)
}