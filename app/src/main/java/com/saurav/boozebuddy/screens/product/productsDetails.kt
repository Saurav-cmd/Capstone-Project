package com.saurav.boozebuddy.screens.product

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.FavouritesViewModel

@Composable
fun ProductsDetailPage(navHostController: NavHostController,favouritesViewModel: FavouritesViewModel,productId: String?, productName: String?, productImage: String?, productDetail: String?) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ProductImage(navHostController,productImage?.trim('"'))
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            ProductDetailsSection(productName, productDetail, favouritesViewModel, productImage?:"")
        }
    }
}



@Composable
fun ProductImage(navController: NavHostController, productImage: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp).background(color = Color.LightGray)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopStart)
                .clickable {
                    navController.navigateUp()
                    Log.e("THis is url", "$productImage")
                }
        )
        Text(text = "$productImage", color = Color.Black)

        if ((productImage ?: "").isEmpty()) {
            Image(
                painter = painterResource(id = ImagesConst.simrsOff),
                contentDescription = "Products Details Here",
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = productImage),
                contentDescription = "Product Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



@Composable
fun ProductDetailsSection(productName:String?, productDetail: String?, favouritesViewModel: FavouritesViewModel, productImage:String) {
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
            Details(productName, productDetail, favouritesViewModel, productImage)
            Spacer(modifier = Modifier.height(25.dp))
//            PriceAndQuantity()
            Spacer(modifier = Modifier.weight(1f)) // Fill remaining space
            AddCartButton()
        }
    }
}


@Composable
private fun Details(productName: String?, productDetail: String?, favouritesViewModel: FavouritesViewModel, productImage: String) {
    val context = LocalContext.current
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
                    // Write function here
                    favouritesViewModel.storeIsFavourites(productName?:"", "Vodka", productImage){success, errMsg ->
                        if(success){
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }else{
                            //error
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourites")
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
        modifier = Modifier.fillMaxWidth(fraction = 0.5f)
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
fun AddCartButton() {
    Button(
        onClick = {

        },
        colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor,
            contentColor = primaryColor
        ),
        modifier = Modifier.padding(horizontal = 40.dp)
    ) {
        Text(
            text = "Add to Wishlist",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
        )
    }
}
