package com.saurav.boozebuddy.screens.product

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.models.Item

@Composable
fun ProductListingScreen(item: Item?) {
    item?.let {
        // Use the item data here
        Column {
            Text(text = "Product Listing page")
            Text(text = "ID: ${it.id}, Text: ${it.text}")
        }
    } ?: run {
        // Handle null item case
        // You can navigate back or display an error message
    }
}
