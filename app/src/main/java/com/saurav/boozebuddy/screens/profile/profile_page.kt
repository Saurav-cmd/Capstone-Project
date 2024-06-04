package com.saurav.boozebuddy.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.ui.theme.bodyColor
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
        item{
            Spacer(modifier = Modifier.height(10.dp))
            BottomContainer()
        }

    }
}

@Composable
private fun TopContainer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = containerColor)
        ) {
            Text(
                text = "My Profile",
                modifier = Modifier.padding(20.dp),
                style = TextStyle(color = bodyColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            )

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50.dp))
                    .size(100.dp)
                    .background(color = primaryColor)
                    .align(Alignment.CenterEnd)
                    .padding(top = 20.dp)
            ) {
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Mike Tyson",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Software Developer",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "An Very good and experienced software developer experience with backend knowledge",
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
      BottomContainerDesign(Icons.Default.Lock, "Log Out", errorColor, "Log Out", color = errorColor)
  }
}

@Composable
private fun BottomContainerDesign(imageVector: ImageVector, description:String, tint: Color, title: String, color: Color? = Color.Black) {
    return Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = imageVector, contentDescription = description, tint = tint, modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(title, style = TextStyle(fontSize = 16.sp, color = color ?: Color.Black, textAlign = TextAlign.Center))
        }
    }
}
