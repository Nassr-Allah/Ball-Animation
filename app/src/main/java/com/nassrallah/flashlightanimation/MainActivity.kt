package com.nassrallah.flashlightanimation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.nassrallah.flashlightanimation.ui.theme.FlashlightAnimationTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashlightAnimationTheme {
                // A surface container using the 'background' color from the theme

                var xOffset by remember {
                    mutableStateOf(0f)
                }
                var yOffset by remember {
                    mutableStateOf(0f)
                }
                var circleXOffset by remember {
                    mutableStateOf(0f)
                }
                var circleYOffset by remember {
                    mutableStateOf(0f)
                }
                var pathPoints = mutableListOf<Offset>()
                var path by remember {
                    mutableStateOf(Path())
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            /*
                            .drawWithCache {
                                path.moveTo(xOffset, yOffset)
                                path.lineTo(circleXOffset, circleYOffset)
                                onDrawBehind {
                                    drawPath(
                                        path = path,
                                        color = Color.Yellow,
                                        style = Stroke(15f)
                                    )
                                }
                            }

                             */
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    xOffset = it.x
                                    yOffset = it.y
                                    //path.moveTo(xOffset, yOffset)
                                    //path.moveTo(xOffset, yOffset)
                                    Log.d("OnTap", "offset is: ${it.x}, ${it.y}")
                                    Log.d(
                                        "OnTap",
                                        "circle offset is: ${circleXOffset}, ${circleYOffset}"
                                    )
                                }
                            },
                    ) {
                        val xPxToMove by animateIntAsState(
                            targetValue = xOffset.toInt(),
                            label = "xPxToMove",
                            animationSpec = spring(
                                stiffness = Spring.StiffnessVeryLow,
                                dampingRatio = Spring.DampingRatioLowBouncy
                            ),
                        )
                        val yPxToMove by animateIntAsState(
                            targetValue = yOffset.toInt(),
                            label = "yPxToMove",
                            animationSpec = spring(
                                stiffness = Spring.StiffnessVeryLow,
                                dampingRatio = Spring.DampingRatioLowBouncy
                            ),
                        )
                        val xLineOffset by animateIntAsState(
                            targetValue = xOffset.toInt(),
                            label = "xLineOffset",
                            animationSpec = spring(
                                stiffness = Spring.StiffnessVeryLow,
                                dampingRatio = Spring.DampingRatioNoBouncy
                            )
                        )
                        val yLineOffset by animateIntAsState(
                            targetValue = yOffset.toInt(),
                            label = "yLineOffset",
                            animationSpec = spring(
                                stiffness = Spring.StiffnessVeryLow,
                                dampingRatio = Spring.DampingRatioNoBouncy
                            )
                        )
                        val offset by animateIntOffsetAsState(
                            targetValue = IntOffset(xPxToMove, yPxToMove),
                            label = "offset",
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMedium,
                                dampingRatio = Spring.DampingRatioLowBouncy,
                            ),
                        )

                        Box(
                            modifier = Modifier
                                .drawWithCache {
                                    path.lineTo(xLineOffset.toFloat(), yLineOffset.toFloat())
                                    //path.relativeLineTo(xPxToMove.toFloat(), yPxToMove.toFloat())
                                    onDrawBehind {
                                        drawPath(
                                            path = path,
                                            color = Color.Black,
                                            style = Stroke(
                                                width = 10f,
                                                pathEffect = PathEffect.dashPathEffect(
                                                    intervals = floatArrayOf(20f, 10f),
                                                    phase = 25f
                                                )
                                            )
                                        )
                                    }
                                }
                                .fillMaxSize()
                        )

                        Box(
                            modifier = Modifier
                                .offset {
                                    offset
                                }
                                .graphicsLayer {
                                    translationX -= 70f
                                    translationY -= 70f
                                }
                                .size(75.dp)
                                .clip(CircleShape)
                                .background(Color.Black)

                        )
                    }
                }
            }
        }
    }
}
