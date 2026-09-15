package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.ui.components.AIAssistantCard
import com.example.ui.components.PickWiseBottomNavigation
import com.example.ui.components.PickWiseTopBar
import com.example.ui.components.QuickActionCard
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PickWiseViewModel
) {
    val recentSearches by viewModel.recentSearches.collectAsState()
    val popularSearches = listOf(
        "Wireless Earbuds",
        "Laptop",
        "Running Shoes",
        "Smartwatch",
        "Backpack",
        "Gaming Phone"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = PickWiseBackground,
        topBar = {
            PickWiseTopBar(
                onMenuClick = { viewModel.navigateTo(ScreenDestination.AdminPanel) },
                onAvatarClick = { viewModel.navigateTo(ScreenDestination.Profile) }
            )
        },
        bottomBar = {
            PickWiseBottomNavigation(
                currentTab = "Home",
                onTabSelected = { viewModel.onBottomTabSelected(it) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Greeting Area (Screen 2: "Hello, Rafi 👋", "What are you looking for today?")
            item {
                Column(modifier = Modifier.padding(top = 4.dp)) {
                    Text(
                        text = "Hello, Rafi 👋",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 22.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "What are you looking for today?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = PickWiseTextSecondary,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            // Primary Search Field
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { viewModel.navigateTo(ScreenDestination.SearchInput) }
                        .border(1.dp, PickWiseCardBorder, RoundedCornerShape(14.dp))
                        .testTag("home_search_bar"),
                    color = PickWiseSurface,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = PickWiseBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Describe what you need...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = PickWiseTextMuted,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { viewModel.openVoiceDialog(true) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Voice Search",
                                tint = PickWiseBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            // 4 Home Shortcuts (Screen 2: Camera, Voice, Compare, Saved)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Camera",
                        subtitle = "Search by Image",
                        icon = Icons.Default.CameraAlt,
                        onClick = { viewModel.openImageDialog(true) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Voice",
                        subtitle = "Speech to Search",
                        icon = Icons.Default.Mic,
                        onClick = { viewModel.openVoiceDialog(true) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Compare",
                        subtitle = "Products",
                        icon = Icons.Default.CompareArrows,
                        onClick = { viewModel.navigateTo(ScreenDestination.CompareProducts) },
                        modifier = Modifier.weight(1f)
                    )
                    QuickActionCard(
                        title = "Saved",
                        subtitle = "Your List",
                        icon = Icons.Default.Favorite,
                        onClick = { viewModel.navigateTo(ScreenDestination.Saved) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // AI Shopping Assistant Banner Card
            item {
                AIAssistantCard(
                    onClick = { viewModel.openClarificationDialog(true) }
                )
            }

            // "Analyze Shared Link" Quick Feature Button (Section 30)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.openLinkAnalysisDialog(true) },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Analyze Link",
                                tint = PickWiseBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Analyze Product Link (Before You Buy)",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = PickWiseNavy,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Text(
                            text = "Check Deal",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PickWiseBlue,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Popular Searches Section
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Popular Searches",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 15.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(popularSearches) { chip ->
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = PickWiseSurface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { viewModel.startResearch(chip) }
                                    .testTag("popular_chip_$chip")
                            ) {
                                Text(
                                    text = chip,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PickWiseTextPrimary,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Recent Searches Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Searches",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 15.sp
                        )
                    )
                    TextButton(
                        onClick = { viewModel.navigateTo(ScreenDestination.SearchInput) }
                    ) {
                        Text(
                            text = "See all",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = PickWiseBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }

            // List of Recent Searches (Matches Screen 2: wireless earbuds under 5000, best laptop for students, running shoes)
            items(recentSearches.take(5)) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { viewModel.startResearch(item.query) }
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = PickWiseTextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = item.query,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = PickWiseTextPrimary,
                            fontSize = 13.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = item.timeAgoLabel,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PickWiseTextMuted,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
