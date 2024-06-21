package com.saurav.boozebuddy.screens.auth


import android.util.Log

import android.util.Patterns
import android.widget.Toast
import android.widget.Toast.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.AuthViewModel

@Composable
fun LoginPage(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        item {
            ImageView()
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "Welcome \n Back", style = TextStyle(
                        color = colors.secondary,
                        fontWeight = FontWeight.Bold, fontSize = 25.sp
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            "Email".TextFormField(
                value = email,
                onValueChange = { email = it },
                validator = { input ->
                    when {
                        input.isEmpty() -> "Field cannot be empty"
                        !Patterns.EMAIL_ADDRESS.matcher(input).matches() -> "Invalid Email Address"
                        else -> null
                    }
                }
            )
        }
        item {
            "Password".TextFormField(
                value = password,
                showSuffix = true,
                onValueChange = { password = it },
                validator = { input ->
                    when {
                        input.isEmpty() -> "Field cannot be empty"
                        else -> null
                    }
                }
            )
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.CenterEnd

            ) {
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(
                        color = secondaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                CustomButton(navController, email, password, authViewModel)
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Don't Have an Account? Sign Up",
                    style = TextStyle(
                        color = secondaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        navController.navigate(NavRoute.SignUp.route)
                    }
                )
            }
        }

    }
}

@Composable
private fun ImageView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Promotion Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CustomButton(
    navController: NavHostController,
    email: String,
    password: String,
    authViewModel: AuthViewModel
) {
    val isLoading by authViewModel.isLoading
    val context = LocalContext.current
    Button(
        onClick = {
            if (email.isEmpty() || password.isEmpty()) {
                Log.e("EMPTY", "EMAIL AND PASSWORD IS EMPTY")
            } else {
                authViewModel.loginUser(email, password) { success, errorMsg ->
                    if (success) {
                        navController.navigate(NavRoute.BottomNavigation.route) {
                            popUpTo(NavRoute.Login.route) { inclusive = true }
                        }
                    } else {
                        makeText(context, "Login error please check email and password", LENGTH_LONG).show()
                        // Handle login failure, e.g., show error message
                        Log.e("LOGIN_ERROR", "Error Occurred: $errorMsg")
                    }
                }
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            contentColor = primaryColor
        ),
        enabled = !isLoading // Disable button while loading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = primaryColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                modifier = Modifier.padding(horizontal = 40.dp),
                text = "Login",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun String.TextFormField(
    value: String,
    onValueChange: (String) -> Unit,
    showSuffix:Boolean = false,
    validator: (String) -> String?
) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) } // State variable for password visibility

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                errorMessage = validator(it)
            },
            placeholder = { Text(text = this@TextFormField, color = secondaryColor) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 20.dp)
                .border(width = 1.dp, color = secondaryColor, shape = RoundedCornerShape(10.dp)),
            visualTransformation =  if (showSuffix) {
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },

            // Use VisualTransformation.None when passwordVisible is true to show the password
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.
            colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            trailingIcon = {
                if(showSuffix){
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Build else Icons.Filled.Lock,
                            // Use Icons.Filled.Eye when passwordVisible is false to show the eye icon
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }else{
                    null
                }

            }
        )
        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                modifier = Modifier.padding(start = 20.dp, top = 4.dp)
            )
        }
    }
}