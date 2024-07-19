package com.example.practicecompose

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.practicecompose.ui.theme.PracticeComposeTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(isPreview: Boolean = false) {
    if (isPreview) {
        Box(modifier = Modifier.fillMaxSize()) {

            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { /* Handle map press gestures */ }
                        )
                    }
            )


            MapView(isPreview = true)
            SearchBar()

            Row(
                modifier = Modifier
                    .width(500.dp)
                    .padding(top = 30.dp)
                    .padding(start = 15.dp)
            ) {
                //Render on Preview
                BackButton()
                FilterButton()
                ListButton()
            }
            CurrentLocationButton()

        }
        Column (modifier = Modifier.padding(top = 300.dp)){
            HorizontalScrollableList()
        }


    } else {
        val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

        LaunchedEffect(key1 = permissionState.status) {
            if (!permissionState.status.isGranted) {
                permissionState.launchPermissionRequest()
            }
        }

        if (permissionState.status.isGranted) {
            Box(modifier = Modifier.fillMaxSize()) {
                MapView()
                SearchBar()

                Row(
                    modifier = Modifier
                        .width(500.dp)
                        .padding(top = 30.dp)
                        .padding(start = 15.dp)
                ) {
                    //Render on Preview
                    BackButton()
                    FilterButton()
                    ListButton()
                }
                CurrentLocationButton()

            }
            Column (modifier = Modifier.padding(top = 300.dp)){
                HorizontalScrollableList()
            }


        } else {
            // Handle permission not granted case
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Location permission required")
            }
        }
    }
}

@Composable
fun MapView(isPreview: Boolean = false) {
    if (isPreview) {
        MockMapView()
    } else {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(-34.0, 151.0), 10f)
        }


        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true,
                isBuildingEnabled = true,
                isIndoorEnabled = true,
                isTrafficEnabled = true,
            )
        )


    }
}

@Composable
fun MockMapView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Map View Placeholder", color = Color.White)

    }
}

@Composable
fun SearchBar() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }


    Row(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 85.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = textState,
            onValueChange = { textState = it },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            singleLine = true
        ) {
            if (textState.text.isEmpty()) {
                Text("Search location / name / country", color = Color.Gray)
            } else {
                Text(textState.text)
            }
        }
        IconButton(onClick = { /* Handle search click */ }) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
        }
    }
}


@Composable
fun BackButton() {
    Button(
        onClick = { /* TODO */ },
        shape = RoundedCornerShape(67.dp), // Set a high value for rounded corners
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .size(66.dp), // Adjust size to make the button smaller but still fit the icon
        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.new_back_arrow), // Replace with your image resource
            contentDescription = "Filter",
            tint = Color(0xFFEC5800), // Set the tint color directly
            modifier = Modifier.size(24.dp) // Adjust size to make the icon visible
        )
    }

    Spacer(modifier = Modifier.width(150.dp))
}


@Composable
fun FilterButton() {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .wrapContentSize()
    ) {
        // List Button
        Button(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(67.dp), // Set a high value for rounded corners
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.size(66.dp), // Adjust size to make the button smaller but still fit the icon
            elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter), // Replace with your image resource
                contentDescription = "Filter",
                tint = Color(0xFFEC5800), // Set the tint color directly
                modifier = Modifier.size(24.dp) // Adjust size to make the icon visible
            )
        }
    }

    Spacer(modifier = Modifier.width(13.dp))

}

@Composable
fun ListButton() {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .wrapContentSize()
    ) {
        // List Button
        Button(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(67.dp), // Set a high value for rounded corners
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.size(66.dp), // Adjust size to make the button smaller but still fit the icon
            elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.list_icon), // Replace with your image resource
                contentDescription = "List",
                tint = Color(0xFFEC5800), // Set the tint color directly
                modifier = Modifier.size(24.dp) // Adjust size to make the icon visible
            )
        }
    }


}


@Composable
fun CurrentLocationButton() {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .padding(top = 510.dp, start = 320.dp)
            .wrapContentSize()
    ) {
        // List Button
        Button(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(67.dp), // Set a high value for rounded corners
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.size(66.dp), // Adjust size to make the button smaller but still fit the icon
            elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.target), // Replace with your image resource
                contentDescription = "List",
                tint = Color(0xFFEC5800), // Set the tint color directly
                modifier = Modifier.width(80.dp) // Adjust size to make the icon visible
            )
        }
    }

}

@Composable
fun HorizontalScrollableList() {
    val items = listOf(
        "Azure Haven Resort",
        "Crimson Peak Lodge",
        "Golden Sands Retreat",
        "Emerald Isle Hotel",
        "Silver Creek Inn"
    )


    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth().padding(top = 275.dp) //push card row down
            .padding(bottom = 16.dp)
    ) {

        items(items.size) { index ->
            CardItem(title = items[index])
        }
    }

}

@Composable
fun CardItem(title: String) {

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(1.dp)
            .size(width = 300.dp, height = 180.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.hotel_pic),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row {
                        Text(
                            text = title,
                            modifier = Modifier.padding(start = 7.dp)
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                        Image(
                            modifier = Modifier.size(24.dp),
                            contentScale = ContentScale.Fit,
                            painter = painterResource(id = R.drawable.rating_star),
                            contentDescription = "Rating Star"
                        )
                        Text(
                            text = "4.8",
                            modifier = Modifier.padding(start = 7.dp)
                        )
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    PracticeComposeTheme {
        MapScreen(isPreview = true)
    }
}