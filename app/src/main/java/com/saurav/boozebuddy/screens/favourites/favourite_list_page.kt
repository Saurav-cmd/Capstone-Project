
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.models.UserFavouritesModel
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.FavouritesViewModel

@Composable
fun FavouritesListPage(navController: NavHostController, favouritesViewModel: FavouritesViewModel) {
    favouritesViewModel.fetchUserFavourites()
    val isLoadingFavourites by favouritesViewModel.isFetchingUserFavourites.observeAsState(initial = false)
    val favouritesData by favouritesViewModel.userFavourites.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding()
    ) {
        TopContainer(navController = navController)
        Spacer(modifier = Modifier.height(15.dp))
        if (isLoadingFavourites) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.secondary)
            }
        } else if (favouritesData.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No Favourites!", fontSize = 18.sp, color = colors.error)
            }
        } else {
            FavouritesList(favouritesData)
        }
    }
}

@Composable
private fun FavouritesList(userFavourites: List<UserFavouritesModel>) {
    LazyColumn(content = {
        items(count = userFavourites.size) { index ->
            FavouritesDesign(userFavourites[index])
            Spacer(modifier = Modifier.height(15.dp))
        }
    })
}

@Composable
private fun FavouritesDesign(favourite: UserFavouritesModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 1.5.dp,
                        color = colors.secondary,
                        shape = RoundedCornerShape(20.dp)
                    )
            )
            {
                val painter =
                    rememberAsyncImagePainter(ImageRequest.Builder // Placeholder image resource
                    // Image to show in case of loading failure
                        (LocalContext.current).data(data = favourite.productImage)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                            placeholder(ImagesConst.people)
                            error(ImagesConst.people)
                        }).build()
                    )

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                )
            }
            Column {
                Text(
                    text = favourite.productName,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = secondaryColor
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = favourite.brandName.removePrefix("\"").removeSuffix("\""),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                )

            }
        }
    }
}



@Composable
private fun TopContainer(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = colors.secondary,
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    navController.navigateUp()
                }
        )

        Text(
            text = "Favourites",
            style = TextStyle(
                color = colors.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}