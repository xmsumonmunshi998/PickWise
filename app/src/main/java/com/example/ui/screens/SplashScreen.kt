package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseNavyDark

@Composable
fun SplashScreen() {
    var startAnim by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0.85f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = "alpha"
    )

    LaunchedEffect(Unit) {
        startAnim = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PickWiseNavyDark)
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .scale(scale)
                .alpha(alpha)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pickwise_logo),
                contentDescription = "PickWise Logo",
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "PickWise",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 32.sp,
                    letterSpacing = (-0.5).sp
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Smarter Choices. Better Purchases.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = PickWiseBlue,
                strokeWidth = 2.5.dp
            )
        }
    }
}
