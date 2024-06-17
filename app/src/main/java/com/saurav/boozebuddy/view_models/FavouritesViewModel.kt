package com.saurav.boozebuddy.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.favourites_impl.FavouritesImpl
import com.saurav.boozebuddy.models.UserFavouritesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val _deletingFavourites = MutableLiveData<Boolean>()
    val deletingFavourites:LiveData<Boolean> get() = _deletingFavourites

    init {
        fetchUserFavourites()
    }

    fun storeIsFavourites(
        productName: String,
        brandName: String,
        productImage: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        _isStoringFavourites.value = true
        viewModelScope.launch(Dispatchers.IO) {
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
            _isFetchingUserFavourites.value = true
            viewModelScope.launch(Dispatchers.IO) {
                _userFavourites.value = favouritesImpl.getUserFavourites()
            }
        } catch (e: Exception) {
            _isFetchingUserFavourites.value = false
            ErrorHandler.getErrorMessage(e)
        } finally {
            _isFetchingUserFavourites.value = false
        }
    }

    fun deleteFavourite(
        favouriteId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            _deletingFavourites.value = true
            viewModelScope.launch(Dispatchers.IO) {
                favouritesImpl.deleteUserFavourites(favouriteId) { success, errorMsg ->
                    callback(success, errorMsg)
                }
            }
        } catch (e: Exception) {
            _deletingFavourites.value = false
            ErrorHandler.getErrorMessage(e)
        } finally {
            _deletingFavourites.value = false
        }
    }

    //check if the user has already favourite or not
    fun checkIfAlreadyFavourite(productId: String):Boolean{
        fetchUserFavourites()
       val favouritesList = _userFavourites.value
        return favouritesList?.any{ it.productId == productId} ?: false
    }
}
