package com.saurav.boozebuddy.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.favourites_impl.FavouritesImpl
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.UserFavouritesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    fun storeIsFavourites(
        productName: String,
        brandName: String,
        productImage: String,
        productId: String,
        brandId: String,
        product: Product,
        callback: (Boolean, String?) -> Unit
    ) {
        _isStoringFavourites.postValue(true)
        viewModelScope.launch{
            try {
                favouritesImpl.addToFavourites(
                    productName,
                    brandName,
                    productImage,
                    productId,
                    brandId,
                    product
                ) { success, errMsg ->
                    callback(success, errMsg)
                }
            } catch (e: Exception) {
                ErrorHandler.getErrorMessage(e)
            } finally {
                _isStoringFavourites.postValue(false)
            }
        }
    }

    fun fetchUserFavourites() {
        viewModelScope.launch {
            try {
                _isFetchingUserFavourites.postValue(true)
                val favourites = withContext(Dispatchers.IO) {
                    favouritesImpl.getUserFavourites()
                }
                _userFavourites.value = favourites // Update LiveData on the main thread
                _isFetchingUserFavourites.postValue(false)
            } catch (e: Exception) {
                _isFetchingUserFavourites.postValue(false)
                ErrorHandler.getErrorMessage(e)
            } finally {
                _isFetchingUserFavourites.postValue(false)
            }
        }
    }


    fun deleteFavourite(
        favouriteId: String,
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            _deletingFavourites.postValue(true)
            viewModelScope.launch {
                favouritesImpl.deleteUserFavourites(favouriteId) { success, errorMsg ->
                    callback(success, errorMsg)
                }
                _deletingFavourites.postValue(false)
            }
        } catch (e: Exception) {
            _deletingFavourites.postValue(false)
            ErrorHandler.getErrorMessage(e)
        } finally {
            _deletingFavourites.postValue(false)
        }
    }

    //check if the user has already favourite or not
    fun checkIfAlreadyFavourite(productId: String):Boolean{
        fetchUserFavourites()
       val favouritesList = _userFavourites.value
        return favouritesList?.any{ it.productId == productId} ?: false
    }
}
