package com.saurav.boozebuddy.screens.wishlist


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils
import com.saurav.boozebuddy.models.WishListProducts
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.WishlistViewModel

@Composable
fun WishlistProductListingPage(navHostController: NavHostController,wishListData: List<WishListProducts>, wishlistViewModel: WishlistViewModel, wishId:String, wishListName:String ) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopContainer(navController = navHostController)
        WishListProductsList(
            navController = navHostController,
            wishListData = wishListData,
            wishlistViewModel,
            wishId,
            wishListName
        )
    }
}

@Composable
private fun TopContainer(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = ThemeUtils.colors.secondary,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    navController.popBackStack()
                }
        )

        Text(
            text = "Wishlist Products",
            style = TextStyle(
                color = ThemeUtils.colors.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun WishListProductsList(
    navController : NavHostController,
    wishListData: List<WishListProducts>,
    wishlistViewModel: WishlistViewModel,
    wishId:String,
    wishListName:String
) {
    LazyColumn(content = {
        items(count = wishListData.size) { index ->
            WishListProductDesign(wishListData[index], wishlistViewModel, wishId, wishListName)
            Spacer(modifier = Modifier.height(15.dp))
        }
    })
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun WishListProductDesign(
    wishListProduct: WishListProducts,
    wishlistViewModel: WishlistViewModel,
    wishId:String,
    wishListName:String
) {
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var showModalBottomSheet by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable {
                showModalBottomSheet = true
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            width = 1.5.dp,
                            color = ThemeUtils.colors.secondary,
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    val painter =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = wishListProduct.productImage)
                                .apply {
                                    crossfade(true)
                                    placeholder(ImagesConst.appLogo)
                                    error(ImagesConst.appLogo)
                                }
                                .build()
                        )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column {
                    Text(
                        text = wishListProduct.productName,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = secondaryColor
                        ),
                        modifier = Modifier.fillMaxSize(fraction = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = wishListProduct.brandName.removePrefix("\"").removeSuffix("\""),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = ThemeUtils.colors.secondary
                        )
                    )
                }
            }
//            Icon(
//                painter = painterResource(id = R.drawable.delete),
//                contentDescription = "Delete",
//                tint = Color.Red,
//                modifier = Modifier
//                    .size(24.dp)
//                    .clickable {
//                        showDialog = true
//                    }
//            )

            if (showDialog) {
                DeleteDialog(onDismiss = { showDialog = false }, wishlistViewModel,wishListProduct.wishListProductId, wishId,  wishListName)
            }
        }
    }

    if (showModalBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { showModalBottomSheet = false}) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp).clip(shape = RoundedCornerShape(2.dp))
                ) {
                    val painter =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = wishListProduct.productImage)
                                .apply {
                                    crossfade(true)
                                    placeholder(ImagesConst.appLogo)
                                    error(ImagesConst.appLogo)
                                }
                                .build()
                        )

                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = wishListProduct.productName,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = secondaryColor
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = wishListProduct.brandName.removePrefix("\"").removeSuffix("\""),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = ThemeUtils.colors.secondary
                    )
                )
                Text(
                    text = wishListProduct.productOrigin,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = ThemeUtils.colors.secondary
                    )
                )
            }
        }
    }
}

@Composable
private fun DeleteDialog(
    onDismiss: () -> Unit,
    wishlistViewModel: WishlistViewModel,
    wishListProductId: String,
    wishId:String,
    wishListName:String
) {
    val isDeleting by wishlistViewModel.isDeletingWishList.observeAsState(initial = false)
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            Button(
                onClick = {
                    wishlistViewModel.deleteWishListProduct(wishId, wishListProductId, wishListName) { success, errMsg ->
                        if (success) {
                            Toast
                                .makeText(context, "Successfully deleted", Toast.LENGTH_SHORT)
                                .show()
//                            wishlistViewModel.fetchWishlist()
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