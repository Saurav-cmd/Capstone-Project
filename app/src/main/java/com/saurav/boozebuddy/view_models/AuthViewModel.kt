package com.saurav.boozebuddy.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurav.boozebuddy.impl.auth_impl.AuthImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//ViewModel: AuthViewModel uses AuthImpl for login and logout operations.
// It manages the loading state using a mutableStateOf.
@HiltViewModel
class AuthViewModel @Inject constructor(private val authImpl: AuthImpl) : ViewModel() {

    var isLoading = mutableStateOf(false)
    var isLoggingOut = mutableStateOf(false)
    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit){
        isLoading.value = true
        viewModelScope.launch {
            authImpl.userLogin(email, password){ success, errMsg ->
                run {
                    isLoading.value = false
                    callback(success, errMsg)
                }
            }
        }
    }

    fun logOutUser(){
        isLoggingOut.value = true
        viewModelScope.launch {
            authImpl.userLogout().run {
                isLoggingOut.value = false

            }
        }
    }

    fun checkIfUserAlreadyLoggedIn() : Boolean {
        return authImpl.userAlreadyLoggedIn()
    }
}