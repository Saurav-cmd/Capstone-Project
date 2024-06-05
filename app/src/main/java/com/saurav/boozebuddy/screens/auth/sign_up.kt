
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saurav.boozebuddy.R
import com.saurav.boozebuddy.ui.theme.bodyColor
import com.saurav.boozebuddy.ui.theme.primaryColor
import com.saurav.boozebuddy.ui.theme.secondaryColor

@Composable
fun SignupPage(){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            ImageView()
        }
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)){
                Text(
                    text = "Create \n Account", style = TextStyle(
                        color = primaryColor,
                        fontWeight = FontWeight.Bold, fontSize = 25.sp
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            "Name".TextFormField()
        }
        item {
            "Email".TextFormField()
        }
        item {
            "Password".TextFormField()
        }
        item {
            "Re Enter Password".TextFormField()
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                CustomButton()
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Already Have an Account? Sign In",
                    style = TextStyle(
                        color = secondaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
        }

    }
}

@Composable
private fun ImageView() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.signup),
            contentDescription = "Promotion Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun CustomButton() {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = bodyColor
        )
        ) {
        Text(text = "Sign Up")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun String.TextFormField() {
    var textFieldValue by remember { mutableStateOf("") }
    TextField(
        value = textFieldValue,
        onValueChange = { data ->
            textFieldValue = data
        },
        placeholder = { Text(text = this, color = secondaryColor) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 10.dp)
            .border(width = 1.dp, color = primaryColor, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = secondaryColor,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}