@file:OptIn(ExperimentalMaterial3Api::class)

package com.saurav.boozebuddy.screens.profile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.UserModel
import com.saurav.boozebuddy.ui.theme.containerColor
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.AuthViewModel
import com.saurav.boozebuddy.view_models.HomeViewModel

@Composable
fun ProfilePage(authViewModel: AuthViewModel, navHostController: NavHostController, homeViewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        item {
            TopContainer(homeViewModel)
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            BottomContainer(authViewModel, navHostController)
        }
    }
}

@Composable
private fun TopContainer(homeViewModel: HomeViewModel) {
    val isLoading by homeViewModel.isFetchingUserInfo.observeAsState(initial = false)
    val userInfo by homeViewModel.userInfo.observeAsState(initial = UserModel())
    Box {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            val path = Path().apply {
                moveTo(0f, size.height)
                quadraticTo(size.width / 2, size.height - 200, size.width, size.height)
                lineTo(size.width, 0f)
                lineTo(0f, 0f)
                close()
            }
            drawPath(path, color = containerColor)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "My Profile",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Surface(
                modifier = Modifier
                    .offset(y = 25.dp) // Half out of the surface
                    .clip(CircleShape)
                    .size(100.dp),
                shape = CircleShape,
                color = Color.White,
                tonalElevation = 20.dp
            ) {
                Image(
                    painter = painterResource(id = ImagesConst.people),
                    contentDescription = "Person Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(30.dp))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(isLoading){
            Text(
                text = "Loading...",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = colors.secondary
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }else{
            Text(
                text = userInfo.name,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = colors.secondary
                ),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        /*Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "UI/UX Designer",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center,
                color = colors.secondary
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )*/
      /*  Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
                color = colors.secondary
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )*/
    }
}


@Composable
fun BottomContainer( authViewModel: AuthViewModel, navHostController: NavHostController) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        BottomContainerDesign(Icons.Default.Info, "Information",  colors.secondary, "Help and Support",color = Color.Black) {
            //call the function here
        }
        BottomContainerDesign(Icons.Default.Settings, "Settings",  colors.secondary, "Settings",color = Color.Black) {
            //call the function here
        }

        BottomContainerDesign(Icons.Default.Info, "App Info", colors.secondary, "App Info", color = Color.Black) {
            //call the function here
        }
        BottomContainerDesign(
            Icons.Default.Lock,
            "Log Out",
            errorColor,
            "Log Out",
            color = errorColor
        ) {
            showDialog = true
        }

        if (showDialog) {
            LogoutDialog(onDismiss = { showDialog = false }, authViewModel = authViewModel,navHostController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomContainerDesign(
    imageVector: ImageVector,
    description: String,
    tint: Color,
    title: String,
    color: Color? = Color.Black,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = lightGrey
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                tint = tint,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = color ?: Color.Black,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}


@Composable
fun LogoutDialog(onDismiss: () -> Unit, authViewModel: AuthViewModel, navHostController: NavHostController) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            Button(onClick = {
                authViewModel.logOutUser().run {
                    navHostController.navigate(NavRoute.Login.route){
                        popUpTo(NavRoute.BottomNavigation.route) { inclusive = true }
                    }
                }
                // Dismiss the dialog after logging out
                onDismiss()
            }, colors = ButtonDefaults.buttonColors(
                containerColor = secondaryColor,
                contentColor = primaryColor
            ),) {
                if(authViewModel.isLoggingOut.value){
                    CircularProgressIndicator()
                }else{
                    Text(text = "Yes",)
                }

            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            },  colors = ButtonDefaults.buttonColors(
                containerColor = errorColor,
                contentColor = primaryColor
            ),) {
                Text(text = "No")
            }
        },
        title = {
            Text(text = "Are you sure you want to logout?", textAlign = TextAlign.Center, color = errorColor)
        },
    )
}

