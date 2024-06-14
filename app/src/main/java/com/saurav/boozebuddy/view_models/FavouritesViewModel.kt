package com.saurav.boozebuddy.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.favourites_impl.FavouritesImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val favouritesImpl: FavouritesImpl) : ViewModel() {
    private val _isStoringFavourites = MutableLiveData<Boolean>()
    val isStoringFavourites: LiveData<Boolean> get() = _isStoringFavourites

    fun storeIsFavourites(
        productName: String,
        brandName: String,
        productImage: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        Log.e("Viewmodel favourites: ", "$productId $brandId")
        try{
           _isStoringFavourites.value = true
           viewModelScope.launch {
               favouritesImpl.addToFavourites(productName, brandName, productImage, productId, brandId){ success, errMsg ->
                   run {
                      callback(success, errMsg)
                   }

               }
           }
        }catch (e:Exception){
            _isStoringFavourites.value = false
            ErrorHandler.getErrorMessage(e)
        }finally {
            _isStoringFavourites.value = false
        }
    }
}