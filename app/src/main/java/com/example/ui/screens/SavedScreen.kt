package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SavedProductEntity
import com.example.ui.components.PickWiseBottomNavigation
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

@Composable
fun SavedScreen(
    viewModel: PickWiseViewModel
) {
    val savedItems by viewModel.savedProducts.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Products", "Searches", "Compare")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = PickWiseBackground,
        bottomBar = {
            PickWiseBottomNavigation(
                currentTab = "Saved",
                onTabSelected = { viewModel.onBottomTabSelected(it) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header (Screen 11: "Saved")
            item {
                Text(
                    text = "Saved",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                )
            }

            // Tabs: Products | Searches | Compare (Screen 11)
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

            // Saved Products list (Screen 11)
            if (savedItems.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No saved items yet",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = PickWiseTextMuted,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "Tap the heart icon on any product to save it here.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PickWiseTextSecondary
                            )
                        )
                    }
                }
            } else {
                items(savedItems) { item ->
                    SavedProductRow(
                        item = item,
                        onClick = {
                            val product = viewModel.repository.getProductById(item.id)
                            viewModel.selectProduct(product)
                            viewModel.navigateTo(ScreenDestination.ProductDetail)
                        },
                        onDelete = {
                            viewModel.removeSavedProduct(item.id)
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SavedProductRow(
    item: SavedProductEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("saved_item_${item.id}"),
        colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.name,
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${item.currency} %,.0f".format(item.price),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseBlue,
                        fontSize = 14.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Saved recently",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PickWiseTextMuted,
                        fontSize = 11.sp
                    )
                )
            }

            Box {
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = PickWiseTextMuted
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Remove from Saved") },
                        leadingIcon = {
                            Icon(Icons.Default.DeleteOutline, contentDescription = null, tint = Color.Red)
                        },
                        onClick = {
                            menuExpanded = false
                            onDelete()
                        }
                    )
                }
            }
        }
    }
}
