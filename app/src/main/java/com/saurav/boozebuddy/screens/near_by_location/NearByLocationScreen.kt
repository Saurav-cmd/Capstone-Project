package com.saurav.boozebuddy.screens.near_by_location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.saurav.boozebuddy.constants.ImagesConst
import com.saurav.boozebuddy.constants.ThemeUtils
import com.saurav.boozebuddy.models.PlaceResult
import com.saurav.boozebuddy.view_models.NearByLocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale

@Composable
fun NearByLocationScreen(
    nearByLocationViewModel: NearByLocationViewModel
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var userLocation by remember { mutableStateOf<Location?>(null) }
    var userAddress by remember { mutableStateOf<String?>(null) }
    var nearbyPlaces by remember { mutableStateOf<List<PlaceResult>>(emptyList()) }
    var permissionGranted by remember { mutableStateOf(false) }

    // Request location permissions using ActivityResultContracts
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            permissionGranted = isGranted
        }
    )

    // Check for permissions and fetch location if granted
    LaunchedEffect(key1 = permissionGranted) {
        if (permissionGranted) {
            val location = fusedLocationClient.lastLocation.await()
            userLocation = location
            location?.let {
                userAddress = getAddressFromLocation(context, it)
                nearbyPlaces = nearByLocationViewModel.fetchNearbyPlaces(it)
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Check initial permission status
    LaunchedEffect(key1 = true) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            permissionGranted = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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
                // Add user location marker with custom icon if available
                userLocation?.let { location ->
                    val scaledBitmap = getScaledBitmap(context, ImagesConst.mapPeople, 100, 100)
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(location.latitude, location.longitude),
                        ),
                        title = userAddress ?: "Your Location",
                        snippet = "Current Location",
                        icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap)
                    )
                }

                // Add markers for nearby places
                nearbyPlaces.forEach { place ->
                    Marker(
                        state = rememberMarkerState(
                            position = LatLng(place.geometry.location.lat, place.geometry.location.lng),
                        ),
                        title = place.name,
                        snippet = place.vicinity
                    )
                }

                // Add hardcoded LCBO locations
                val lcboLocations = listOf(
                    LatLng(43.00367833650152, -81.22728456513236),
                    LatLng(43.00416970434645, -81.17178251905588)
                )

                lcboLocations.forEach { latLng ->
                    Marker(
                        state = rememberMarkerState(position = latLng),
                        title = "LCBO Store",
                        snippet = "Liquor Store"
                    )
                }
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

private fun getScaledBitmap(context: Context, resourceId: Int, width: Int, height: Int): Bitmap {
    val originalBitmap = BitmapFactory.decodeResource(context.resources, resourceId)
    return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
}



//@Composable
//fun NearByLocationScreen(
//   nearByLocationViewModel: NearByLocationViewModel
//) {
//    val context = LocalContext.current
//    val fusedLocationClient: FusedLocationProviderClient = remember {
//        LocationServices.getFusedLocationProviderClient(context)
//    }
//    var userLocation by remember { mutableStateOf<Location?>(null) }
//    var userAddress by remember { mutableStateOf<String?>(null) }
//    var nearbyPlaces by remember { mutableStateOf<List<PlaceResult>>(emptyList()) }
//    var permissionGranted by remember { mutableStateOf(false) }
//
//    // Request location permissions using ActivityResultContracts
//    val requestPermissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission(),
//        onResult = { isGranted: Boolean ->
//            permissionGranted = isGranted
//        }
//    )
//
//    // Check for permissions and fetch location if granted
//    LaunchedEffect(key1 = permissionGranted) {
//        if (permissionGranted) {
//            val location = fusedLocationClient.lastLocation.await()
//            userLocation = location
//            location?.let {
//                userAddress = getAddressFromLocation(context, it)
//                nearbyPlaces = nearByLocationViewModel.fetchNearbyPlaces(it)
//            }
//        } else {
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }
//
//    // Check initial permission status
//    LaunchedEffect(key1 = true) {
//        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            permissionGranted = true
//        } else {
//            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//    }
//
//    val cameraPositionState = rememberCameraPositionState()
//
//    LaunchedEffect(userLocation) {
//        userLocation?.let {
//            cameraPositionState.move(
//                CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f)
//            )
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopContainer()
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .navigationBarsPadding()
//                .imePadding()
//        ) {
//            GoogleMap(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f), // Ensures the map takes up available space
//                cameraPositionState = cameraPositionState,
//            ) {
//                // Add user location marker with custom icon if available
//                userLocation?.let { location ->
//                    val scaledBitmap = getScaledBitmap(context, ImagesConst.mapPeople, 100, 100)
//                    Marker(
//                        state = rememberMarkerState(
//                            position = LatLng(location.latitude, location.longitude),
//                        ),
//                        title = userAddress ?: "Your Location",
//                        snippet = "Current Location",
//                        icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap)
//                    )
//                }
//
//                // Add markers for nearby places
//                nearbyPlaces.forEach { place ->
//                    Marker(
//                        state = rememberMarkerState(
//                            position = LatLng(place.geometry.location.lat, place.geometry.location.lng),
//                        ),
//                        title = place.name,
//                        snippet = place.vicinity
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun TopContainer() {
//    Text(
//        modifier = Modifier
//            .padding(horizontal = 20.dp)
//            .padding(top = 10.dp, bottom = 10.dp),
//        text = "Near By Liquor Stores",
//        style = TextStyle(
//            color = ThemeUtils.colors.secondary,
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold
//        )
//    )
//}
//
//private suspend fun getAddressFromLocation(context: Context, location: Location): String? {
//    return withContext(Dispatchers.IO) {
//        try {
//            val geocoder = Geocoder(context, Locale.getDefault())
//            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//            addresses?.firstOrNull()?.getAddressLine(0)
//        } catch (e: Exception) {
//            null
//        }
//    }
//}
//
//private fun getScaledBitmap(context: Context, resourceId: Int, width: Int, height: Int): Bitmap {
//    val originalBitmap = BitmapFactory.decodeResource(context.resources, resourceId)
//    return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
//}
