package com.saurav.boozebuddy.api_services

import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

object ErrorHandler {
    fun getErrorMessage(exception: Exception): String {
        return when (exception) {
            is SocketException -> "Network error: ${exception.message}"
            is SocketTimeoutException -> "Connection timeout: ${exception.message}"
            is IOException -> "IO error: ${exception.message}"
            else -> exception.message ?: "Unknown error occurred"
        }
    }
}
