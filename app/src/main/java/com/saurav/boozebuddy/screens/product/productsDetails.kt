package com.saurav.boozebuddy.screens.product

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.Product
import com.saurav.boozebuddy.ui.theme.lightGrey
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.FavouritesViewModel

@Composable
fun ProductsDetailPage(
    navHostController: NavHostController,
    favouritesViewModel: FavouritesViewModel,
    productInfo: Product,
    brandName: String?,
    brandId: String?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightGrey)
    ) {
        item {
            ProductImage(navHostController, productInfo.productImage)
        }
        item {
            ProductDetailsSection(
                productInfo.productName,
                productInfo.productDescription,
                favouritesViewModel,
                productInfo.productImage ?: "",
                productInfo.productId,
                brandName,
                brandId
            )
        }
    }
}


@Composable
fun ProductImage(navController: NavHostController, productImage: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .background(color = Color.LightGray)
    ) {

        val painter =
            rememberAsyncImagePainter(
                ImageRequest.Builder // Placeholder image resource
                // Image to show in case of loading failure
                    (LocalContext.current).data(data = productImage)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(ImagesConst.people)
                        error(ImagesConst.people)
                        size(Size.ORIGINAL)
                    }).build()
            )

        Image(
            painter = painter,
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop, // Maintain aspect ratio and crop if needed
            modifier = Modifier.fillMaxSize() // Take full height and width
        )

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 10.dp)
                .clickable {
                    navController.navigateUp()
                    Log.e("THis is url", "$productImage")
                }
        )
    }
}


@Composable
fun ProductDetailsSection(
    productName: String?,
    productDetail: String?,
    favouritesViewModel: FavouritesViewModel,
    productImage: String,
    productId: String?,
    brandName: String?,
    brandId: String?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Fill the entire screen height
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(lightGrey)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxSize()
        ) {
            Details(
                productName,
                productDetail,
                favouritesViewModel,
                productImage,
                productId,
                brandName,
                brandId
            )
            Spacer(modifier = Modifier.height(25.dp))
//            PriceAndQuantity()
            Spacer(modifier = Modifier.weight(1f)) // Fill remaining space
            AddCartButton()
        }
    }
}


@Composable
private fun Details(
    productName: String?,
    productDetail: String?,
    favouritesViewModel: FavouritesViewModel,
    productImage: String,
    productId: String?,
    brandName: String?,
    brandId: String?
) {
    val context = LocalContext.current
    val isAddingToFavourite by favouritesViewModel.isStoringFavourites.observeAsState(initial = false)
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
                text = "$productName",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Red
                ),
                onClick = {
                  if(favouritesViewModel.checkIfAlreadyFavourite(productId?:"")){
                      Toast.makeText(context, "Already in Favourites List", Toast.LENGTH_SHORT).show()
                  }else{
                      favouritesViewModel.storeIsFavourites(
                          productName ?: "",
                          brandName ?: "",
                          productImage,
                          productId ?: "",
                          brandId ?: ""
                      ) { success, errMsg ->
                          if (success) {
                              Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                              favouritesViewModel.checkIfAlreadyFavourite(productId ?: "")
                          } else {
                              //error
                              Toast.makeText(context, "Error $errMsg", Toast.LENGTH_SHORT).show()
                          }
                      }
                  }

                }
            ) {
                if(favouritesViewModel.checkIfAlreadyFavourite(productId?:"")){
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourites"
                    )
                }else{
                    if (isAddingToFavourite) CircularProgressIndicator() else Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favourites"
                    )
                }



            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Description(productDetail)
    }
}

@Composable
private fun Description(productDetail: String?) {
    Text(
        text = "$productDetail",
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
            .background(Color.Gray)
    ) {
        Text(
            text = value,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = primaryColor
        )
    }
}

@Composable
fun AddCartButton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp) // Adjust padding as needed
    ) {
        Button(
            onClick = {
                // Handle button click
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = secondaryColor,
                contentColor = primaryColor
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter) // Aligns the button at the bottom center
                .padding(horizontal = 40.dp) // Adjust horizontal padding if necessary
        ) {
            Text(
                text = "Add to Wishlist",
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W500)
            )
        }
    }
}
