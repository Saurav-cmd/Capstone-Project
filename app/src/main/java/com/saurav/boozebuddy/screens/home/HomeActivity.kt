package com.saurav.boozebuddy.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor


@Composable
fun HomePage(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        GreetingContainer()
        Spacer(modifier = Modifier.height(10.dp))
        "Search your favourite brand".TextFormField()
        Spacer(modifier = Modifier.height(25.dp))
        Banner()
    }
}

@Composable
private fun GreetingContainer(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(
                text = "Hello Saurav,\nGood Morning",
                style = TextStyle(color = primaryColor,
                    fontWeight = FontWeight.Bold, fontSize = 16.sp))
            Text(text = "London",
                style = TextStyle(color = secondaryColor,
                    fontWeight = FontWeight.W500, fontSize = 14.sp))
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50.dp))
                .size(50.dp)
                .background(color = primaryColor)) {
//            Image(
//                painter = painterResource(id = R.drawable.person),
//                contentDescription = "Person Image",
//            )
        }

    }
}

@Composable
private fun Banner(){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)) {
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Promotion Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
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