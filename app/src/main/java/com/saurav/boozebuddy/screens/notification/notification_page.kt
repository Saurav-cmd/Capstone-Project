package com.saurav.boozebuddy.screens.notification

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.containerColor
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor


@Composable
@Preview
fun NotificationPage() {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        content = {
            item {
                TopContainer()
                Spacer(modifier = Modifier.height(20.dp))
            }
            item {
                NotificationContainerDesign()
            }
        })
}

@Composable
private fun TopContainer() {
    return Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Notification",
            style = TextStyle(color = colors.secondary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        )

        Text(
            text = "Filter",
            style = TextStyle(color = containerColor, fontSize = 16.sp, fontWeight = FontWeight.W500)
        )
    }
}

@Composable
private fun NotificationContainerDesign() {
    return Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = lightGrey)
            .clip(RoundedCornerShape(5.dp))
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(5.dp))
            ) {
                Image(
                    painter = painterResource(id = ImagesConst.banner),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(5.dp))
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Text(
                    text = "Your order was dispatched and will soon reach you.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = colors.secondary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "2h ago", style = TextStyle(
                    fontSize = 14.sp,
                    color = colors.secondary,
                ))
            }

        }
    }
}


