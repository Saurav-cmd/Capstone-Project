package com.saurav.boozebuddy.view_models

import androidx.lifecycle.ViewModel
import com.saurav.boozebuddy.app_interface.places_interface.PlacesApiService
import com.saurav.boozebuddy.models.PlaceResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NearByLocationViewModel @Inject constructor(
    private val placesApiService: PlacesApiService
) : ViewModel() {

    suspend fun fetchNearbyPlaces(location: android.location.Location): List<PlaceResult> {
        return try {
            val locationString = "${location.latitude},${location.longitude}"
            val response = placesApiService.getNearbyPlaces(
                location = locationString,
                radius = 5000,
                type = "liquor_store",
                apiKey = "AIzaSyAELUXUvZ3zzBOZXuNc0EcdP_rUhqAxZ3Q"
            )
            response.results
        } catch (e: Exception) {
            emptyList()
        }
    }
}