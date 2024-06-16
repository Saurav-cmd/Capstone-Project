package com.saurav.boozebuddy.screens.product

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.google.gson.Gson
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.HomeViewModel

@Composable
fun ProductListingScreen(
    navHostController: NavHostController,
    productsJson: List<Product>,
    brandName: String?,
    brandId: String?,
    homeViewModel: HomeViewModel
) {
    val filteredProducts by homeViewModel.filteredProduct.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        item {
            TopContainer(navHostController)
        }
        item {
            SearchBar(homeViewModel, productsJson)
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {
            if(filteredProducts.isEmpty()){
                ProductGridView(navHostController, productsJson, brandName, brandId, homeViewModel)
            }else{
                ProductGridView(navHostController, filteredProducts, brandName, brandId, homeViewModel)
            }

        }
    }
}

@Composable
private fun TopContainer(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    navController.navigateUp()
                }
        )

        Text(
            text = "Products",
            style = TextStyle(
                color = colors.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(homeViewModel: HomeViewModel, productData: List<Product>) {
    var textFieldValue by remember { mutableStateOf("") }
    TextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
            homeViewModel.filterProducts(productData, textFieldValue)
        },
        placeholder = { Text(text = "Search", color = secondaryColor) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = secondaryColor
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp,top = 10.dp, end = 20.dp)
            .border(1.dp, colors.secondary, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        )
    )
    Spacer(modifier = Modifier.height(25.dp))
}

@Composable
fun ProductGridView(navController: NavHostController, productData: List<Product>, brandName: String?, brandId: String?, homeViewModel: HomeViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (productData.isEmpty()) {
            Text(text = "No Products to show!!")
        } else {
            val rows = productData.chunked(2)
            rows.forEach { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { item ->
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GridItem(item, navController, brandName, brandId, homeViewModel)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                    if (rowItems.size < 2) {
                        repeat(2 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(item: Product, navHostController: NavHostController, passedBrandName: String?, passedBrandId: String?, homeViewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .width(170.dp)
            .height(220.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = colors.primary
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.Gray.copy(alpha = 0.1f))
            .clickable {
                val productData = Uri.encode(Gson().toJson(item))
                val brandName = Uri.encode(Gson().toJson(passedBrandName))
                val brandId = Uri.encode(Gson().toJson(passedBrandId))
                navHostController.navigate("${NavRoute.ProductDetail.route}/${productData}/${brandName}/${brandId}")
                homeViewModel.clearFilteredProducts()
            },
        contentAlignment = Alignment.Center
    ) {

        val painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder // Placeholder image resource
            // Image to show in case of loading failure
                (LocalContext.current).data(data = item.productImage)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    placeholder(ImagesConst.people)
                    error(ImagesConst.people)
                    size(Size.ORIGINAL)
                }).build()
            )

        Image(
            painter = painter,
            contentDescription = item.productDescription,
            contentScale = ContentScale.Crop, // Maintain aspect ratio and crop if needed
            modifier = Modifier.fillMaxSize() // Take full height and width
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color(0x99000000))
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = item.productName ?: "",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${item.productQuantity}",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${item.productPrice}",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            /*IconButton(
                onClick = { *//*TODO*//* },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Add to Cart",
                    tint = Color.White,
                )
            }*/
        }
    }
}
