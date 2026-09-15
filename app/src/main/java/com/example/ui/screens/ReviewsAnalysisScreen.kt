package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.ui.theme.PickWiseGreenLight
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseOrange
import com.example.ui.theme.PickWiseRed
import com.example.ui.theme.PickWiseRedLight
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun ReviewsAnalysisScreen(
    viewModel: PickWiseViewModel
) {
    val product by viewModel.selectedProduct.collectAsState()
    val reviewSummary = product.reviewSummary
    val reviewTrust = product.reviewTrust
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Summary", "Reviews", "Analysis")

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
            // Header (Screen 6: Back, Title "Reviews & Analysis")
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateTo(ScreenDestination.AIResult) },
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
                        text = "Reviews & Analysis",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // Tabs Row: Summary | Reviews | Analysis (Screen 6)
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = PickWiseSurface,
                    contentColor = PickWiseBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = PickWiseBlue
                        )
                    }
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 13.sp,
                                    color = if (selectedTab == index) PickWiseBlue else PickWiseTextSecondary
                                )
                            }
                        )
                    }
                }
            }

            // Overall Rating Card (Screen 6: 4.4 ★ Based on 2,843 reviews)
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
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${reviewSummary.overallRating}",
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PickWiseNavy,
                                            fontSize = 28.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = PickWiseOrange,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Text(
                                    text = "Based on %,d reviews".format(reviewSummary.totalReviews),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PickWiseTextSecondary,
                                        fontSize = 12.sp
                                    )
                                )
                            }

                            // Review Trust Badge (Screen 6: Review Trust: High, No signs of fake reviews detected)
                            Card(
                                colors = CardDefaults.cardColors(containerColor = PickWiseGreenLight),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Shield,
                                            contentDescription = null,
                                            tint = PickWiseGreen,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Review Trust: ${reviewTrust.status}",
                                            color = PickWiseGreen,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Text(
                                        text = reviewTrust.description,
                                        color = PickWiseGreen,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Star Distribution breakdown bars (Screen 6)
                        reviewSummary.starDistribution.forEach { (stars, pct) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$stars★",
                                    fontSize = 11.sp,
                                    color = PickWiseTextSecondary,
                                    modifier = Modifier.width(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                LinearProgressIndicator(
                                    progress = { pct / 100f },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(6.dp)
                                        .clip(CircleShape),
                                    color = if (stars >= 4) PickWiseGreen else if (stars == 3) PickWiseOrange else PickWiseRed,
                                    trackColor = Color(0xFFF1F5F9)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$pct%",
                                    fontSize = 11.sp,
                                    color = PickWiseTextMuted,
                                    modifier = Modifier.width(32.dp)
                                )
                            }
                        }
                    }
                }
            }

            // "What Users Liked" (Screen 6: Green check icons)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "What Users Liked",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        reviewSummary.whatUsersLiked.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = PickWiseGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = PickWiseTextPrimary,
                                        fontSize = 13.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // "Common Complaints" (Screen 6: Red warning icons)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Common Complaints",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        reviewSummary.commonComplaints.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = PickWiseRed,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = PickWiseTextPrimary,
                                        fontSize = 13.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Customer Reviews list
            if (reviewSummary.sampleReviews.isNotEmpty()) {
                item {
                    Text(
                        text = "Verified Customer Reviews",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 15.sp
                        )
                    )
                }

                items(reviewSummary.sampleReviews) { review ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = review.author,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = PickWiseNavy
                                )
                                Text(
                                    text = review.date,
                                    fontSize = 11.sp,
                                    color = PickWiseTextMuted
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                (1..review.rating).forEach {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = PickWiseOrange,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Verified Purchase",
                                    fontSize = 10.sp,
                                    color = PickWiseGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = review.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = PickWiseTextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = review.comment,
                                fontSize = 12.sp,
                                color = PickWiseTextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
