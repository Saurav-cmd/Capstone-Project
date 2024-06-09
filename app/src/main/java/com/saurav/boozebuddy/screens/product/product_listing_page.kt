package com.saurav.boozebuddy.screens.product

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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.Item
import com.saurav.boozebuddy.screens.home.items
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor

@Composable
fun ProductListingScreen(navHostController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        item { TopContainer(navHostController) }
        item { SearchBar() }
        item { ProductGridView(navHostController) }
    }
}

@Composable
private fun TopContainer(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterStart)
                .clickable { navController.popBackStack() }
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
fun ProductGridView(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
                }
            }
        }
    }
}

@Composable
fun GridItem(item: Item, navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .width(170.dp)
            .height(220.dp)
            .shadow(10.dp, RoundedCornerShape(20.dp), spotColor = colors.primary)
            .clip(RoundedCornerShape(20.dp))
            .background(primaryColor)
            .clickable { navHostController.navigate(NavRoute.ProductDetail.route) }
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
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
                ProductInfo()
            }
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to Cart",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
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
