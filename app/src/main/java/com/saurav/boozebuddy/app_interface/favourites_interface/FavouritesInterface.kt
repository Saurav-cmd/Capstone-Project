package com.saurav.boozebuddy.app_interface.favourites_interface

interface FavouritesInterface {
    suspend fun addToFavourites(productName:String,brandName:String,productImage:String,callback: (Boolean, String?) -> Unit)
    suspend fun getUserFavourites()
}