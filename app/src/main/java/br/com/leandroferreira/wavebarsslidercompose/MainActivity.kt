package br.com.leandroferreira.wavebarsslidercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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
            .height(50.dp)
            .width(250.dp),
          color = MaterialTheme.colors.background
        ) {
          AudioWaveView(
            waveBars = buildList {
              repeat(30) { i ->
                add(i / 30F)
              }
            }, modifier = Modifier
              .height(50.dp)
              .width(200.dp),
          )
        }
      }
    }
  }
}
