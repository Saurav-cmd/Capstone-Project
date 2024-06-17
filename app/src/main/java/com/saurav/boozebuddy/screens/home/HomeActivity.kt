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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.BannerModel
import com.saurav.boozebuddy.models.BrandModel
import com.saurav.boozebuddy.models.UserModel
import com.saurav.boozebuddy.ui.theme.containerColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.HomeViewModel
import kotlin.math.absoluteValue


@Composable
fun HomePage(navController: NavHostController, homeViewModel: HomeViewModel) {

    LaunchedEffect(Unit) {
       run {
           homeViewModel.initializeData()
       }
    }

    // Collect the brands from HomeViewModel
    val brands by homeViewModel.brands.observeAsState(emptyList())
    val isLoading by homeViewModel.isLoading.observeAsState(false)
    val banners by homeViewModel.banners.observeAsState(emptyList())
    val isLoadingBanner by homeViewModel.isLoadingBanners.observeAsState(initial = false)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        item { GreetingContainer(navController, homeViewModel) }
        item { Spacer(modifier = Modifier.height(10.dp)) }
//        item { SearchField("Search your favourite brand") }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item {
            if(isLoadingBanner){
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp) // Ensure the Card takes full width and height
                        .padding(16.dp) // Add padding to center the CircularProgressIndicator
                ){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = colors.secondary)
                    }
                }
            }else{
                Banner(banners)
            }

        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
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




@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banner(banners: List<BannerModel>) {
    val pagerState = rememberPagerState(initialPage = 1)

    HorizontalPager(
        count = banners.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 45.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .height(180.dp) // Ensure the Card takes full width and height
        ) {
                val painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(banners[page].bannerImage)
                        .apply {
                            crossfade(true)
                            placeholder(ImagesConst.appLogo)
                            error(ImagesConst.appLogo)
                        }
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Promotion Banner Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize() // Ensure the Image takes the full size of the card
                        .offset {
                            val pageOffset =
                                this@HorizontalPager.calculateCurrentOffsetForPage(page)
                            // Use it as a multiplier to apply an offset
                            IntOffset(
                                x = (32.dp * pageOffset).roundToPx(),
                                y = 0,
                            )
                        }
                )

        }
    }
}





@Composable
private fun GreetingContainer(navController: NavHostController, homeViewModel: HomeViewModel) {
    val isLoading by homeViewModel.isFetchingUserInfo.observeAsState(initial = false)
    val userInfo by homeViewModel.userInfo.observeAsState(initial = UserModel())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp,),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            if(isLoading){
                Text(
                    text = "Loading...",
                    style = TextStyle(
                        color = colors.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }else{
                Text(
                    text = "Hello ${userInfo.name.split(" ")[0]},\n${homeViewModel.getGreetingMessage()}",
                    style = TextStyle(
                        color = colors.secondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }

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
                .size(50.dp)
                .clickable {
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp,),
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp,),
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
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(width = 1.5.dp, color = colors.secondary, shape = RoundedCornerShape(20.dp))
            .clickable {
                val jsonEncoded = Uri.encode(Gson().toJson(brandData.products))
                val brandName = Uri.encode(Gson().toJson(brandData.brandName))
                val brandId = Uri.encode(Gson().toJson(brandData.brandId))
                navController.navigate("${NavRoute.ProductListing.route}/$jsonEncoded/$brandName/$brandId")
            }
    )
    {
        val painter =
            rememberAsyncImagePainter(ImageRequest.Builder // Placeholder image resource
            // Image to show in case of loading failure
                (LocalContext.current).data(data = brandData.brandLogo)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    placeholder(ImagesConst.appLogo)
                    error(ImagesConst.appLogo)
                }).build()
            )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
