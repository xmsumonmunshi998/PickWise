package com.example.ui.screens

import android.content.Intent
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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseGreen
import com.example.ui.theme.PickWiseGreenLight
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseOrange
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun ProductDetailScreen(
    viewModel: PickWiseViewModel
) {
    val context = LocalContext.current
    val product by viewModel.selectedProduct.collectAsState()
    val savedProducts by viewModel.savedProducts.collectAsState()
    val isSaved = savedProducts.any { it.id == product.id }
    var selectedImageIndex by remember { mutableStateOf(0) }

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
            // Top Bar: Back, Favorite, Share (Screen 5)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
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

                    Row {
                        IconButton(
                            onClick = { viewModel.toggleSaveProduct(product) },
                            modifier = Modifier.testTag("detail_favorite_btn")
                        ) {
                            Icon(
                                imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isSaved) PickWiseBlue else PickWiseTextMuted
                            )
                        }
                        IconButton(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Check out ${product.name} on PickWise: ${product.primaryStore.url}")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Product"))
                            },
                            modifier = Modifier.testTag("detail_share_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                tint = PickWiseNavy
                            )
                        }
                    }
                }
            }

            // Image Gallery Hero with index counter badge "1/5" (Screen 5)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = product.imageDrawableRes),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Fit
                            )

                            // 1/5 counter badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "${selectedImageIndex + 1}/5",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Thumbnails preview strip
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            (0..3).forEach { index ->
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = if (selectedImageIndex == index) 2.dp else 1.dp,
                                            color = if (selectedImageIndex == index) PickWiseBlue else PickWiseCardBorder,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { selectedImageIndex = index }
                                        .padding(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = product.imageDrawableRes),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Product Details Title & Rating (Screen 5)
            item {
                Column {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = PickWiseOrange,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${product.rating}",
                            fontWeight = FontWeight.Bold,
                            color = PickWiseNavy,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "(${product.reviewCount} reviews) • ${product.subtitle}",
                            color = PickWiseTextSecondary,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Price & Discount badge (Screen 5: ৳ 54,990, ৳ 59,990, 8% OFF)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${product.currency} %,.0f".format(product.price),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 24.sp
                            )
                        )
                        if (product.originalPrice != null) {
                            Text(
                                text = "${product.currency} %,.0f".format(product.originalPrice),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PickWiseTextMuted,
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 15.sp
                                )
                            )
                        }
                        if (product.discountPercent > 0) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PickWiseGreenLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${product.discountPercent}% OFF",
                                    color = PickWiseGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            // Big CTA: "View on Amazon" (Screen 5)
            item {
                Button(
                    onClick = {
                        viewModel.openStoreDeal(context, product.primaryStore.url, product.primaryStore.storeName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("detail_primary_cta"),
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
                        contentDescription = "Open Link",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Key Specifications Table (Screen 5)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Key Specifications",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        product.specifications.forEachIndexed { index, pair ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = pair.first,
                                    color = PickWiseTextSecondary,
                                    fontSize = 13.sp,
                                    modifier = Modifier.weight(0.45f)
                                )
                                Text(
                                    text = pair.second,
                                    color = PickWiseNavy,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    modifier = Modifier.weight(0.55f)
                                )
                            }
                            if (index < product.specifications.size - 1) {
                                HorizontalDivider(color = Color(0xFFF1F5F9))
                            }
                        }
                    }
                }
            }

            // Quick Jump Sections (Reviews, 6 Store Prices, Price History)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailSectionLink(
                        title = "Customer Reviews & Trust",
                        subtitle = "4.4 ★ (2,843 reviews) • No fake review signs",
                        onClick = { viewModel.navigateTo(ScreenDestination.ReviewsAnalysis) }
                    )
                    DetailSectionLink(
                        title = "Price Comparison",
                        subtitle = "6 verified stores • Lowest at ৳ 54,990",
                        onClick = { viewModel.navigateTo(ScreenDestination.PriceComparison) }
                    )
                    DetailSectionLink(
                        title = "Price History & When to Buy",
                        subtitle = "Currently 8% lower than 30-day average",
                        onClick = { viewModel.navigateTo(ScreenDestination.PriceHistory) }
                    )
                    DetailSectionLink(
                        title = "Compare with HP Pavilion 15",
                        subtitle = "Head-to-head student recommendation comparison",
                        onClick = { viewModel.navigateTo(ScreenDestination.CompareProducts) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun DetailSectionLink(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 13.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PickWiseTextSecondary,
                        fontSize = 11.sp
                    )
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = PickWiseTextMuted,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
