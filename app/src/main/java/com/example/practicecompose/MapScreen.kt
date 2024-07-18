package com.example.practicecompose

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.example.practicecompose.ui.theme.PracticeComposeTheme


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(isPreview: Boolean = false) {
    if (isPreview) {
        Box(modifier = Modifier.fillMaxSize()) {
            MapView(isPreview = true)
            SearchBar()

            Row(
                modifier = Modifier
                    .width(500.dp)
                    .padding(top = 14.dp)
                    .padding(start = 15.dp)
            ) {
                //Render on Preview
                BackButton()
                FilterButton()
                ListButton()
            }

            CurrentLocationButton()

//            BottomCard()

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

                //Render on device
                Row {
                    BackButton()
                    FilterButton()
                    ListButton()
                }

                CurrentLocationButton()

//                BottomCard()

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
            .padding(top = 80.dp)
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
//            .padding(start = 49.dp)
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
            .padding(top = 580.dp, start = 300.dp)
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
fun BottomCard() {
    Box(
        modifier = Modifier
            .padding(top = 700.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Example Hotel Cards
            HotelCard(hotelName = "Resort Hotel", rating = 4.8)
            Spacer(modifier = Modifier.width(8.dp))
            HotelCard(hotelName = "Windsor Hotel", rating = 4.4)
        }
    }
}


@Composable
fun HotelCard(hotelName: String, rating: Double) {
    Column(
        modifier = Modifier
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        // Replace with an Image composable for actual images
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray, RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(hotelName, style = MaterialTheme.typography.bodyMedium)
        Text("Rating: $rating", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun HorizontalScrollableList() {
    val items = listOf(
        "Card 1",
        "Card 2",
        "Card 3",
        "Card 4",
        "Card 5"
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
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
            .padding(top = 670.dp)
            .size(width = 270.dp, height = 150.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        )

        {
            Column() {
                Image(
                    painter = painterResource(id = R.drawable.hotel_pic),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentScale = ContentScale.Crop

                )

                Text(text = "Hotel Details")

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