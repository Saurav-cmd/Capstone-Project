package com.saurav.boozebuddy.api_services

import com.google.firebase.auth.FirebaseAuth

class FirebaseHelper(private val auth: FirebaseAuth) {

    //Function to login user using email and password
    fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, null)
                    } else {
                        val exception = task.exception
                        callback(false, ErrorHandler.getErrorMessage(exception as Exception))
                    }
                }
        } catch (e: Exception) {
            callback(false, ErrorHandler.getErrorMessage(e))
        }
    }

    //Function to logout user
    fun signOutUser() {
        try {
            auth.signOut()
        } catch (e: Exception) {
           ErrorHandler.getErrorMessage(e)
        }
    }

    //Function to check if the user has already logged in or not
    fun userAlreadyLoggedIn(): Boolean {
        return try {
            auth.currentUser != null
        } catch (e: java.lang.Exception) {
            ErrorHandler.getErrorMessage(e)
            false
        }
    }
}
