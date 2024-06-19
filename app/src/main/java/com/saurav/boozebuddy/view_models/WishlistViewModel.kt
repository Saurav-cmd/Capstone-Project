package com.saurav.boozebuddy.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.wishlist_impl.WishlistImplementation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(val wishlistImplementation: WishlistImplementation) : ViewModel() {
    private val _isStoringWishlist = MutableLiveData<Boolean>()
    val isStoringWishList : LiveData<Boolean> get() = _isStoringWishlist

    fun storeWishList(
        folderName: String,
        productName: String,
        productDesc: String,
        productImage: String,
        brandName: String,
        productId: String,
        brandId: String,
        callback: (Boolean, String?) -> Unit
    ){
        try{
            viewModelScope.launch {
                _isStoringWishlist.postValue(true)
                wishlistImplementation.addWishList(folderName, productName, productDesc,productImage, brandName, productId, brandId){
                    success, errMsg ->
                    callback(success, errMsg)
                }
                _isStoringWishlist.postValue(false)
            }
        }catch (e: Exception){
            _isStoringWishlist.postValue(false)
            ErrorHandler.getErrorMessage(e)
        }
    }

}