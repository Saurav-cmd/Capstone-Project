package com.saurav.boozebuddy.screens.wishlist


import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.WishlistModel
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.WishlistViewModel
import java.util.Locale


@Composable
fun WishListPage(wishlistViewModel: WishlistViewModel, navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        wishlistViewModel.fetchWishlist()
    }

    val isFetchingWishList by wishlistViewModel.isFetchingWishList.observeAsState(initial = false)
    val wishListData by wishlistViewModel.wishListValue.observeAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        TopContainer()
        if (isFetchingWishList) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = secondaryColor,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Loading Wishlist...", color = secondaryColor, fontSize = 18.sp)
            }
        } else if (wishListData.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No Wishlist to show", color = errorColor, textAlign = TextAlign.Center)
            }
        } else {
            DetailContainer(wishListData, wishlistViewModel, navHostController)
        }

    }
}

@Composable
private fun TopContainer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = "WishList",
            style = TextStyle(
                color = colors.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun DetailContainer(wishListData: List<WishlistModel>, wishlistViewModel: WishlistViewModel, navHostController: NavHostController) {
    val isDeletingWishList by wishlistViewModel.isDeletingWishList.observeAsState(initial = false)
    var showDialog by remember {
        mutableStateOf(false)
    }
    LazyColumn(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 5.dp, top = 10.dp)
    ) {
        items(wishListData.size) { index ->
            val data = wishListData[index]
            Row(
                modifier = Modifier.fillMaxWidth().clickable {
                    val jsonEncoded = Uri.encode(Gson().toJson(data.wishListProducts))
                    navHostController.navigate("${NavRoute.WishListProductListingPage.route}/$jsonEncoded") {
                        launchSingleTop = true
                    }
                },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    data.wishName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    color = colors.secondary,
                    fontSize = 18.sp
                )
               if(isDeletingWishList){
                   CircularProgressIndicator(
                       color = secondaryColor
                   )
               }else {
                   IconButton(onClick = {
                       showDialog = true
                   }) {
                       Icon(
                           imageVector = Icons.Default.Delete,
                           contentDescription = "Delete",
                           tint = Color.Red
                       )
                   }
               }
            }
            Divider()


            if (showDialog) {
                DeleteDialog(onDismiss = { showDialog = false }, wishlistViewModel = wishlistViewModel, data.wishId)
            }
        }
    }
}

@Composable
fun DeleteDialog(
    onDismiss: () -> Unit,
    wishlistViewModel: WishlistViewModel,
    wishListId: String
) {
    val isDeleting by wishlistViewModel.isDeletingWishList.observeAsState(initial = false)
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            Button(
                onClick = {
                    wishlistViewModel.deleteWishList(wishListId) { success, errMsg ->
                        if (success) {
                            Toast
                                .makeText(context, "Successfully deleted", Toast.LENGTH_SHORT)
                                .show()
                           wishlistViewModel.fetchWishlist()
                        } else {
                            Toast
                                .makeText(context, "Error cannot delete $errMsg", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    // Dismiss the dialog after logging out
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    contentColor = primaryColor
                ),
            ) {
                if (isDeleting) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Yes")
                }

            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = errorColor,
                    contentColor = primaryColor
                ),
            ) {
                Text(text = "No")
            }
        },
        title = {
            Text(
                text = "Are you sure you want to delete?",
                textAlign = TextAlign.Center,
                color = errorColor
            )
        },
    )
}
