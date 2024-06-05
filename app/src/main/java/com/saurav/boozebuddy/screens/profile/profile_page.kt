package com.saurav.boozebuddy.screens.profile

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.ui.theme.containerColor
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.primaryColor

@Composable
fun ProfilePage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        item {
            TopContainer()
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            BottomContainer()
        }
    }
}

@Composable
private fun TopContainer() {
    Box {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            val path = Path().apply {
                moveTo(0f, size.height)
                quadraticTo(size.width / 2, size.height - 100, size.width, size.height)
                lineTo(size.width, 0f)
                lineTo(0f, 0f)
                close()
            }
            drawPath(path, color = containerColor)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "My Profile",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Surface(
                modifier = Modifier
                    .offset(y = 25.dp) // Half out of the surface
                    .clip(CircleShape)
                    .size(100.dp),
                shape = CircleShape,
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                Image(
                    painter = painterResource(id = ImagesConst.people),
                    contentDescription = "Person Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(60.dp)) // Adjusted to accommodate the offset profile image

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Johns Steel",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "UI/UX Designer",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }
}


@Composable
private fun BottomContainer() {
    return Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        BottomContainerDesign(Icons.Default.Info, "Information", primaryColor, "Help and Support")
        BottomContainerDesign(Icons.Default.Settings, "Settings", primaryColor, "Settings")
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Black)
        )
        BottomContainerDesign(Icons.Default.Info, "App Info", primaryColor, "App Info")
        BottomContainerDesign(
            Icons.Default.Lock,
            "Log Out",
            errorColor,
            "Log Out",
            color = errorColor
        )
    }
}

@Composable
private fun BottomContainerDesign(
    imageVector: ImageVector,
    description: String,
    tint: Color,
    title: String,
    color: Color? = Color.Black
) {
    return Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                tint = tint,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                title,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = color ?: Color.Black,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}
