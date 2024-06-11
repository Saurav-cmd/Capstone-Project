package com.saurav.boozebuddy.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.api_services.ErrorHandler
import com.saurav.boozebuddy.impl.home_impl.HomeImpl
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeImpl: HomeImpl) : ViewModel() {
    private val _brands = MutableLiveData<List<BrandModel>>()
    val brands: LiveData<List<BrandModel>> get() = _brands

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _filteredProduct = MutableLiveData<List<Product>>()
    val filteredProduct: LiveData<List<Product>> get() = _filteredProduct

    init {
        Log.e("Fetching Brands", "Fetching brands")
        fetchBrands()
    }

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
}
