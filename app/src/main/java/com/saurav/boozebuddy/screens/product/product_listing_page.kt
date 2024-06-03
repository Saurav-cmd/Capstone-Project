package com.saurav.boozebuddy.screens.product

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun ProductListingScreen(navController: NavHostController) {
    Column {
        Text(text = "Product Listing page")
    }
}