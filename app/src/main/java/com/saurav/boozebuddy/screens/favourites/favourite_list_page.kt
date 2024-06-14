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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.secondaryColor

@Composable
fun FavouritesListPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding() // Automatically adds padding for the bottom navigation bar
            .imePadding()
    ) {
        TopContainer(navController = navController)
        Spacer(modifier = Modifier.height(15.dp))
        FavouritesList()
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

@Composable
private fun FavouritesList() {
    return LazyColumn(content = {
        items(count = 5) {
            FavouritesDesign()
            Spacer(modifier = Modifier.height(15.dp))
        }
    })
}

@Composable
private fun FavouritesDesign() {
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
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = ImagesConst.simrsOff),
                    contentDescription = "Products Details Here",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            Column {
                Text(
                    text = "Grey Goose Vodka",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = secondaryColor
                    )
                )
                Text(
                    text = "Vodka",
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
