@file:Suppress("DEPRECATION")

package com.saurav.boozebuddy.app_navigation

import SignupPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.NavType.ParcelableType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saurav.boozebuddy.models.Item
import com.saurav.boozebuddy.screens.SplashScreen
import com.saurav.boozebuddy.screens.auth.LoginPage
import com.saurav.boozebuddy.screens.bottom_navigation.BottomNavigationBarMain
import com.saurav.boozebuddy.screens.home.HomePage
import com.saurav.boozebuddy.screens.product.ProductListingScreen
import com.saurav.boozebuddy.screens.product.ProductsDetailPage
import getItemById

object NavGraph {
    @Composable
    fun Setup(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavRoute.Splash.route) {
            composable(NavRoute.Splash.route) { SplashScreen(navController) }
            composable(NavRoute.Login.route) { LoginPage(navController) }
            composable(NavRoute.Home.route) { HomePage(navController) }
            composable(NavRoute.SignUp.route) { SignupPage(navController) }
            composable(NavRoute.BottomNavigation.route) { BottomNavigationBarMain(navController) }
            composable(NavRoute.ProductListing.route) { ProductListingScreen(navController)}
            composable(NavRoute.ProductDetail.route) { ProductsDetailPage(navController) }
//            composable(
//                route = "${NavRoute.ProductListing}/{itemId}",
//                arguments = listOf(navArgument("itemId") { type = NavType.IntType })
//            ) { backStackEntry ->
//                val itemId = backStackEntry.arguments?.getInt("itemId")
//                val item = getItemById(itemId) // Implement this function to get the item by ID
//                ProductListingScreen(item)
//            }

        }
    }
}