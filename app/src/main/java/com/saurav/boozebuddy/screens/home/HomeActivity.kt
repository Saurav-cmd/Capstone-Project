package com.saurav.boozebuddy.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.Item
import com.saurav.boozebuddy.ui.theme.containerColor

val items = listOf(
    Item(1, "Johnnie Walker", imageRes = ImagesConst.johnnieWalker),
    Item(2, "Simrs Off", imageRes = ImagesConst.simrsOff),
    Item(3, "Martini", imageRes = ImagesConst.martini),
    Item(4, "Tequila", imageRes = ImagesConst.tequila),
    Item(5, "Vodka", imageRes = ImagesConst.vodka),
)

@Composable
fun HomePage(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        item {
            GreetingContainer()
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            "Search your favourite brand".TextFormField()
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {
            Banner()
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {
            TopBrandsLine()
            Spacer(modifier = Modifier.height(16.dp))
        }
        // Use item for the TopBrandsGridView to avoid nested LazyColumn
        item {
            TopBrandsGridView(navController)
        }
    }
}

@Composable
private fun GreetingContainer() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hello Saurav,\nGood Morning",
                style = TextStyle(
                    color = colors.secondary,
                    fontWeight = FontWeight.Bold, fontSize = 16.sp
                )
            )
            Text(
                text = "London",
                style = TextStyle(
                    color = colors.secondary,
                    fontWeight = FontWeight.W500, fontSize = 14.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(50.dp))
                .size(50.dp)
                .background(color = colors.secondary)
        ) {
        }
    }
}

@Composable
private fun Banner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
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
    val colors = MaterialTheme.colorScheme

    TextField(
        value = textFieldValue,
        onValueChange = { data ->
            textFieldValue = data
        },
        placeholder = { Text(text = this, color = containerColor) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .border(width = 1.dp, color = colors.secondary, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colors.secondary,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@Composable
fun TopBrandsLine() {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(colors.onSurface)
        )
        Text(
            text = "Top Brands",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            color = colors.onSurface,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(colors.onSurface)
        )
    }
}

@Composable
fun TopBrandsGridView(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val rows = items.chunked(3)
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
                        GridItem(item = item, navController)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.text,
                            color = colors.onSurface,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                if (rowItems.size < 3) {
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(item: Item, navController: NavHostController) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(width = 1.5.dp, color = colors.secondary, shape = RoundedCornerShape(20.dp))
            .clickable {
                navController.navigate(NavRoute.ProductListing.route)
            }
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = "Promotion Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        )
    }
}
