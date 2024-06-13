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
    var isCreatingNewUser = mutableStateOf(false)
    var isLoggingOut = mutableStateOf(false)
    fun loginUser(email: String, password: String, callback: (Boolean, String?) -> Unit){
       try {
           isLoading.value = true
           viewModelScope.launch {
               authImpl.userLogin(email, password){ success, errMsg ->
                   run {
                       callback(success, errMsg)
                   }
               }
           }
       }catch (e:Exception){
           isLoading.value = false
       }finally {
           isLoading.value = false
       }
    }

    fun signUpUser(name:String, email:String, password:String, callback: (Boolean, String?) -> Unit){
        try{
            isCreatingNewUser.value = true
            viewModelScope.launch {
                authImpl.userSignUp(name, email, password){success, errMsg ->
                    run {
                        callback(success,errMsg)
                    }
                }
            }
        }catch (e: Exception){
            isCreatingNewUser.value = false
            callback(false, e.toString())
        }finally {
            isCreatingNewUser.value = false
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