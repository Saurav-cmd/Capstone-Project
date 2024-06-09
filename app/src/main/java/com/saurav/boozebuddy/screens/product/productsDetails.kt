package com.saurav.boozebuddy.screens.product

import android.view.RoundedCorner
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors

import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor


@Composable
@Preview(showBackground = true)
fun ProductsDetailPage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = ImagesConst.simrsOff),
            contentDescription = "Products Details Here",
            alignment = Alignment.Center,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
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
            IconButton(colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.Red // Set the content color
            ), onClick = {
                //write function here
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
            .background(Color.Gray),
    ) {
        Text(
            text = value,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = colors.primary
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
