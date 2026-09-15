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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.StoreOfferCard
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun PriceComparisonScreen(
    viewModel: PickWiseViewModel
) {
    val context = LocalContext.current
    val product by viewModel.selectedProduct.collectAsState()
    val offers = product.allOffers

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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header: Back arrow & Title (Screen 7)
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
                        text = "Price Comparison",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 18.sp
                        )
                    )
                }
            }

            // Summary Subtitle & Filter Row (Screen 7)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "We found ${offers.size} trusted stores",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PickWiseTextSecondary,
                                fontSize = 12.sp
                            )
                        )
                    }

                    // Sort dropdown pill (Screen 7: Sort by: Lowest Price v)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Lowest Price",
                                fontSize = 12.sp,
                                color = PickWiseNavy,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = PickWiseTextSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Store Offer Cards (Screen 7: Amazon BD, Daraz, Gadget & Gear, Star Tech, Pickaboo, Ryans)
            items(offers) { offer ->
                StoreOfferCard(
                    offer = offer,
                    onViewDealClick = {
                        viewModel.openStoreDeal(context, offer.url, offer.storeName)
                    }
                )
            }

            // Disclaimer Note at bottom (Screen 7)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = PickWiseTextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Prices may vary based on stock and availability. Check the store for the latest price.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PickWiseTextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
