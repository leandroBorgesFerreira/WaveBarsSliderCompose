package br.com.leandroferreira.wavebarsslidercompose.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.leandroferreira.wavebarsslidercompose.ui.theme.WaveBarsSliderComposeTheme
import java.lang.Float.max
import java.lang.Float.min

private const val DEFAULT_TRACKER_HEIGHT_DP = 60
private const val DEFAULT_TRACKER_WIDTH = 4

@Composable
fun AudioWaveView(
  waveBars: List<Float>,
  modifier: Modifier = Modifier,
  colorLeft: Color = Color.Blue,
  colorRight: Color = Color.Gray,
  trackerDraw: DrawScope.(Float, Size) -> Unit = { progressWidth, trackerSize ->
    drawRoundRect(
      color = Color.Red,
      topLeft = Offset(
        min(
          max(progressWidth - trackerSize.width / 2, 0F),
          size.width - trackerSize.width
        ),
        0F
      ),
      size = trackerSize,
      cornerRadius = CornerRadius(10F, 10F)
    )
  } ,
) {

  var progress by remember { mutableStateOf(0F) }
  var isDragging by remember { mutableStateOf(false) }

  var seekWidth: Float? = null
  val barSpacing = 0.4

  Canvas(modifier = modifier.pointerInput(Unit) {
    detectHorizontalDragGestures(
      onDragStart = {
        isDragging = true
      },
      onHorizontalDrag = { change, _ ->
        progress = change.position.x * 100F / (seekWidth ?: 10F)
      },
      onDragCancel = {
        isDragging = false
      },
      onDragEnd = {
        isDragging = false
      }
    )
  }) {
    seekWidth = size.width

    val totalSpaceWidth = size.width * barSpacing
    val totalBarWidth = size.width - totalSpaceWidth

    val barWidth = totalBarWidth / waveBars.size
    val spaceWidth = totalSpaceWidth / waveBars.size
    val progressToWidth = progress / 100F * size.width
    val trackerHeight = DEFAULT_TRACKER_HEIGHT_DP

    val trackerWidth = DEFAULT_TRACKER_WIDTH.dp.toPx()

    repeat(waveBars.size) { i ->
      val barHeight = max(size.height * waveBars[i], 3.dp.toPx())
      val left = (barWidth + spaceWidth) * i
      val right = left + barWidth
      val top = (size.height - barHeight) / 2
      val bottom = top + barHeight

      val color = if (progressToWidth > left) colorLeft else colorRight
      val barRect = Rect(left.toFloat(), top, right.toFloat(), bottom)

      drawRoundRect(
        color = color,
        topLeft = barRect.topLeft,
        size = barRect.size,
        cornerRadius = CornerRadius(50F, 50F)
      )
    }

    trackerDraw(this, progressToWidth, Size(trackerWidth.dp.toPx(), size.height))
  }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  WaveBarsSliderComposeTheme {
    AudioWaveView(
      waveBars = buildList {
        repeat(30) { i ->
          add(i / 30F)
        }
      },
      modifier = Modifier
        .height(70.dp)
        .width(250.dp),
    )
  }
}




