package com.saurav.boozebuddy.screens.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.utils.noRippleClickable
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.screens.cart.CartPage
import com.saurav.boozebuddy.screens.home.HomePage
import com.saurav.boozebuddy.screens.notification.NotificationPage
import com.saurav.boozebuddy.screens.profile.ProfilePage
import com.saurav.boozebuddy.ui.theme.bottomNavUnSelectedIconColor
import com.saurav.boozebuddy.ui.theme.primaryColor

@Composable
fun BottomNavigationBarMain(navController: NavHostController) {
    AnimatedNavBar(navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedNavBar(navController: NavHostController) {
    val navigationBarItems = remember { NavigationBarItems.values() }
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier.height(64.dp),
                selectedIndex = selectedIndex,
                ballAnimation = Straight(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = colors.secondary,
                ballColor = colors.secondary,
            ) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                selectedIndex = item.ordinal
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(26.dp),
                            imageVector = item.icon,
                            contentDescription = "Icon",
                            tint = if (selectedIndex == item.ordinal) colors.primary else bottomNavUnSelectedIconColor
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedIndex) {
                0 -> {
                    HomePage(navController)
                }
                1 -> {
                    NotificationPage()
                }
                2 -> {
                    CartPage()
                }
                else -> {
                    ProfilePage()
                }
            }
        }
    }
}

enum class NavigationBarItems(val icon: ImageVector) {
    Home(icon = Icons.Default.Home),
    Notification(icon = Icons.Default.Notifications),
    Cart(icon = Icons.Default.ShoppingCart),
    Person(icon = Icons.Default.Person),
}
