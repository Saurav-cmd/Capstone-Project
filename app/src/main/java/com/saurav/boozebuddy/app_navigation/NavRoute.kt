package com.saurav.boozebuddy.app_navigation

sealed class NavRoute(val route: String){
    //
    object Home : NavRoute("home")
    object Detail : NavRoute("detail/{itemId}") {
        fun createRoute(itemId: Int) = "detail/$itemId"
    }
}
