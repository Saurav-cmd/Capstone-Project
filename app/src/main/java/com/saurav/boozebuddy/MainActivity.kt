package com.saurav.boozebuddy


import SignupPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.saurav.boozebuddy.screens.auth.LoginPage

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
//                    HomePage()
//                    LoginPage()
                    SignupPage()
                }
            }
        }
    }
}
