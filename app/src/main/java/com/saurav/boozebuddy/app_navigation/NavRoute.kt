package com.saurav.boozebuddy.app_navigation

sealed class NavRoute(val route: String) {
    object Splash : NavRoute("splash")
    object Login : NavRoute("login")
    object SignUp: NavRoute("sign_up")
    object Home : NavRoute("home")
    object BottomNavigation : NavRoute("bottom_navigation")
    object ProductListing: NavRoute("product_listing")

}