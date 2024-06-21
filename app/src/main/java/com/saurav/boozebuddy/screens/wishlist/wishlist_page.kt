package com.saurav.boozebuddy.screens.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.WishListProducts
import com.saurav.boozebuddy.models.WishlistModel
import com.saurav.boozebuddy.ui.theme.errorColor
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.WishlistViewModel
import java.util.Locale


@Composable
fun WishListPage(wishlistViewModel: WishlistViewModel) {

    LaunchedEffect(Unit) {
        wishlistViewModel.fetchWishlist()
    }

    val isFetchingWishList by wishlistViewModel.isFetchingWishList.observeAsState(initial = false)
    val wishListData by wishlistViewModel.wishListValue.observeAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding(), // Adds padding for the on-screen keyboard if needed
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        TopContainer()
        if (isFetchingWishList) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = secondaryColor,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Loading Wishlist...", color = secondaryColor, fontSize = 18.sp)
            }
        } else if (wishListData.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No Wishlist to show", color = errorColor, textAlign = TextAlign.Center)
            }
        } else {
            DetailContainer(wishListData)
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
    }
}

@Composable
private fun DetailContainer(wishListData: List<WishlistModel>) {
    LazyColumn(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 5.dp, top = 10.dp)
    ) {
        items(wishListData.size) { index ->
            val data = wishListData[index]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    data.wishName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    color = colors.secondary,
                    fontSize = 18.sp
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
            Divider()
        }
    }
}
