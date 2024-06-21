package com.saurav.boozebuddy.screens.product

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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

@Composable
fun ProductsDetailPage(
    navHostController: NavHostController,
    favouritesViewModel: FavouritesViewModel,
    productInfo: Product,
    brandName: String?,
    brandId: String?,
    wishlistViewModel: WishlistViewModel
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
                brandId
            )
            Spacer(modifier = Modifier.height(25.dp))
//            PriceAndQuantity()
            Spacer(modifier = Modifier.weight(1f)) // Fill remaining space
            AddCartButton(navHostController, wishlistViewModel, productInfo, brandName, brandId)
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
    brandId: String?
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
                text = "$productName",
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
                            brandId ?: ""
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
        Spacer(modifier = Modifier.height(5.dp))
        Description(productDetail)
    }
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
private fun PriceAndQuantity() {
    Column {
        Text(
            text = "Price and Quantity",
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "30 Available",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text("$24.00", style = TextStyle(fontSize = 14.sp, color = Color.Black))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    QuantityBoxDesign(value = "-")
                    Spacer(modifier = Modifier.width(5.dp))
                    QuantityBoxDesign(value = "1")
                    Spacer(modifier = Modifier.width(5.dp))
                    QuantityBoxDesign(value = "+")
                }
            }
        }
    }
}

@Composable
private fun QuantityBoxDesign(value: String) {
    Box(
        modifier = Modifier
            .size(height = 20.dp, width = 20.dp)
            .background(Color.Gray)
    ) {
        Text(
            text = value,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = primaryColor
        )
    }
}

@Composable
fun AddCartButton(
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

    val isStoringWishlist by wishlistViewModel.isStoringWishList.observeAsState(initial = false)
    var wishlistName by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        confirmButton = {
            Button(
                onClick = {
                    wishlistViewModel.storeWishList(
                        wishlistName,
                        productInfo.productName ?: "",
                        productInfo.productDescription ?: "",
                        productInfo.productImage ?: "",
                        brandName ?: "",
                        productInfo.productId,
                        brandId ?: ""
                    ){
                        success, errMsg ->

                        if(success){
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Error $errMsg", Toast.LENGTH_SHORT).show()
                        }
                    }
                    onDismiss()
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                ) {
                    items(15) { index ->
                        Text(
                            text = "Hello $index",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Start
                        )
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
