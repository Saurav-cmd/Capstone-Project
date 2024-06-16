package com.saurav.boozebuddy.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.favourites_impl.FavouritesImpl
import com.saurav.boozebuddy.models.UserFavouritesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val favouritesImpl: FavouritesImpl) :
    ViewModel() {
    private val _isStoringFavourites = MutableLiveData<Boolean>()
    val isStoringFavourites: LiveData<Boolean> get() = _isStoringFavourites

    private val _isFetchingUserFavourites = MutableLiveData<Boolean>()
    val isFetchingUserFavourites: LiveData<Boolean> get() = _isFetchingUserFavourites

    private val _userFavourites = MutableLiveData<List<UserFavouritesModel>>()
    val userFavourites: LiveData<List<UserFavouritesModel>> get() = _userFavourites

    fun storeIsFavourites(
        productName: String,
        brandName: String,
        productImage: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        _isStoringFavourites.value = true
        viewModelScope.launch {
            try {
                favouritesImpl.addToFavourites(
                    productName,
                    brandName,
                    productImage,
                    productId,
                    brandId
                ) { success, errMsg ->
                    callback(success, errMsg)
                }
            } catch (e: Exception) {
                ErrorHandler.getErrorMessage(e)
            } finally {
                _isStoringFavourites.value = false
            }
        }
    }

    fun fetchUserFavourites() {
        try {
            viewModelScope.launch {
                _isFetchingUserFavourites.value = true
                _userFavourites.value = favouritesImpl.getUserFavourites()
            }
        } catch (e: Exception) {
            _isFetchingUserFavourites.value = false
            ErrorHandler.getErrorMessage(e)
        } finally {
            _isFetchingUserFavourites.value = false
        }
    }

    fun deleteFavourite(favourite: UserFavouritesModel) {
        viewModelScope.launch {
            try {
                Log.d("FavouritesViewModel", "Attempting to delete favourite with id: ${favourite.id}")
                favouritesImpl.deleteFavourite(favourite) { success, errorMsg ->
                    if (success) {
                        Log.d("FavouritesViewModel", "Successfully deleted favourite with id: ${favourite.id}")
                        // Update the list of favourites after deletion
                        _userFavourites.value = _userFavourites.value?.filter { it.id != favourite.id }
                    } else {
                        Log.e("FavouritesViewModel", "Error deleting favourite: $errorMsg")
                    }
                }
            } catch (e: Exception) {
                ErrorHandler.getErrorMessage(e)
            }
        }
    }
}
