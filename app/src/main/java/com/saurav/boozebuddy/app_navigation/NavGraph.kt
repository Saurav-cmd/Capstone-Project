@file:Suppress("DEPRECATION")

package com.saurav.boozebuddy.app_navigation


import FavouritesListPage
import SignupPage
import android.util.Log
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
import com.saurav.boozebuddy.screens.wishlist.WishlistProductListingPage
import com.saurav.boozebuddy.view_models.AuthViewModel
import com.saurav.boozebuddy.view_models.FavouritesViewModel
import com.saurav.boozebuddy.view_models.HomeViewModel
import com.saurav.boozebuddy.view_models.WishlistViewModel

object NavGraph {
    @Composable
    fun Setup(
        navController: NavHostController,
        authViewModel: AuthViewModel,
        homeViewModel: HomeViewModel,
        favouritesViewModel: FavouritesViewModel,
        wishlistViewModel: WishlistViewModel
    ) {
        NavHost(navController = navController, startDestination = NavRoute.Splash.route) {
            composable(NavRoute.Splash.route) { SplashScreen(navController, authViewModel) }
            composable(NavRoute.Login.route) { LoginPage(navController, authViewModel) }
            composable(NavRoute.SignUp.route) { SignupPage(navController, authViewModel) }
            composable(NavRoute.BottomNavigation.route) { BottomNavigationBarMain(navController, authViewModel, homeViewModel, wishlistViewModel) }
            composable(NavRoute.FavouritesListing.route) { FavouritesListPage(navController, favouritesViewModel) }
            composable(NavRoute.ProductListing.route + "/{products}/{brandName}/{brandId}") { backStackEntry ->
                val productsJson = backStackEntry.arguments?.getString("products")
                val brandName = backStackEntry.arguments?.getString("brandName")
                val brandId = backStackEntry.arguments?.getString("brandId")

                val productsListType = object : TypeToken<List<Product>>() {}.type
                val products: List<Product> = Gson().fromJson(productsJson, productsListType)

                ProductListingScreen(navController, products, brandName ?: "", brandId ?: "", homeViewModel)
            }
            composable(
                route = NavRoute.ProductDetail.route + "/{productData}/{brandName}/{brandId}",
            ) { backStackEntry ->
                val productJson = backStackEntry.arguments?.getString("productData")
                val brandNameJson = backStackEntry.arguments?.getString("brandName")
                val brandIdJson = backStackEntry.arguments?.getString("brandId")

                val productType = object : TypeToken<Product>() {}.type
                val productData: Product = Gson().fromJson(productJson, productType)
                val brandName: String = Gson().fromJson(brandNameJson, String::class.java)
                val brandId: String = Gson().fromJson(brandIdJson, String::class.java)

                Log.e("When passing the data: ", "$brandId ${productData.productId}")
                ProductsDetailPage(navController, favouritesViewModel, productData, brandName, brandId, wishlistViewModel)
            }
            composable(NavRoute.WishListProductListingPage.route) { WishlistProductListingPage(navController) }

        }
    }
}