@file:Suppress("DEPRECATION")

package com.saurav.boozebuddy.app_navigation


import FavouritesListPage
import SignupPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.saurav.boozebuddy.screens.SplashScreen
import com.saurav.boozebuddy.screens.auth.LoginPage
import com.saurav.boozebuddy.screens.bottom_navigation.BottomNavigationBarMain
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
            composable(NavRoute.SignUp.route) { SignupPage(navController, authViewModel) }
            composable(NavRoute.BottomNavigation.route) { BottomNavigationBarMain(navController, authViewModel, homeViewModel) }
            composable(NavRoute.FavouritesListing.route) { FavouritesListPage(navController) }
            composable(
                route = NavRoute.ProductDetail.route + "/{productId}/{productName}/{productImage}",
                arguments = listOf(
                    navArgument("productId") { type = NavType.StringType },
                    navArgument("productName") { type = NavType.StringType },
                    navArgument("productImage") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                val productName = backStackEntry.arguments?.getString("productName")
                val productImage = backStackEntry.arguments?.getString("productImage")
                ProductsDetailPage(productId, productName, productImage)
            }
        }
    }
}