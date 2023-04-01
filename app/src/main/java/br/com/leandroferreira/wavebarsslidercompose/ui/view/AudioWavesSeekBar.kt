package br.com.leandroferreira.wavebarsslidercompose.ui.view

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalDragOrCancellation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.leandroferreira.wavebarsslidercompose.ui.theme.WaveBarsSliderComposeTheme
import java.lang.Float.max


@Composable
fun AudioWaveView(
  waveBars: List<Float>,
  modifier: Modifier = Modifier,
  colorLeft: Color = Color.Blue,
  colorRight: Color = Color.Gray,
) {

  var progress by remember { mutableStateOf(30F) }

  val barSpacing = 0.4

  Canvas(modifier = modifier.pointerInput(Unit) {
    detectDragGestures { change, _ ->
      progress = change.position.x.div(250.dp.toPx()).times(100)
    }
  }) {
    val totalSpaceWidth = size.width * barSpacing
    val totalBarWidth = size.width - totalSpaceWidth

    val barWidth = totalBarWidth / waveBars.size
    val spaceWidth = totalSpaceWidth / waveBars.size
    val progressToWidth = progress / 100F * size.width

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




