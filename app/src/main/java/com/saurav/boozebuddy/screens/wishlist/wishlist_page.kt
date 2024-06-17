package com.saurav.boozebuddy.screens.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.lightGrey

@Composable
fun WishListPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        item { TopContainer() }
        items(5) {
            Spacer(modifier = Modifier.height(20.dp))
            WishListItem()
        }
    }
}

@Composable
private fun TopContainer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
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
                    // Handle navigation back
                }
        )

        Text(
            text = "WishList",
            style = TextStyle(
                color = colors.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )

        Card(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                tint = errorColor,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
private fun WishListItem() {
    Column(modifier = Modifier.fillMaxWidth()) {
        WishListHeader()
        Spacer(modifier = Modifier.height(5.dp))
        Divider(color = lightGrey)
        Spacer(modifier = Modifier.height(10.dp))
        WishListContent()
    }
}

@Composable
private fun WishListHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Bag logo",
                tint = colors.secondary
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "JD",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = colors.secondary,
                modifier = Modifier.size(16.dp)
            )
        }
        Button(
            onClick = {
                // Handle button click action here
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Add to Cart",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.End,
                        color = colors.secondary
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to Cart",
                    tint = colors.secondary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun WishListContent() {
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(id = ImagesConst.banner),
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        WishListDetails()
    }
}

@Composable
private fun WishListDetails() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        WishListDetailsRow(
            leftText = "8848 Vodka",
            rightText = "+",
            rightTextPadding = 5.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        WishListDetailsRow(
            leftText = "Vodka",
            rightText = "10",
            rightTextPadding = 3.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        WishListDetailsRow(
            leftText = "$10.04",
            rightText = "-",
            rightTextPadding = 7.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(lightGrey)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable {
                    // Handle click event here
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Delete",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        color = colors.secondary
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = errorColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun WishListDetailsRow(
    leftText: String,
    rightText: String,
    rightTextPadding: Dp
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = leftText,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colors.secondary
            )
        )
        Card {
            Text(
                text = rightText,
                modifier = Modifier.padding(rightTextPadding)
            )
        }
    }
}

