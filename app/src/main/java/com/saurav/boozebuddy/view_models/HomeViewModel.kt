package com.saurav.boozebuddy.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.impl.home_impl.HomeImpl
import com.saurav.boozebuddy.models.BrandModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeImpl: HomeImpl) : ViewModel() {
    private val _brands = MutableLiveData<List<BrandModel>>()
    val brands: LiveData<List<BrandModel>> get() = _brands

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        Log.e("Fetching Brands", "Fetching brands")
        fetchBrands()
    }

    private fun fetchBrands() {
        _isLoading.value = true
        viewModelScope.launch {
            _brands.value = homeImpl.fetchBrands()
            _isLoading.value = false
        }
    }
}
