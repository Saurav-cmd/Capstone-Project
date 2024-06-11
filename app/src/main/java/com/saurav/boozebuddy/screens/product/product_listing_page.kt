package com.saurav.boozebuddy.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
<<<<<<< HEAD
import androidx.compose.material3.*
import androidx.compose.runtime.*
=======
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
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor

@Composable
fun ProductListingScreen(navHostController: NavHostController, productsJson: List<Product>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
<<<<<<< HEAD
        item { TopContainer(navHostController) }
        item { SearchBar() }
        item { ProductGridView(navHostController) }
=======
        item {
            TopContainer(navHostController)
        }
        item {
            "Search".TextFormField()
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {
            ProductGridView(navHostController, productsJson)
        }
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
    }
}

@Composable
private fun TopContainer(navController: NavHostController) {
    Box(
<<<<<<< HEAD
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
=======
        modifier = Modifier.fillMaxWidth(),
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
<<<<<<< HEAD
                .size(30.dp)
                .align(Alignment.CenterStart)
                .clickable { navController.popBackStack() }
=======
                .padding(10.dp)
                .size(30.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    navController.popBackStack()
                }
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
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
private fun SearchBar() {
    var textFieldValue by remember { mutableStateOf("") }
    TextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
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
            .padding(top = 10.dp)
            .border(1.dp, colors.secondary, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colors.secondary,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.height(25.dp))
}

@Composable
fun ProductGridView(navController: NavHostController, productData: List<Product>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
<<<<<<< HEAD
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    GridItem(item, navController)
                    Spacer(modifier = Modifier.height(4.dp))
                }
                if (rowItems.size < 2) {
                    repeat(2 - rowItems.size) { Spacer(modifier = Modifier.weight(1f)) }
=======
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
                            GridItem(item, navController)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                    if (rowItems.size < 2) {
                        repeat(2 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
                }
            }
        }

    }
}

@Composable
fun GridItem(item: Product, navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .width(170.dp)
            .height(220.dp)
<<<<<<< HEAD
            .shadow(10.dp, RoundedCornerShape(20.dp), spotColor = colors.primary)
            .clip(RoundedCornerShape(20.dp))
            .background(primaryColor)
            .clickable { navHostController.navigate(NavRoute.ProductDetail.route) }
=======
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = colors.primary
            )
            .clip(RoundedCornerShape(20.dp))
            .background(color = primaryColor)
            .clickable {
                navHostController.navigate(NavRoute.ProductDetail.route)
            }
//            .border(width = 1.5.dp, color = colors.secondary, shape = RoundedCornerShape(20.dp))
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
    ) {
        AsyncImage(
            model = item.productImage,
            contentDescription = item.productDescription,
            contentScale = ContentScale.Crop,
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
<<<<<<< HEAD
                ProductInfo()
            }
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to Cart",
                tint = Color.White,
=======
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


            IconButton(
                onClick = { /*TODO*/ },
>>>>>>> ae85916 (#2 working on the product listing and product search by using dynamic data from firebase by Saurav Adhikari)
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Add to Cart",
                    tint = Color.White,

                    )
            }
        }
    }
}

@Composable
fun ProductInfo() {
    Text(
        text = "Johnny Walker",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "1L",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "$100",
        style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
}
