import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize().padding(2.dp)
        ) {

            val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            var seconds by remember {
                mutableFloatStateOf((time.second) % 60f)
            }

            var mins by remember {
                mutableFloatStateOf(time.minute % 60f)
            }

            var hour by remember {
                mutableFloatStateOf((time.hour) % 12f)
            }


            LaunchedEffect(key1 = seconds) {
                delay(1000L)
                seconds += 1f
                mins += 1f / 60
                hour += 1f / (3600f)
            }

            Column(Modifier.align(Alignment.Center)) {
                Clock(
                    150.dp,
                    seconds,
                    mins,
                    hour,
                )
            }
        }
    }
}