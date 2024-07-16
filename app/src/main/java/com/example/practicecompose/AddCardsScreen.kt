package com.example.practicecompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CardListScreen() {
    // State to hold the list of cards
    var cards by remember { mutableStateOf(listOf<String>()) }


    // A counter to generate unique card names
    var cardCounter by remember { mutableStateOf(0) }

    val scrollState = rememberLazyListState()


    Column(modifier = Modifier.padding(16.dp)) {
        // Button to add a new card
        Button(
            onClick = {
                cardCounter++ // Increment counter for a unique card name
                cards = cards + "Card ${cardCounter}" // Add a new card

            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Add Card")
        }

        // Displaying cards in a scrollable row
        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between cards
        ) {
            items(cards) { card ->
                Card(
                    modifier = Modifier
                        .size(120.dp, 180.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Cyan
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(card)
                    }
                }
            }
        }
    }

    // Scroll will jump to newly created card
    LaunchedEffect(cards.size) {
        if (cards.isNotEmpty()) {
            scrollState.animateScrollToItem(cards.lastIndex)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCardListScreen() {
    CardListScreen()
}
