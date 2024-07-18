package com.saurav.boozebuddy.app_interface.profile_interface

interface ProfileInterface {
    suspend fun clearAllData(callback: (Boolean,String) -> Unit)
}