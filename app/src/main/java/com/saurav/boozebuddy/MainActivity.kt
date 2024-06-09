package com.saurav.boozebuddy
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.saurav.boozebuddy.app_navigation.NavGraph
import com.saurav.boozebuddy.ui.theme.BoozeBuddyTheme
import com.saurav.boozebuddy.ui.theme.bodyColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoozeBuddyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = bodyColor,
                ) {
                   MyApp()

                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavGraph.Setup(navController = navController)
}
