package com.example.practicecompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.io.path.Path
import kotlin.math.min

@Composable
fun StarRating() {
    var rating by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Stars(rating = rating, onRatingChange = { rating = it })
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = String.format("%.1f", rating))
    }
}

@Composable
fun Stars(rating: Float, onRatingChange: (Float) -> Unit) {
    val starCount = 5
    val starSize = 40.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(starSize)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val newRating = min(starCount.toFloat(), (change.position.x / (starSize.toPx()) + 1) / starCount * starCount)
                    onRatingChange(newRating)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (i in 0 until starCount) {
                val left = i * starSize.toPx()
                val filledRatio = min(1f, rating - i)
                drawStar(
                    size = Size(starSize.toPx(), starSize.toPx()),
                    left = left,
                    filledRatio = filledRatio
                )
            }
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(size: Size, left: Float, filledRatio: Float) {
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(left + size.width / 2f, 0f)
        lineTo(left + size.width * 0.6f, size.height * 0.4f)
        lineTo(left + size.width, size.height * 0.4f)
        lineTo(left + size.width * 0.7f, size.height * 0.6f)
        lineTo(left + size.width * 0.8f, size.height)
        lineTo(left + size.width / 2f, size.height * 0.75f)
        lineTo(left + size.width * 0.2f, size.height)
        lineTo(left + size.width * 0.3f, size.height * 0.6f)
        lineTo(left, size.height * 0.4f)
        lineTo(left + size.width * 0.4f, size.height * 0.4f)
        close()
    }
    drawPath(path, Color.Gray)
    if (filledRatio > 0) {
        drawPath(
            path = path,
            color = Color(0xFFFFA500),
            alpha = filledRatio
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStarRating(){
    StarRating()
}
