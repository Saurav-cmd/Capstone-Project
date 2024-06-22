package com.saurav.boozebuddy.screens.product

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.FavouritesViewModel
import com.saurav.boozebuddy.view_models.WishlistViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsDetailPage(
    navHostController: NavHostController,
    favouritesViewModel: FavouritesViewModel,
    productInfo: Product,
    brandName: String?,
    brandId: String?,
    wishlistViewModel: WishlistViewModel
) {
    Scaffold(
        bottomBar = {
            AddWishListButton(navHostController, wishlistViewModel, productInfo, brandName, brandId)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = lightGrey)
        ) {
            item {
                ProductImage(navHostController, productInfo.productImage)
            }
            item {
                ProductDetailsSection(
                    productInfo.productName,
                    productInfo.productDescription,
                    favouritesViewModel,
                    productInfo.productImage ?: "",
                    productInfo.productId,
                    brandName,
                    brandId,
                    navHostController,
                    wishlistViewModel,
                    productInfo
                )
            }
        }
    }
}



@Composable
fun ProductImage(navController: NavHostController, productImage: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(color = Color.LightGray)
    ) {

        val painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder // Placeholder image resource
                // Image to show in case of loading failure
                    (LocalContext.current).data(data = productImage)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(ImagesConst.appLogo)
                        error(ImagesConst.appLogo)
                        size(Size.ORIGINAL)
                    }).build()
            )

        Image(
            painter = painter,
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop, // Maintain aspect ratio and crop if needed
            modifier = Modifier.fillMaxSize() // Take full height and width
        )

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 10.dp)
                .clickable {
                    navController.navigateUp()
                    Log.e("THis is url", "$productImage")
                }
        )
    }
}


@Composable
fun ProductDetailsSection(
    productName: String?,
    productDetail: String?,
    favouritesViewModel: FavouritesViewModel,
    productImage: String,
    productId: String?,
    brandName: String?,
    brandId: String?,
    navHostController: NavHostController,
    wishlistViewModel: WishlistViewModel,
    productInfo: Product,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Fill the entire screen height
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(lightGrey)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxSize()
        ) {
            Details(
                productName,
                productDetail,
                favouritesViewModel,
                productImage,
                productId,
                brandName,
                brandId,productInfo
            )
            Spacer(modifier = Modifier.height(25.dp))
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


@Composable
private fun Details(
    productName: String?,
    productDetail: String?,
    favouritesViewModel: FavouritesViewModel,
    productImage: String,
    productId: String?,
    brandName: String?,
    brandId: String?,
    productInfo: Product,
) {
    val context = LocalContext.current
    val isAddingToFavourite by favouritesViewModel.isStoringFavourites.observeAsState(initial = false)
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "$productName (${productInfo.productVolume} ml)",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Red
                ),
                onClick = {
                    if (favouritesViewModel.checkIfAlreadyFavourite(productId ?: "")) {
                        Toast.makeText(context, "Already in Favourites List", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        favouritesViewModel.storeIsFavourites(
                            productName ?: "",
                            brandName ?: "",
                            productImage,
                            productId ?: "",
                            brandId ?: "",
                            productInfo
                        ) { success, errMsg ->
                            if (success) {
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                favouritesViewModel.checkIfAlreadyFavourite(productId ?: "")
                            } else {
                                //error
                                Toast.makeText(context, "Error $errMsg", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            ) {
                if (favouritesViewModel.checkIfAlreadyFavourite(productId ?: "")) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourites"
                    )
                } else {
                    if (isAddingToFavourite) CircularProgressIndicator() else Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favourites"
                    )
                }


            }
        }
//        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Made in: ${productInfo.productOrigin}",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Description(productDetail)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Ingredients",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        DisplayIngredients(ingredients = productInfo.productIngredients)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Alcohol Percentage",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "${productInfo.productABV}%",
            style = TextStyle(fontSize = 14.sp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun DisplayIngredients(ingredients: List<String>?) {
    val formattedIngredients = ingredients?.joinToString(", ")
    Text(
        text = formattedIngredients ?: "",
        style = TextStyle(fontSize = 14.sp),
        color = Color.Black,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun Description(productDetail: String?) {
    Text(
        text = "$productDetail",
        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light),
        color = Color.Black,
//        modifier = Modifier.fillMaxWidth(fraction = 0.5f)
    )
}


@Composable
fun AddWishListButton(
    navHostController: NavHostController,
    wishlistViewModel: WishlistViewModel,
    productInfo: Product,
    brandName: String?,
    brandId: String?,
) {
    var isDialogueOpen by remember { mutableStateOf<Boolean>(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp) // Adjust padding as needed
    ) {
        Button(
            onClick = {
                isDialogueOpen = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = secondaryColor,
                contentColor = primaryColor
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter) // Aligns the button at the bottom center
                .padding(horizontal = 40.dp) // Adjust horizontal padding if necessary
        ) {
            Text(
                text = "Add to Wishlist",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
        }
    }

    if (isDialogueOpen) {
        WishListDialog(
            onDismiss = { isDialogueOpen = false },
            navHostController = navHostController,
            wishlistViewModel = wishlistViewModel,
            productInfo,
            brandName, brandId
        )
    }
}


@Composable
fun WishListDialog(
    onDismiss: () -> Unit,
    navHostController: NavHostController,
    wishlistViewModel: WishlistViewModel,
    productInfo: Product,
    brandName: String?,
    brandId: String?,
) {
    // State variables
    val isStoringWishlist by wishlistViewModel.isStoringWishList.observeAsState(initial = false)
    var wishlistName by remember { mutableStateOf("") }
    val context = LocalContext.current
    val wishListData by wishlistViewModel.wishListValue.observeAsState(initial = emptyList())
    val isFetchingWishList by wishlistViewModel.isFetchingWishList.observeAsState(initial = false)

    // State variable to track selected wishlist item
    var selectedWishlistIndex by remember { mutableStateOf(-1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            Button(
                onClick = {
                    if (wishlistName.isNotBlank()) {
                        val existingWishlist = wishListData.find { it.wishName == wishlistName }
                        if (existingWishlist != null) {
                            Toast.makeText(context, "Wishlist name already exists", Toast.LENGTH_SHORT).show()
                        } else {
                            // User wants to create a new wishlist
                            wishlistViewModel.storeWishList(
                                wishlistName,
                                productInfo.productName ?: "",
                                productInfo.productDescription ?: "",
                                productInfo.productImage ?: "",
                                brandName ?: "",
                                productInfo.productId,
                                brandId ?: "",
                                productInfo,
                            ) { success, errMsg ->
                                if (success) {
                                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                    wishlistViewModel.fetchWishlist()
                                } else {
                                    Toast.makeText(context, "Error $errMsg", Toast.LENGTH_SHORT).show()
                                }
                            }
                            onDismiss()
                        }
                    } else if (selectedWishlistIndex != -1) {
                        // User selected an existing wishlist
                        val selectedWishlist = wishListData[selectedWishlistIndex]
                        wishlistViewModel.storeWishList(
                            selectedWishlist.wishName,
                            productInfo.productName ?: "",
                            productInfo.productDescription ?: "",
                            productInfo.productImage ?: "",
                            brandName ?: "",
                            productInfo.productId,
                            brandId ?: "",
                            productInfo,
                        ) { success, errMsg ->
                            if (success) {
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                wishlistViewModel.fetchWishlist()
                            } else {
                                Toast.makeText(context, "Error $errMsg", Toast.LENGTH_SHORT).show()
                            }
                        }
                        onDismiss()
                    } else {
                        Toast.makeText(context, "Please enter a wishlist name or select an existing wishlist", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryColor,
                    contentColor = primaryColor
                ),
            ) {
                if (isStoringWishlist) {
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
            Column {
                Text(
                    text = "Enter your wishlist name",
                    textAlign = TextAlign.Center,
                    color = colors.secondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
                Spacer(modifier = Modifier.height(10.dp))
                "Wishlist".TextFormField(
                    value = wishlistName,
                    onValueChange = { wishlistName = it },
                    validator = { input ->
                        when {
                            input.isEmpty() -> "Field cannot be empty"
                            else -> null
                        }
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Or",
                    textAlign = TextAlign.Center,
                    color = colors.secondary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "Select From Previous Wishlist",
                    textAlign = TextAlign.Center,
                    color = colors.secondary,
                    fontSize = 16.sp
                )
                if (isFetchingWishList) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
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
                    // Handle case when wishlist data is empty
                    Text(text = "No wishlist available", color = secondaryColor, fontSize = 16.sp)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (wishListData.size > 5) 200.dp else 100.dp)
                    ) {
                        items(wishListData.size) { index ->
                            val data = wishListData[index]
                            val isSelected = index == selectedWishlistIndex

                            // Clickable Text item
                            Text(
                                data.wishName.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.ROOT
                                    ) else it.toString()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        selectedWishlistIndex = index // Update selected index
                                    }
                                    .background(if (isSelected) Color.LightGray else Color.Transparent),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start
                            )
                            Divider()
                        }
                    }
                }
            }
        }
    )
}




@Composable
private fun String.TextFormField(
    value: String,
    onValueChange: (String) -> Unit,
    showSuffix: Boolean = false,
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
                .padding(top = 0.dp)
                .border(width = 1.dp, color = secondaryColor, shape = RoundedCornerShape(10.dp)),
            visualTransformation = if (showSuffix) {
                if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },

            // Use VisualTransformation.None when passwordVisible is true to show the password
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            trailingIcon = {
                if (showSuffix) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Build else Icons.Filled.Lock,
                            // Use Icons.Filled.Eye when passwordVisible is false to show the eye icon
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                } else {
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
