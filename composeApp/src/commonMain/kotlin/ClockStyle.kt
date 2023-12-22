import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
expect fun Clock(
    radius: Dp,
    seconds: Float,
    minutes: Float,
    hour: Float
)

