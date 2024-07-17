package com.saurav.boozebuddy.screens.near_by_location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.saurav.boozebuddy.constants.ThemeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NearByLocationScreen() {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var userLocation by remember { mutableStateOf<Location?>(null) }
    var userAddress by remember { mutableStateOf<String?>(null) }

    // Request location permissions using ActivityResultContracts
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, fetch user location
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        userLocation = location
                    }
            }
        }
    )

    // Request location permissions if not granted
    LaunchedEffect(key1 = true) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    userLocation = location
                }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Fetch the address from the location
    LaunchedEffect(userLocation) {
        userLocation?.let { location ->
            userAddress = getAddressFromLocation(context, location)
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f)
            )
        }
    }

    Scaffold(
        topBar = {
            TopContainer()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .navigationBarsPadding()
                .imePadding()
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f), // Ensures the map takes up available space
                cameraPositionState = cameraPositionState,
            ) {
                // Add user location marker if available
                userLocation?.let { location ->
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(location.latitude, location.longitude),
                        ),
                        title = userAddress ?: "Your Location",
                        snippet = "Current Location"
                    )
                }

                // Add markers for nearby places
                Marker(
                    state = rememberMarkerState(
                        position = LatLng(43.65107, -79.347015),
                    ),
                    title = "LCBO",
                    snippet = "Liquor Store"
                )
                // Add more markers for other nearby places
            }
        }
    }
}

@Composable
private fun TopContainer() {
    Text(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp, bottom = 10.dp),
        text = "Near By Liquor Stores",
        style = TextStyle(
            color = ThemeUtils.colors.secondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

private suspend fun getAddressFromLocation(context: Context, location: Location): String? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            addresses?.firstOrNull()?.getAddressLine(0)
        } catch (e: Exception) {
            null
        }
    }
}
