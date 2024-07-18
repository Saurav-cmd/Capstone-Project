package com.saurav.boozebuddy.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.impl.profile_impl.ProfileImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileImpl: ProfileImpl):ViewModel() {

    private val _isDeletingAllData = MutableLiveData<Boolean>()
    val isDeletingAllData: LiveData<Boolean> get() = _isDeletingAllData

    fun deleteAllData(callback: (Boolean, String?) -> Unit){
        try{
            _isDeletingAllData.value = true
            viewModelScope.launch {
                profileImpl.clearAllData{
                    success,errMsg ->
                    run {
                        callback(success, errMsg)
                    }
                }
            }
        }catch (e:Exception){
            _isDeletingAllData.value = false
        }finally {
            _isDeletingAllData.value = false
        }

    }}