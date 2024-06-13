package com.saurav.boozebuddy
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.saurav.boozebuddy.app_navigation.NavGraph
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.screens.auth.LoginPage
import com.saurav.boozebuddy.screens.bottom_navigation.BottomNavigationBarMain
import com.saurav.boozebuddy.screens.home.HomePage
import com.saurav.boozebuddy.ui.theme.BoozeBuddyTheme
import com.saurav.boozebuddy.view_models.AuthViewModel
import com.saurav.boozebuddy.view_models.FavouritesViewModel
import com.saurav.boozebuddy.view_models.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoozeBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.primary,
                ) {
                   MyApp(authViewModel, homeViewModel, favouritesViewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    favouritesViewModel: FavouritesViewModel
) {
    val navController = rememberNavController()
    NavGraph.Setup(navController = navController, authViewModel, homeViewModel, favouritesViewModel)
}
