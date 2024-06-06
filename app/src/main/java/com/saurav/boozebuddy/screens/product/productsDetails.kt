package com.saurav.boozebuddy.screens.product
import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.ui.theme.bodyColor
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor


@Composable
fun ProductsDetailPage() {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.simrs_off),
            contentDescription = "Products Details Here"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Details()
        Spacer(modifier = Modifier.height(20.dp))
        PriceAndQuantity()
        Spacer(modifier = Modifier.height(90.dp))
        AddCartButton()
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
            IconButton(onClick = {
                //write function here
            }
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourites")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Description()
    }
}

@Composable
private fun Description() {
    Text(
        text = "Alcohol with 10% Alcohol level enjoy your company with 32 others",
        style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Light),
        color = Color.DarkGray,
        modifier = Modifier.fillMaxWidth(fraction = 0.5f)
    )
}

@Composable
private fun PriceAndQuantity() {
    Column {
        Text(text = "Price and Quantity")
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(lightGrey)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column {
                    Text("30 Available")
                    Text("$24.00")
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
        Text(text = value, textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center), color = bodyColor)
    }
}

@Composable
fun AddCartButton  () {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .height(90.dp)
        .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = bodyColor
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 40.dp),
                text = "Add to Cart",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
            Text(text = "$350.69")
        }


    }

}
