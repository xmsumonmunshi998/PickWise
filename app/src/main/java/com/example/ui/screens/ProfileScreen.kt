package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PickWiseBottomNavigation
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
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
fun ProfileScreen(
    viewModel: PickWiseViewModel
) {
    val profile = viewModel.repository.userProfile

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = PickWiseBackground,
        bottomBar = {
            PickWiseBottomNavigation(
                currentTab = "Profile",
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
            // Screen 12: Profile Header Title
            item {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }

            // User Info Card (Screen 12: Rafi Islam, rafi****@gmail.com, Premium member)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "R",
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 22.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = profile.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PickWiseNavy,
                                    fontSize = 17.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = profile.email,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = PickWiseTextSecondary,
                                    fontSize = 12.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PickWiseGreenLight)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "Premium Member",
                                    color = PickWiseGreen,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }

            // Profile Options Menu (Screen 12)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
                ) {
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        ProfileMenuItem(
                            icon = Icons.Default.Tune,
                            title = "My Shopping Profile",
                            subtitle = "Preferences, budget & priority traits",
                            onClick = {}
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        ProfileMenuItem(
                            icon = Icons.Default.Notifications,
                            title = "Price Drop Notifications",
                            subtitle = "Manage active price alert triggers",
                            onClick = { viewModel.openPriceAlertDialog(true) }
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        ProfileMenuItem(
                            icon = Icons.Default.Language,
                            title = "Language & Currency",
                            subtitle = "English & বাংলা • ৳ BDT",
                            onClick = {}
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // ADMIN & AI ENGINE PANEL (Sections 9-14, 40, 54, 55)
                        ProfileMenuItem(
                            icon = Icons.Default.AdminPanelSettings,
                            title = "Admin & AI Architecture",
                            subtitle = "Gemini, OpenAI, xAI, Routing & Cost Control",
                            onClick = { viewModel.navigateTo(ScreenDestination.AdminPanel) },
                            tint = PickWiseBlue
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        ProfileMenuItem(
                            icon = Icons.Default.HelpOutline,
                            title = "Help & Support",
                            subtitle = "FAQs and human support team",
                            onClick = {}
                        )
                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        ProfileMenuItem(
                            icon = Icons.Default.Info,
                            title = "About PickWise",
                            subtitle = "v1.0.0 • AI Shopping Research Assistant",
                            onClick = {}
                        )
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
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    tint: Color = PickWiseNavy
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(tint.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = PickWiseNavy,
                    fontSize = 14.sp
                )
            )
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
