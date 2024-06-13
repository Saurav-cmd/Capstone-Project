package com.saurav.boozebuddy.impl.home_impl

import com.saurav.boozebuddy.api_services.FirestoreHelper

import com.saurav.boozebuddy.app_interface.home_interface.HomeInterface
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.UserModel
import javax.inject.Inject

class HomeImpl @Inject constructor(private val firestoreHelper: FirestoreHelper): HomeInterface{

    override suspend fun fetchBrands(): List<BrandModel> {
       return firestoreHelper.fetchBrandsWithProducts()
    }

    override suspend fun fetchUserInfo(): UserModel? {
        return firestoreHelper.fetchUserInfo()
    }

}