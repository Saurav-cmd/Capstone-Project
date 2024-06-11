package com.saurav.boozebuddy.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saurav.boozebuddy.app_navigation.NavRoute
import com.saurav.boozebuddy.constants.ThemeUtils.colors
import com.saurav.boozebuddy.ui.theme.secondaryColor
import com.saurav.boozebuddy.view_models.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    StylishBBAnimation {
        if(authViewModel.checkIfUserAlreadyLoggedIn()){
            navController.navigate(NavRoute.BottomNavigation.route) {
                popUpTo(NavRoute.Splash.route) { inclusive = true }
            }
        }else{
            navController.navigate(NavRoute.Login.route){
                popUpTo(NavRoute.Splash.route) {inclusive = true}
            }
        }

    }
}

@Composable
fun StylishBBAnimation(onAnimationEnd: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000, easing = LinearEasing), label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2000) // Duration of the animation
        onAnimationEnd()
    }

    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = "BB",
            style = TextStyle(
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = colors.secondary
            )
        )
        CircularProgressIndicator(progress = animatedProgress)
    }
}

@Composable
fun CircularProgressIndicator(progress: Float) {
    Canvas(modifier = Modifier.size(200.dp)) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        drawArc(
            color = secondaryColor,
            startAngle = 0f,
            sweepAngle = progress * 360f,
            useCenter = false,
            style = Stroke(
                width = 8f,
                pathEffect = pathEffect,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}
