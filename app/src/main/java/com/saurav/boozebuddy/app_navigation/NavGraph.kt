package com.saurav.boozebuddy.app_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saurav.boozebuddy.screens.SplashScreen
import com.saurav.boozebuddy.screens.auth.LoginPage
import com.saurav.boozebuddy.screens.bottom_navigation.BottomNavigationBarMain
import com.saurav.boozebuddy.screens.home.HomePage
import com.saurav.boozebuddy.screens.product.ProductListingScreen

object NavGraph {
    @Composable
    fun Setup(navController: NavHostController) {
        NavHost(navController = navController, startDestination = NavRoute.Splash.route) {
            composable(NavRoute.Splash.route) { SplashScreen(navController) }
            composable(NavRoute.Login.route) { LoginPage(navController) }
            composable(NavRoute.Home.route) { HomePage(navController) }
            composable(NavRoute.BottomNavigation.route) { BottomNavigationBarMain(navController) }
            composable(NavRoute.ProductListing.route){ ProductListingScreen(navController)}
        }
    }
}