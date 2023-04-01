package br.com.leandroferreira.wavebarsslidercompose.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.leandroferreira.wavebarsslidercompose.ui.theme.WaveBarsSliderComposeTheme
import java.lang.Float.max

private const val DEFAULT_TRACKER_HEIGHT_DP = 60
private const val DEFAULT_TRACKER_WIDTH_DP = 20

@Composable
fun AudioWaveView(
  waveBars: List<Float>,
  modifier: Modifier = Modifier,
  colorLeft: Color = Color.Blue,
  colorRight: Color = Color.Gray,
  trackerFunc: @Composable () -> Unit
) {

  var progress by remember { mutableStateOf(0F) }
  var seekWidth: Float? = null
  val barSpacing = 0.4


  Canvas(modifier = modifier.pointerInput(Unit) {
    detectDragGestures { change, _ ->
      progress = change.position.x * 100F / (seekWidth ?: 10F)
    }
  }) {
    seekWidth = size.width

    val totalSpaceWidth = size.width * barSpacing
    val totalBarWidth = size.width - totalSpaceWidth

    val barWidth = totalBarWidth / waveBars.size
    val spaceWidth = totalSpaceWidth / waveBars.size
    val progressToWidth = progress / 100F * size.width
    val trackerHeight = DEFAULT_TRACKER_HEIGHT_DP
    val trackerWidth = DEFAULT_TRACKER_WIDTH_DP

    repeat(waveBars.size) { i ->
      val barHeight = max(size.height * waveBars[i], 3.dp.toPx())
      val left = (barWidth + spaceWidth) * i
      val right = left + barWidth
      val top = (size.height - barHeight) / 2
      val bottom = top + barHeight

      val color = if (progressToWidth > left + barWidth / 2) colorLeft else colorRight
      val barRect = Rect(left.toFloat(), top, right.toFloat(), bottom)

      drawRoundRect(
        color = color,
        topLeft = barRect.topLeft,
        size = barRect.size,
        cornerRadius = CornerRadius(50F, 50F)
      )
    }

    drawRoundRect(
      color = Color.Red,
      topLeft = Offset(
        0F - trackerWidth.dp.toPx() / 2 + progressToWidth,
        (size.height / 2) - trackerHeight.dp.toPx() / 2
      ),
      size = Size(trackerWidth.dp.toPx(), trackerHeight.dp.toPx()),
      cornerRadius = CornerRadius(10F, 10F)
    )
  }

//  Layout(content = trackerFunc, modifier = modifier) { measurables, constraints ->
//    val trackerPlaceable = measurables.first().measure(constraints)
//
//    layout(constraints.maxWidth, constraints.maxHeight) {
//      trackerPlaceable.place(0, 0)
//    }
//  }
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
      trackerFunc = {
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(RectangleShape)
            .background(Color.Red)
        )
      }
    )
  }
}




