package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseGreen
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun ResearchProgressScreen(
    viewModel: PickWiseViewModel
) {
    val query by viewModel.searchQuery.collectAsState()
    val stepIndex by viewModel.researchStepIndex.collectAsState()
    val steps = viewModel.researchSteps

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = PickWiseBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(ScreenDestination.SearchInput) },
                    modifier = Modifier.testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PickWiseNavy
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "AI Shopping Research",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Query summary card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Researching for you",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PickWiseTextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "“$query”",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = PickWiseNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = { ((stepIndex.toFloat() + 1) / steps.size).coerceIn(0.1f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(CircleShape),
                        color = PickWiseBlue,
                        trackColor = Color(0xFFE2E8F0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Multistep Research Pipeline (Section 38: Understanding your request… Searching products… etc.)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    steps.forEachIndexed { index, stepName ->
                        val isDone = index < stepIndex
                        val isCurrent = index == stepIndex

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isDone -> PickWiseGreen
                                            isCurrent -> PickWiseBlue.copy(alpha = alpha)
                                            else -> Color(0xFFE2E8F0)
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Done",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else if (isCurrent) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(14.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.White
                                    )
                                } else {
                                    Text(
                                        text = "${index + 1}",
                                        fontSize = 11.sp,
                                        color = PickWiseTextMuted,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                text = stepName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isCurrent) FontWeight.Bold else if (isDone) FontWeight.Medium else FontWeight.Normal,
                                    color = if (isDone) PickWiseNavy else if (isCurrent) PickWiseBlue else PickWiseTextMuted,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Transparency badge
            Text(
                text = "Live multi-source synthesis with Gemini & OpenAI router • 6 verified merchant feeds",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = PickWiseTextMuted,
                    fontSize = 11.sp
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
