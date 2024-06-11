package com.saurav.boozebuddy.screens.home

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.ui.theme.containerColor
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.HomeViewModel


@Composable
fun HomePage(navController: NavHostController, homeViewModel: HomeViewModel) {
    // Collect the brands from HomeViewModel
    val brands by homeViewModel.brands.observeAsState(emptyList())
    val isLoading by homeViewModel.isLoading.observeAsState(false)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
    ) {
        item { GreetingContainer(navController) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
        item { SearchField("Search your favourite brand") }
        item { Spacer(modifier = Modifier.height(25.dp)) }
        item { Banner() }
        item { Spacer(modifier = Modifier.height(25.dp)) }
        item { TopBrandsLine() }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            if (isLoading) {
               Column(
                   modifier = Modifier.fillMaxWidth(),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {
                   CircularProgressIndicator(
                       modifier = Modifier.size(50.dp),
                       color = secondaryColor,
                       strokeWidth = 2.dp
                   )
                   Spacer(modifier = Modifier.height(5.dp))
                   Text(text = "Loading Brands...", color = secondaryColor, fontSize = 18.sp)
               }
            } else if (brands.isEmpty()) {
                Text(
                    text = "Brands are not available",
                    color = colors.onSurface,
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            } else {
                TopBrandsGridView(navController, brands)
            }
        }
    }
}

@Composable
private fun GreetingContainer(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Hello Saurav,\nGood Morning",
                style = TextStyle(
                    color = colors.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "London",
                style = TextStyle(
                    color = colors.secondary,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .size(50.dp).clickable {
                    navController.navigate(NavRoute.FavouritesListing.route)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favourites",
                tint = Color.Red,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
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
private fun SearchField(placeholderText: String) {
    var textFieldValue by remember { mutableStateOf("") }
    val colors = MaterialTheme.colorScheme

    TextField(
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        placeholder = { Text(text = placeholderText, color = containerColor) },
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
fun TopBrandsGridView(navController: NavHostController, brands: List<BrandModel>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        brands.chunked(3).forEach { brands ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                brands.forEach { item ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        GridItem(brandData = item, navController = navController)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.brandName ?: "",
                            color = colors.onSurface,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
                if (brands.size < 3) {
                    repeat(3 - brands.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

    }


}


@Composable
fun GridItem(brandData: BrandModel, navController: NavHostController) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(width = 1.5.dp, color = colors.secondary, shape = RoundedCornerShape(20.dp))
            .clickable {
                val jsonEncoded = Uri.encode(Gson().toJson(brandData.products))
                navController.navigate("${NavRoute.ProductListing.route}/$jsonEncoded")
            }
    )
    {
        AsyncImage(
            model = brandData.brandLogo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}
