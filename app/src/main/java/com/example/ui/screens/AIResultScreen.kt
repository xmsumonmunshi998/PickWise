package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Product
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseBlueContainer
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseGreen
import com.example.ui.theme.PickWiseGreenLight
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTeal
import com.example.ui.theme.PickWiseTealLight
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun AIResultScreen(
    viewModel: PickWiseViewModel
) {
    val context = LocalContext.current
    val product by viewModel.selectedProduct.collectAsState()
    val savedProducts by viewModel.savedProducts.collectAsState()
    val isSaved = savedProducts.any { it.id == product.id }

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
            // Top Bar: Back arrow & Title "AI Research Result" (Screen 4)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                            text = "AI Research Result",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 18.sp
                            )
                        )
                    }

                    IconButton(
                        onClick = { viewModel.toggleSaveProduct(product) },
                        modifier = Modifier.testTag("save_product_button")
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Save",
                            tint = if (isSaved) PickWiseBlue else PickWiseTextMuted
                        )
                    }
                }
            }

            // Product Hero Card (Screen 4: ASUS VivoBook 15 + "Best Overall" badge)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.navigateTo(ScreenDestination.ProductDetail) }
                        .testTag("product_hero_card"),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            // Product Image
                            Image(
                                painter = painterResource(id = product.imageDrawableRes),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Fit
                            )

                            // Badge (Best Overall)
                            val badge = product.bestBadge
                            if (badge != null) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(PickWiseGreen)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = badge,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = product.subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = PickWiseTextSecondary,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }

            // Match Score Pill Card (Screen 4: 92/100 Excellent Match)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseTealLight),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCCFBF1))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Circular gauge
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(3.dp, PickWiseTeal, CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${product.aiMatchScore}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = PickWiseTeal
                                )
                                Text(
                                    text = "/100",
                                    fontSize = 9.sp,
                                    color = PickWiseTextMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = product.matchTag,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PickWiseNavy,
                                    fontSize = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = product.matchDescription,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PickWiseTextSecondary,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }
                }
            }

            // Price Row & Discount Badge (Screen 4: ৳ 54,990, 8% lower than average price)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${product.currency} %,.0f".format(product.price),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 24.sp
                        )
                    )

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PickWiseGreenLight)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = product.priceTrendNote,
                            color = PickWiseGreen,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Primary CTA Button (Screen 4: "View on Amazon", Clickable destination!)
            item {
                Button(
                    onClick = {
                        viewModel.openStoreDeal(context, product.primaryStore.url, product.primaryStore.storeName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("primary_cta_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "View on ${product.primaryStore.storeName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = "External Link",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // 4 Highlight Badges (Screen 4: Student Friendly, Good Battery, Lightweight, Value for Money)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HighlightPill(
                        icon = Icons.Default.School,
                        title = "Student\nFriendly",
                        modifier = Modifier.weight(1f)
                    )
                    HighlightPill(
                        icon = Icons.Default.BatteryChargingFull,
                        title = "Good Battery\n(up to 8h)",
                        modifier = Modifier.weight(1f)
                    )
                    HighlightPill(
                        icon = Icons.Default.Speed,
                        title = "Lightweight\n1.7 kg",
                        modifier = Modifier.weight(1f)
                    )
                    HighlightPill(
                        icon = Icons.Default.MonetizationOn,
                        title = "Value for\nMoney",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // "Why we recommend this?" Checklist (Screen 4)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Why we recommend this?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        product.whyRecommended.forEach { point ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Check",
                                    tint = PickWiseGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = point,
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

            // Quick Exploration Navigation (Detail, Comparison, Reviews, Price History)
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.navigateTo(ScreenDestination.ProductDetail) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Full Specs", fontSize = 12.sp, color = PickWiseNavy)
                        }
                        OutlinedButton(
                            onClick = { viewModel.navigateTo(ScreenDestination.PriceComparison) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("6 Store Deals", fontSize = 12.sp, color = PickWiseNavy)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.navigateTo(ScreenDestination.ReviewsAnalysis) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Review Trust (4.4★)", fontSize = 12.sp, color = PickWiseNavy)
                        }
                        OutlinedButton(
                            onClick = { viewModel.navigateTo(ScreenDestination.PriceHistory) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Price History & Advice", fontSize = 12.sp, color = PickWiseNavy)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun HighlightPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PickWiseBlue,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    color = PickWiseTextSecondary,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
