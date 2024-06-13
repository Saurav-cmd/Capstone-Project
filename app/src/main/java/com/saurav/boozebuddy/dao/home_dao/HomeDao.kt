package com.saurav.boozebuddy.dao.home_dao

import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.UserModel

interface HomeDao {
    suspend fun fetchBrands():List<BrandModel>
    suspend fun fetchUserInfo(): UserModel?
}