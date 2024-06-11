package com.saurav.boozebuddy.dao.home_dao

import com.saurav.boozebuddy.models.BrandModel

interface HomeDao {
    suspend fun fetchBrands():List<BrandModel>
}