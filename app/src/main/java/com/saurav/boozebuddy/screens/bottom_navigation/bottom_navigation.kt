package com.saurav.boozebuddy.screens.bottom_navigation

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var selectedIndex by remember{ mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        bottomBar = {
            AnimatedNavigationBar(
                modifier = Modifier.height(64.dp),
                selectedIndex = selectedIndex,
                ballAnimation = Straight(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = primaryColor,
                ballColor = primaryColor,
            )
            {
                navigationBarItems.forEach {
                        item -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            selectedIndex = item.ordinal
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        modifier = Modifier.size(26.dp),
                        imageVector = item.icon,
                        contentDescription = "Icon",
                        tint = if(selectedIndex == item.ordinal) Color.White else bottomNavUnSelectedIconColor
                    )
                }
                }
            }
        }
    ) {
        when (selectedIndex) {
            0 -> {
                HomePage(navController)
            }
            1 -> {
                NotificationPage()
            }
            2 ->{
                CartPage()
            }
            else -> {
                ProfilePage()
            }
        }
    }
}

enum class NavigationBarItems(val icon: ImageVector){
    Home(icon = Icons.Default.Home),
    Notification(icon = Icons.Default.Notifications),
    Cart(icon = Icons.Default.ShoppingCart),
    Person(icon = Icons.Default.Person),
}
