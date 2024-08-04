package com.saurav.boozebuddy.app_interface.places_interface

import com.saurav.boozebuddy.models.NearbyPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {
    @GET("place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): NearbyPlacesResponse
}