package com.saurav.boozebuddy.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor

@Composable
fun ProductsDetailPage(navHostController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            ProductImage(navHostController)
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            ProductDetailsSection()
        }
    }
}

@Composable
fun ProductImage(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = ThemeUtils.colors.secondary,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopStart)
                .clickable { navController.popBackStack() }
        )
        Image(
            painter = painterResource(id = ImagesConst.simrsOff),
            contentDescription = "Products Details Here",
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ProductDetailsSection() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(lightGrey)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Details()
            Spacer(modifier = Modifier.height(25.dp))
            PriceAndQuantity()
            Spacer(modifier = Modifier.height(90.dp))
            AddCartButton()
        }
    }
}

@Composable
private fun Details() {
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
                text = "Simrs off Liquor",
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
                }
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourites")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Description()
    }
}

@Composable
private fun Description() {
    Text(
        text = "Alcohol with 10% Alcohol level enjoy your company with 32 others",
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
                    QuantityBoxDesign(value = "-", onClick = { /* Decrease quantity */ })
                    Spacer(modifier = Modifier.width(5.dp))
                    QuantityBoxDesign(value = "1", onClick = {})
                    Spacer(modifier = Modifier.width(5.dp))
                    QuantityBoxDesign(value = "+", onClick = { /* Increase quantity */ })
                }
            }
        }
    }
}

@Composable
private fun QuantityBoxDesign(value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(height = 20.dp, width = 20.dp)
            .background(Color.Gray)
            .clickable(onClick = onClick)
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
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Add to Cart",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
            Text(text = "$350.69", fontSize = 16.sp)
        }
    }
}
