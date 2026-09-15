package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.PriceHistoryPoint
import com.example.data.model.StoreOffer
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseBlueContainer
import com.example.ui.theme.PickWiseBlueDark
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseGreen
import com.example.ui.theme.PickWiseGreenLight
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseOrange
import com.example.ui.theme.PickWiseRed
import com.example.ui.theme.PickWiseRedLight
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTeal
import com.example.ui.theme.PickWiseTealLight
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary

/**
 * Top App Bar matching Reference Screen 2:
 * Hamburger menu, PickWise brand wordmark, Rafi avatar with green online status dot.
 */
@Composable
fun PickWiseTopBar(
    onMenuClick: () -> Unit = {},
    onAvatarClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier.testTag("menu_button")
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = PickWiseTextPrimary
            )
        }

        // PickWise Brand Wordmark
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.testTag("brand_logo")
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pickwise_logo),
                contentDescription = "PickWise Logo",
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PickWise",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = PickWiseNavy,
                    letterSpacing = (-0.5).sp
                )
            )
        }

        // User Avatar with Green Ring / Status Dot
        Box(
            modifier = Modifier
                .size(36.dp)
                .clickable(onClick = onAvatarClick)
                .testTag("user_avatar")
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFFE2E8F0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "R",
                    fontWeight = FontWeight.Bold,
                    color = PickWiseNavy,
                    fontSize = 15.sp
                )
            }
            // Green status indicator
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(PickWiseGreen)
                    .border(1.5.dp, Color.White, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

/**
 * Bottom Navigation Bar matching Reference Screens:
 * Home, Discover, Saved, Profile
 */
@Composable
fun PickWiseBottomNavigation(
    currentTab: String,
    onTabSelected: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = PickWiseSurface,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                label = "Home",
                selected = currentTab == "Home",
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Outlined.Home,
                onClick = { onTabSelected("Home") }
            )
            BottomNavItem(
                label = "Discover",
                selected = currentTab == "Discover",
                selectedIcon = Icons.Default.Explore,
                unselectedIcon = Icons.Outlined.Explore,
                onClick = { onTabSelected("Discover") }
            )
            BottomNavItem(
                label = "Saved",
                selected = currentTab == "Saved",
                selectedIcon = Icons.Default.Bookmark,
                unselectedIcon = Icons.Default.BookmarkBorder,
                onClick = { onTabSelected("Saved") }
            )
            BottomNavItem(
                label = "Profile",
                selected = currentTab == "Profile",
                selectedIcon = Icons.Default.Person,
                unselectedIcon = Icons.Outlined.Person,
                onClick = { onTabSelected("Profile") }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    selected: Boolean,
    selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .testTag("tab_$label")
    ) {
        Icon(
            imageVector = if (selected) selectedIcon else unselectedIcon,
            contentDescription = label,
            tint = if (selected) PickWiseBlue else PickWiseTextMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 11.sp,
                color = if (selected) PickWiseBlue else PickWiseTextMuted
            )
        )
    }
}

/**
 * 4 Quick Action Cards (Screen 2): Camera, Voice, Compare, Saved
 */
@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("action_$title"),
        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(PickWiseCardBorder, PickWiseCardBorder)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(PickWiseBlueContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = PickWiseBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = PickWiseTextPrimary,
                    fontSize = 12.sp
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = PickWiseTextMuted,
                    fontSize = 10.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/**
 * AI Shopping Assistant Banner Card (Screen 2)
 */
@Composable
fun AIAssistantCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("ai_assistant_card"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(Color(0xFFDCFCE7), Color(0xFFBBF7D0))))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCFCE7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "AI Assistant",
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AI Shopping Assistant",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Tell us your needs, and we'll find the best options for you.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PickWiseTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Open",
                tint = PickWiseTextMuted,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

/**
 * Store Offer Row (Price Comparison Screen 7)
 */
@Composable
fun StoreOfferCard(
    offer: StoreOffer,
    onViewDealClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .testTag("store_offer_${offer.storeName}"),
        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(PickWiseCardBorder, PickWiseCardBorder))),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Store logo badge
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF1F5F9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = offer.storeName.take(1).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = offer.storeName,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseTextPrimary,
                                fontSize = 14.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${offer.currency} %,.0f".format(offer.price),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        if (offer.badge != null) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(PickWiseGreenLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = offer.badge,
                                    color = PickWiseGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                    Text(
                        text = "${offer.stockStatus} • ${offer.deliveryEstimate}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PickWiseTextMuted,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            // View Deal Button
            Button(
                onClick = onViewDealClick,
                colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier.testTag("view_deal_btn_${offer.storeName}")
            ) {
                Text(
                    text = "View Deal",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * Interactive Price Trend Canvas Line Chart (Price History Screen 8)
 */
@Composable
fun PriceTrendLineChart(
    history: List<PriceHistoryPoint>,
    modifier: Modifier = Modifier
) {
    if (history.isEmpty()) return

    val minPrice = (history.minOfOrNull { it.price } ?: 50000.0) * 0.95
    val maxPrice = (history.maxOfOrNull { it.price } ?: 65000.0) * 1.05

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = 8.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val stepX = width / (history.size - 1).coerceAtLeast(1)

                val points = history.mapIndexed { index, point ->
                    val x = index * stepX
                    val normalizedY = (point.price - minPrice) / (maxPrice - minPrice)
                    val y = height - (normalizedY * height).toFloat()
                    Offset(x, y)
                }

                // Draw gradient fill area under the line
                val fillPath = Path().apply {
                    moveTo(0f, height)
                    points.forEach { lineTo(it.x, it.y) }
                    lineTo(width, height)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(PickWiseBlue.copy(alpha = 0.2f), Color.Transparent),
                        startY = 0f,
                        endY = height
                    )
                )

                // Draw line
                val strokePath = Path().apply {
                    points.forEachIndexed { i, p ->
                        if (i == 0) moveTo(p.x, p.y) else lineTo(p.x, p.y)
                    }
                }
                drawPath(
                    path = strokePath,
                    color = PickWiseBlue,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )

                // Draw points
                points.forEachIndexed { idx, p ->
                    val isCurrent = history[idx].isCurrent
                    drawCircle(
                        color = Color.White,
                        radius = if (isCurrent) 6.dp.toPx() else 4.dp.toPx(),
                        center = p
                    )
                    drawCircle(
                        color = if (isCurrent) PickWiseNavy else PickWiseBlue,
                        radius = if (isCurrent) 4.dp.toPx() else 3.dp.toPx(),
                        center = p
                    )
                }
            }
        }

        // Timeline labels (Apr, May, Jun, Jul)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            history.forEach { point ->
                Text(
                    text = point.label,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (point.isCurrent) PickWiseNavy else PickWiseTextMuted,
                        fontWeight = if (point.isCurrent) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}
