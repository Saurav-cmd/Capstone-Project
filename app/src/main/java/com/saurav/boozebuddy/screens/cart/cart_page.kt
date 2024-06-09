package com.saurav.boozebuddy.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor

@Composable
fun CartPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        item {
            TopContainer()
        }
        item {
            Spacer(modifier = Modifier.height(height = 40.dp))
            CartDesign()
        }
        item {
            Spacer(modifier = Modifier.height(height = 40.dp))
            CartDesign()
        }
        item {
            Spacer(modifier = Modifier.height(height = 40.dp))
            CartDesign()
        }
        item {
            Spacer(modifier = Modifier.height(height = 40.dp))
            CartDesign()
        }
        item {
            Spacer(modifier = Modifier.height(height = 40.dp))
            CartDesign()
        }
    }
}

@Composable
private fun TopContainer() {
    return Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "My Cart", style = TextStyle(
                color = colors.secondary, fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
        )
        Card {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = errorColor,
                modifier = Modifier
                    .padding(10.dp)
                    .size(30.dp)
            )
        }
    }
}

@Composable
private fun CartDesign() {
    return Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Bag logo", tint = colors.secondary)
                Spacer(modifier = Modifier.width(width = 10.dp))
                Text("JD", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colors.secondary))
            }
            Text(
                "View Brand",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.End,
                    color = colors.secondary
                ),
            )
        }
        Spacer(modifier = Modifier.height(height = 5.dp))
        //this is horizontal line......................................................................
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(lightGrey)
            )
        }
        //this is horizontal line ends here......................................................................
        Spacer(modifier = Modifier.height(height = 10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Image(
                    painter = painterResource(id = ImagesConst.banner),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop, // Adjust content scale to fit within the box
                    modifier = Modifier
                        .fillMaxSize() // Ensure the image takes up the entire box
                        .clip(RoundedCornerShape(20.dp)) // Apply the same clipping shape
                )
            }
            Spacer(modifier = Modifier.width(width = 10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "8848 Vodka",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colors.secondary)
                    )
                    Card {
                        Text(
                            text = "+",
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Vodka",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W500, color = colors.secondary)
                    )
                    Text(
                        text = "10",
                        style = TextStyle(textAlign = TextAlign.Center, color = colors.secondary),
                        modifier = Modifier.padding(end = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$10.04",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400, color = colors.secondary),
                    )
                    Card {
                        Text(
                            text = "-",
                            modifier = Modifier.padding(7.dp)
                        )
                    }
                }
            }
        }
    }
}