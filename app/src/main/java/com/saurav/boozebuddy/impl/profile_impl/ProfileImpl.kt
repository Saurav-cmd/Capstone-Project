package com.saurav.boozebuddy.impl.profile_impl

import com.saurav.boozebuddy.api_services.FirestoreHelper
import com.saurav.boozebuddy.app_interface.profile_interface.ProfileInterface
import javax.inject.Inject

class ProfileImpl @Inject constructor(private val firestoreHelper: FirestoreHelper) : ProfileInterface {
    override suspend fun clearAllData(callback: (Boolean, String) -> Unit) {
        firestoreHelper.clearAllAppData{
            success, errMsg ->
            callback(success, errMsg ?: "Error")
        }
    }
}