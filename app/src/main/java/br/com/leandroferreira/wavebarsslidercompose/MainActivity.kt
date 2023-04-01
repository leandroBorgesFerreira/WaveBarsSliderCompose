package br.com.leandroferreira.wavebarsslidercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.leandroferreira.wavebarsslidercompose.ui.theme.WaveBarsSliderComposeTheme
import br.com.leandroferreira.wavebarsslidercompose.ui.view.AudioWaveView

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WaveBarsSliderComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier
            .height(150.dp)
            .width(250.dp),
          color = MaterialTheme.colors.background
        ) {
          AudioWaveView(
            waveBars = buildList {
              repeat(30) { i ->
                add(i / 30F)
              }
            },
            modifier = Modifier
              .height(50.dp)
              .width(200.dp)
              .padding(horizontal = 30.dp, 6.dp),
            trackerFunc = {
              Box(
                modifier = Modifier
                  .width(5.dp)
                  .height(5.dp)
                  .clip(RectangleShape)
                  .background(Color(0x5500FF00))
              )
            }
          )
        }
      }
    }
  }
}
