package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.AIProviderConfig
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseBlueContainer
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseGreen
import com.example.ui.theme.PickWiseGreenLight
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun AdminPanelScreen(
    viewModel: PickWiseViewModel
) {
    val context = LocalContext.current
    val providers = viewModel.repository.aiProviders
    val routings = viewModel.repository.featureRoutings
    val cost = viewModel.repository.costSettings

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = PickWiseBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateTo(ScreenDestination.Home) },
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PickWiseNavy
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Admin & AI Architecture",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // Client Security Guarantee Notice (Prompt Rule: Never expose API keys inside Android client)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = PickWiseBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Zero Client Key Exposure Architecture",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = PickWiseNavy
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "All AI calls and scraping pipelines are brokered via secure server-side proxy. Raw provider API keys are strictly forbidden from client bundle storage.",
                                fontSize = 11.sp,
                                color = PickWiseTextSecondary,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }

            // Section 1: Multi-AI Provider Architecture
            item {
                Text(
                    text = "AI Providers & Active Models",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 15.sp
                    )
                )
            }

            items(providers) { provider ->
                AIProviderCard(
                    provider = provider,
                    onTestConnection = {
                        viewModel.testAIProviderConnection(context, provider)
                    }
                )
            }

            // Section 2: Feature Routing Matrix
            item {
                Text(
                    text = "Feature-Specific AI Routing",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 15.sp
                    )
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        routings.forEachIndexed { index, route ->
                            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                Text(
                                    text = route.featureName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PickWiseNavy
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Primary: ${route.primaryProvider} (${route.primaryModel})",
                                        fontSize = 11.sp,
                                        color = PickWiseBlue
                                    )
                                    Text(
                                        text = "Fallback: ${route.fallbackProvider}",
                                        fontSize = 11.sp,
                                        color = PickWiseTextMuted
                                    )
                                }
                            }
                            if (index < routings.size - 1) {
                                HorizontalDivider(color = Color(0xFFF1F5F9))
                            }
                        }
                    }
                }
            }

            // Section 3: Cost Control & Safety Limits
            item {
                Text(
                    text = "Cost Control & Rate Limiting",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 15.sp
                    )
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Daily Budget Spent", fontSize = 13.sp, color = PickWiseTextSecondary)
                            Text(
                                text = "$${cost.currentDailySpentUsd} / $${cost.dailyBudgetUsd}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = PickWiseNavy
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { (cost.currentDailySpentUsd / cost.dailyBudgetUsd).toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = PickWiseBlue,
                            trackColor = Color(0xFFE2E8F0)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Per-User Daily Quota", fontSize = 12.sp, color = PickWiseTextSecondary)
                            Text(text = "${cost.perUserDailyLimit} queries/day", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PickWiseNavy)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Max Research Sources", fontSize = 12.sp, color = PickWiseTextSecondary)
                            Text(text = "${cost.maxResearchDepthSources} sources", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PickWiseNavy)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun AIProviderCard(
    provider: AIProviderConfig,
    onTestConnection: () -> Unit
) {
    var enabled by remember { mutableStateOf(provider.isEnabled) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (enabled) PickWiseGreen else PickWiseTextMuted)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = provider.name,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 15.sp
                        )
                    )
                }
                Switch(
                    checked = enabled,
                    onCheckedChange = { enabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PickWiseBlue
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Model: ${provider.currentModel}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = PickWiseTextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Avg Latency: ${provider.latencyMs}ms • Key: ${provider.apiKeyMasked}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = PickWiseTextMuted,
                    fontSize = 11.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onTestConnection,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Test Connection",
                    fontSize = 12.sp,
                    color = PickWiseBlue
                )
            }
        }
    }
}
