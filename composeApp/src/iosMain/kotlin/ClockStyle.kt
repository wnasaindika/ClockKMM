import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.skia.BlendMode
import org.jetbrains.skia.Font
import org.jetbrains.skia.Paint
import org.jetbrains.skia.TextLine
import org.jetbrains.skia.Typeface
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
actual fun Clock(
    radius: Dp,
    seconds: Float,
    minutes: Float,
    hour: Float
) {
    Canvas(modifier = Modifier.size(radius)) {
        rotate(degrees = -90f) {
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    x = center.x,
                    y = center.y,
                    radius = radius.toPx(),
                    Paint().apply {
                        color = org.jetbrains.skia.Color.makeARGB(50, 0, 0, 0)
                        strokeWidth = 20.dp.toPx()
                        setStroke(true)
                        blendMode = BlendMode.HARD_LIGHT
                    }
                )
            }

            val skiaFont = Font(Typeface.makeDefault())
            skiaFont.size = 18.sp.toPx()

            for (i in 0..59) {
                val angleInRad = i * (360f / 60f) * (PI.toFloat() / 180f)
                val markerLength = if (i % 5 == 0) 15.dp.toPx() else 5.dp.toPx()
                val markerColor = if (i % 5 == 0) Color.DarkGray else Color.Gray

                val start = Offset(
                    x = radius.toPx() * cos(angleInRad) + center.x,
                    y = radius.toPx() * sin(angleInRad) + center.y
                )

                val end = Offset(
                    x = (radius.toPx() - markerLength) * cos(angleInRad) + center.x,
                    y = (radius.toPx() - markerLength) * sin(angleInRad) + center.y
                )

                drawLine(
                    color = markerColor,
                    start = start,
                    end = end,
                    strokeWidth = 2.dp.toPx()
                )
                drawContext.canvas.nativeCanvas.apply {
                    val textRadius = radius.toPx() - markerLength - 21.dp.toPx()
                    val x = textRadius * cos(angleInRad) + center.x
                    val y = textRadius * sin(angleInRad) + center.y

                    val textAngle = when (i) {
                        45 -> -180f
                        15 -> 0f
                        in 16..44 -> -90f
                        else -> 90f
                    }

                    val clockText = when (i) {
                        0 -> "12"
                        else -> if (i % 5 == 0) "${i / 5}" else ""
                    }
                    rotate(
                        degrees = angleInRad * (180f / PI.toFloat()) + textAngle,
                        pivot = Offset(x, y)
                    ) {
                        drawTextLine(
                            TextLine.Companion.make(clockText, skiaFont),
                            x,
                            y,
                            Paint().apply {
                                color = org.jetbrains.skia.Color.makeARGB(100, 0, 0, 0)
                            }
                        )
                    }
                }

            }
            rotate(degrees = seconds * (360f / 60f) - 90f) {
                drawLine(
                    color = Color.LightGray,
                    strokeWidth = 3.dp.toPx(),
                    start = center,
                    end = Offset(center.x, radius.toPx() * 1.5f),
                    cap = StrokeCap.Round
                )
            }

            rotate(degrees = minutes * (360f / 60f) - 90f) {
                drawLine(
                    color = Color.Gray,
                    strokeWidth = 5.dp.toPx(),
                    start = center,
                    end = Offset(center.x, radius.toPx() * 1.25f),
                    cap = StrokeCap.Round
                )
            }

            rotate(degrees = hour * (360f / 12f) - 90f) {
                drawLine(
                    color = Color.DarkGray,
                    strokeWidth = 7.dp.toPx(),
                    start = center,
                    end = Offset(center.x, radius.toPx()),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
