package com.example.practicecompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
    var cards by remember { mutableStateOf(listOf<String>()) }
    var cardCounter by remember { mutableStateOf(0) }
    val scrollState = rememberLazyListState()

    Column(modifier = Modifier.padding(16.dp)) {
        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cards) { card ->
                AnimatedVisibility(
                    visible = true,  // This should typically be tied to a state
                    enter = fadeIn(animationSpec = tween(durationMillis = 300))
                ) {
                    Card(
                        modifier = Modifier
                            .size(120.dp, 180.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Cyan)
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

            // Add Card button
            item {
                Card(
                    modifier = Modifier
                        .size(120.dp, 180.dp)
                        .padding(8.dp)
                        .clickable {
                            cardCounter++
                            cards = cards + "Card ${cardCounter}"
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Add Card")
                    }
                }
            }
        }

        // Auto-scroll to keep "Add Card" button visible
        LaunchedEffect(cards.size) {
            if (cards.isNotEmpty()) {
                scrollState.animateScrollToItem(cards.size)
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewCardListScreen() {
    CardListScreen()
}
