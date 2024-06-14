package com.saurav.boozebuddy.api_services

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

object ErrorHandler {
    fun getErrorMessage(exception: Exception): String {
        return when (exception) {
            is SocketException -> "Network error: ${exception.message}"
            is SocketTimeoutException -> "Connection timeout: ${exception.message}"
            is IOException -> "IO error: ${exception.message}"
            is FirebaseAuthException -> "Firebase authentication error: ${exception.message}"
            is FirebaseFirestoreException -> "Firestore error: ${exception.message}"
            else -> exception.message ?: "Unknown error occurred"
        }
    }
}

