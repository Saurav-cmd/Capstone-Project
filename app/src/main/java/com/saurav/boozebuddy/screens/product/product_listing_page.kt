package com.saurav.boozebuddy.screens.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.models.Item
import com.saurav.boozebuddy.screens.home.items
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor

@Composable
fun ProductListingScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ){
        item {
            TopContainer()
        }
        item {
            "Search".TextFormField()
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {
            ProductGridView()
        }
    }
}

@Composable
private fun TopContainer(){
    Box(
        modifier = Modifier.fillMaxWidth(),
    ){
         Icon(
             imageVector = Icons.Default.ArrowBack,
             contentDescription = "Back",
             tint = Color.Black,
             modifier = Modifier
                 .padding(10.dp)
                 .size(30.dp)
                 .align(Alignment.CenterStart)
         )

        Text(
            text = "Products", style = TextStyle(
                color = primaryColor, fontSize = 24.sp, fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun String.TextFormField() {
    var textFieldValue by remember { mutableStateOf("") }
    TextField(
        value = textFieldValue,
        onValueChange = { data ->
            textFieldValue = data
        },
        placeholder = { Text(text = this, color = secondaryColor) },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = secondaryColor)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .border(width = 1.dp, color = primaryColor, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = secondaryColor,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun ProductGridView() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = items.chunked(2)
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
                        GridItem(item)
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

//@Composable
//fun GridItem(item: Item) {
//    Box(
//        modifier = Modifier
//            .width(170.dp)
//            .height(220.dp)
//            .clip(RoundedCornerShape(20.dp))
//            .border(width = 1.5.dp, color = primaryColor, shape = RoundedCornerShape(20.dp))
//    ) {
//        Image(
//            painter = painterResource(id = item.imageRes),
//            contentDescription = "Product Image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(10.dp)
//        )
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter) // Align to the bottom center of the parent (image)
//                .background(color = Color(0x99000000)) // Semi-transparent background color
//        ) {
//            Column(
//                modifier = Modifier.padding(10.dp) // Add padding to the content
//            ) {
//                Text(
//                    text = "item.name",
//                    style = TextStyle(
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp
//                    ),
//                )
//
//                Text(
//                    text = "1L",
//                    style = TextStyle(
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 14.sp
//                    ),
//                )
//
//                Text(
//                    text = "$$$$$",
//                    style = TextStyle(
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 14.sp
//                    ),
//                )
//            }
//
//            Icon(
//                imageVector = Icons.Default.ShoppingCart,
//                contentDescription = "Add to Cart",
//                tint = Color.White,
//                modifier = Modifier.align(Alignment.BottomEnd)
//            )
//        }
//    }
//}

@Composable
fun GridItem(item: Item) {
    Box(
        modifier = Modifier
            .width(170.dp)
            .height(220.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(width = 1.5.dp, color = primaryColor, shape = RoundedCornerShape(20.dp))
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
                .background(color = Color(0x99000000))
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Johnny Walker",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "1L",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$100",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Add to Cart",
                tint = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

