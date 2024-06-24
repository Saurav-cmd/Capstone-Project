package com.saurav.boozebuddy
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.saurav.boozebuddy.app_navigation.NavGraph
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.BoozeBuddyTheme
import com.saurav.boozebuddy.view_models.AuthViewModel
import com.saurav.boozebuddy.view_models.FavouritesViewModel
import com.saurav.boozebuddy.view_models.HomeViewModel
import com.saurav.boozebuddy.view_models.WishlistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private val wishlistViewModel: WishlistViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        setContent {
            BoozeBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.primary,
                ) {
                   MyApp(authViewModel, homeViewModel, favouritesViewModel, wishlistViewModel)
                }
            }
        }
    }

    private fun requestNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}

@Composable
fun MyApp(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    favouritesViewModel: FavouritesViewModel,
    wishlistViewModel: WishlistViewModel
) {
    val navController = rememberNavController()
    NavGraph.Setup(navController = navController, authViewModel, homeViewModel, favouritesViewModel, wishlistViewModel)
}