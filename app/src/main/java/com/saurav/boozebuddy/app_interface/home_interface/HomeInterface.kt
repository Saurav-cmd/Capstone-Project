package com.saurav.boozebuddy.app_interface.home_interface

import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.UserModel

interface HomeInterface {
    suspend fun fetchBrands():List<BrandModel>
    suspend fun fetchUserInfo(): UserModel?
}