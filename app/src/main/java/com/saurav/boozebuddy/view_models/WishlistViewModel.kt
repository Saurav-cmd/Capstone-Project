package com.saurav.boozebuddy.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.wishlist_impl.WishlistImplementation
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.WishlistModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val wishlistImplementation: WishlistImplementation) : ViewModel() {
    private val _isStoringWishlist = MutableLiveData<Boolean>()
    val isStoringWishList: LiveData<Boolean> get() = _isStoringWishlist

    private val _isFetchingWishList = MutableLiveData<Boolean>()
    val isFetchingWishList: LiveData<Boolean> get() = _isFetchingWishList

    private val _wishListValue = MutableLiveData<List<WishlistModel>>()
    val wishListValue: LiveData<List<WishlistModel>> get() = _wishListValue

    private val _isDeletingWishlist = MutableLiveData<Boolean>()
    val isDeletingWishList: LiveData<Boolean> get() = _isDeletingWishlist

    private val _isDeletingWishlistProduct = MutableLiveData<Boolean>()
    val isDeletingWishlistProduct: LiveData<Boolean> get() = _isDeletingWishlistProduct

    init {
        fetchWishlist()
    }

    fun storeWishList(
        folderName: String,
        productName: String,
        productDesc: String,
        productImage: String,
        brandName: String,
        productId: String,
        brandId: String,
        product: Product,
        callback: (Boolean, String?) -> Unit
    ) {
        try {
            _isStoringWishlist.value = true
            viewModelScope.launch {
                if (folderName.isNotEmpty()) {
                    wishlistImplementation.addWishList(
                        folderName,
                        productName,
                        productDesc,
                        productImage,
                        brandName,
                        productId,
                        brandId,
                        product
                    ) { success, errMsg ->
                        callback(success, errMsg)
                    }
                } else {
                    callback(false, "Wishlist name cannot be empty")
                }
            }
        } catch (e: Exception) {
            _isStoringWishlist.value = false
            ErrorHandler.getErrorMessage(e)
        } finally {
            _isStoringWishlist.value = false
        }
    }


    fun fetchWishlist() {
        _isFetchingWishList.value = true
        viewModelScope.launch {
            try {
                val wishlist = wishlistImplementation.getWishList()
                _wishListValue.value = wishlist
            } catch (e: Exception) {
                _isFetchingWishList.value = false
                ErrorHandler.getErrorMessage(e)
            } finally {
                _isFetchingWishList.value = false
            }
        }
    }

    fun deleteWishList(wishListId: String, callback: (Boolean, String?) -> Unit){
        try{
            _isDeletingWishlist.value = true
            viewModelScope.launch {
                wishlistImplementation.deleteWishList(wishListId){
                    success, errMsg ->
                    callback(success,errMsg)
                }
            }
        }catch (e:Exception){
            _isDeletingWishlist.value = false
            ErrorHandler.getErrorMessage(e)
        }finally {
            _isDeletingWishlist.value = false
        }
    }

    fun deleteWishListProduct(wishListId:String, wishListProductId: String,wishListName:String ,callback: (Boolean, String?) -> Unit){
        try{
            _isDeletingWishlistProduct.value = true
            viewModelScope.launch {
                wishlistImplementation.deleteWishlistProduct(wishListId, wishListProductId,wishListName ,callback)
            }
        }catch (e:Exception){
            _isDeletingWishlistProduct.value = false
            ErrorHandler.getErrorMessage(e)
        }finally {
            _isDeletingWishlistProduct.value = false
        }
    }


}
