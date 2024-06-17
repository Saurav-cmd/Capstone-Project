package com.saurav.boozebuddy.view_models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.home_impl.HomeImpl
import com.saurav.boozebuddy.models.BannerModel
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.models.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeImpl: HomeImpl) : ViewModel() {
    private val _brands = MutableLiveData<List<BrandModel>>()
    val brands: LiveData<List<BrandModel>> get() = _brands

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _filteredProduct = MutableLiveData<List<Product>>()
    val filteredProduct: LiveData<List<Product>> get() = _filteredProduct

    private val _userInfo = MutableLiveData<UserModel>()
    val userInfo : LiveData<UserModel> get() = _userInfo

    private val _isFetchingUserInfo = MutableLiveData<Boolean>()
    val isFetchingUserInfo: LiveData<Boolean> get() = _isFetchingUserInfo

    private val _banners = MutableLiveData<List<BannerModel>>()
    val banners: LiveData<List<BannerModel>> get() = _banners

    private val _isFetchingBanners = MutableLiveData<Boolean>()
    val isFetchingBanners: LiveData<Boolean> get() = _isFetchingBanners

    init {
        fetchBrands()
        fetchBanners()
    }

    //function to fetch the brands
    private fun fetchBrands() {
        try {
            _isLoading.value = true
            viewModelScope.launch {
                _brands.value = homeImpl.fetchBrands()
            }
        }catch (e:Exception){
            _isLoading.value = false
            ErrorHandler.getErrorMessage(e)
        }finally {
            _isLoading.value = false
        }
    }

    //Function to filter the products by product name
    fun filterProducts(products: List<Product>, query: String){
        val filteredList = mutableListOf<Product>()
        if (query.isNotBlank()) {
            for (product in products) {
                if (product.productName?.contains(query, ignoreCase = true) == true) {
                    filteredList.add(product)
                }
            }
        }
        _filteredProduct.value = filteredList
    }

    //Function used to fetch the logged in user info
    fun fetchUserInfo(){
        try{
            _isFetchingUserInfo.value = true
          viewModelScope.launch {
            _userInfo.value =  homeImpl.fetchUserInfo()
          }
        }catch (e: Exception){
            _isFetchingUserInfo.value = false
            ErrorHandler.getErrorMessage(e)
        }finally {
            _isFetchingUserInfo.value = false
        }
    }

    // Function to fetch banners
    private fun fetchBanners() {
        try {
            _isFetchingBanners.value = true
           viewModelScope.launch {
               homeImpl.fetchBanner { banners ->
                   _banners.postValue(banners ?: emptyList())
                   _isFetchingBanners.postValue(false)
               }
           }
        } catch (e: Exception) {
            _isFetchingBanners.value = false
            ErrorHandler.getErrorMessage(e)
        }
    }



    fun clearFilteredProducts() {
        _filteredProduct.value = emptyList()
    }

    //This functions give us info like goodAfternoon, Good morning, good evening...
    fun getGreetingMessage(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            in 18..21 -> "Good Evening"
            else -> "Good Night"
        }
    }
}


