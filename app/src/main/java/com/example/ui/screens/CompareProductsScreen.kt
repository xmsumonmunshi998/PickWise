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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
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
fun CompareProductsScreen(
    viewModel: PickWiseViewModel
) {
    val productA by viewModel.productA.collectAsState()
    val productB by viewModel.productB.collectAsState()

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
            // Header (Screen 9: Back arrow, "Compare Products")
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
                        text = "Compare Products",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // Side by side products summary (Screen 9)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Product A Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseBlue)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PickWiseGreenLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "AI Pick",
                                    color = PickWiseGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Image(
                                painter = painterResource(id = productA.imageDrawableRes),
                                contentDescription = productA.name,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = productA.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = PickWiseNavy,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${productA.currency} %,.0f".format(productA.price),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = PickWiseBlue
                            )
                        }
                    }

                    // Product B Card
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFF1F5F9))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Alternative",
                                    color = PickWiseTextSecondary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Image(
                                painter = painterResource(id = productB.imageDrawableRes),
                                contentDescription = productB.name,
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = productB.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = PickWiseNavy,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "${productB.currency} %,.0f".format(productB.price),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = PickWiseNavy
                            )
                        }
                    }
                }
            }

            // AI Verdict Card (Screen 9)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseTealLight),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF99F6E4))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PickWiseTeal,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "AI Decision Recommendation",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PickWiseNavy,
                                    fontSize = 13.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Pick ${productA.name} to save ৳3,000, enjoy +1h battery, and lighter carry weight. Pick ${productB.name} if you prioritize aluminum keyboard finish.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PickWiseTextSecondary,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            )
                        }
                    }
                }
            }

            // Comparison Metrics Table (Screen 9)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Key Comparison Points",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = PickWiseNavy
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        CompareRow(title = "AI Match Score", valA = "92 / 100 ✓", valB = "88 / 100", highlightA = true)
                        CompareRow(title = "Lowest Price", valA = "৳ 54,990 ✓", valB = "৳ 57,990", highlightA = true)
                        CompareRow(title = "Battery Life", valA = "Up to 8 hours ✓", valB = "Up to 7 hours", highlightA = true)
                        CompareRow(title = "Carry Weight", valA = "1.7 kg (Lighter) ✓", valB = "1.8 kg", highlightA = true)
                        CompareRow(title = "User Rating", valA = "4.4 ★ (2.8k)", valB = "4.3 ★ (1.2k)", highlightA = true)
                        CompareRow(title = "Speaker Audio", valA = "Stereo Sound", valB = "B&O Audio ✓", highlightA = false)
                        CompareRow(title = "Chassis Finish", valA = "Durable Plastic", valB = "Aluminum Palmrest ✓", highlightA = false)
                    }
                }
            }

            // CTA Select actions
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.selectProduct(productA)
                            viewModel.navigateTo(ScreenDestination.ProductDetail)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Choose ASUS", fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            viewModel.selectProduct(productB)
                            viewModel.navigateTo(ScreenDestination.ProductDetail)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Choose HP", fontSize = 13.sp, color = PickWiseNavy)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CompareRow(
    title: String,
    valA: String,
    valB: String,
    highlightA: Boolean
) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = title, fontSize = 11.sp, color = PickWiseTextMuted)
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = valA,
                fontSize = 12.sp,
                fontWeight = if (highlightA) FontWeight.Bold else FontWeight.Normal,
                color = if (highlightA) PickWiseGreen else PickWiseTextPrimary,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = valB,
                fontSize = 12.sp,
                fontWeight = if (!highlightA) FontWeight.Bold else FontWeight.Normal,
                color = if (!highlightA) PickWiseNavy else PickWiseTextSecondary,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider(color = Color(0xFFF1F5F9), modifier = Modifier.padding(top = 6.dp))
    }
}
