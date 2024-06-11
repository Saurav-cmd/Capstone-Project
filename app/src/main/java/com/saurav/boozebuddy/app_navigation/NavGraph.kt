@file:Suppress("DEPRECATION")

package com.saurav.boozebuddy.app_navigation

import FavouritesListPage
import SignupPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.screens.SplashScreen
import com.saurav.boozebuddy.screens.auth.LoginPage
import com.saurav.boozebuddy.screens.bottom_navigation.BottomNavigationBarMain
import com.saurav.boozebuddy.screens.product.ProductListingScreen
import com.saurav.boozebuddy.screens.product.ProductsDetailPage
import com.saurav.boozebuddy.view_models.AuthViewModel
import com.saurav.boozebuddy.view_models.HomeViewModel

object NavGraph {
    @Composable
    fun Setup(
        navController: NavHostController,
        authViewModel: AuthViewModel,
        homeViewModel: HomeViewModel,
    ) {
        NavHost(navController = navController, startDestination = NavRoute.Splash.route) {
            composable(NavRoute.Splash.route) { SplashScreen(navController, authViewModel) }
            composable(NavRoute.Login.route) { LoginPage(navController, authViewModel) }
            composable(NavRoute.SignUp.route) { SignupPage(navController) }
            composable(NavRoute.BottomNavigation.route) { BottomNavigationBarMain(navController, authViewModel, homeViewModel) }
            composable(NavRoute.ProductDetail.route) { ProductsDetailPage() }
            composable(NavRoute.FavouritesListing.route) { FavouritesListPage(navController) }
            composable(NavRoute.ProductListing.route + "/{products}") { backStackEntry ->
                val productsJson = backStackEntry.arguments?.getString("products")
                val productsListType = object : TypeToken<List<Product>>() {}.type
                val products: List<Product> = Gson().fromJson(productsJson, productsListType)
                ProductListingScreen(navController, products, homeViewModel)
            }

        }
    }
}