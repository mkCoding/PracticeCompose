package com.example.practicecompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin





@Composable
fun RatingBarComposable() {
    var rating by remember { mutableStateOf(0f) }
    val outlinedStar = painterResource(id = R.drawable.outline_star)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val width = size.width.toFloat()
                    val position = change.position.x.coerceIn(0f, width)
                    rating = (position / (width / 5)).coerceIn(0f, 5f)
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(5) { index ->
                Box(modifier = Modifier.size(36.dp).padding(4.dp)) {
                    Icon(
                        painter = outlinedStar,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    val filledPercentage = (rating - index).coerceIn(0f, 1f)
                    if (filledPercentage > 0) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val starPath = createStarPath(size.minDimension)
                            clipPath(starPath) {
                                drawPath(
                                    path = starPath,
                                    color = Color(0xFFFFA726).copy(alpha = filledPercentage)
                                )
                            }
                        }
                    }
                }
            }
        }
        Text(text = "Rating: ${"%.1f".format(rating)}", modifier = Modifier.padding(top = 16.dp))
    }
}

fun createStarPath(size: Float): Path {
    val path = Path()
    val mid = size / 2
    val radius = mid
    val innerRadius = mid / 2.5

    path.moveTo(mid, 0f)
    for (i in 1..5) {
        val angle = i * 72.0
        path.lineTo(
            (mid + radius * cos(Math.toRadians(angle - 18))).toFloat(),
            (radius * sin(Math.toRadians(angle - 18))).toFloat()
        )
        path.lineTo(
            (mid + innerRadius * cos(Math.toRadians(angle - 54))).toFloat(),
            (innerRadius * sin(Math.toRadians(angle - 54))).toFloat()
        )
    }
    path.close()
    return path
}



@Preview(showBackground = true)
@Composable
fun PreviewRatingBarComposable(){

    RatingBarComposable()
}