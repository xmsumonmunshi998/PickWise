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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PickWiseBackground
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseBlueContainer
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

@Composable
fun SearchInputScreen(
    viewModel: PickWiseViewModel
) {
    val initialQuery by viewModel.searchQuery.collectAsState()
    var textInput by remember { mutableStateOf(initialQuery) }

    val exampleQueries = listOf(
        "10 হাজার টাকার মধ্যে ভালো smartphone",
        "Gaming laptop under 70000",
        "Best budget earbuds",
        "Running shoes for men"
    )

    val suggestionChips = listOf("Suggest", "Laptop", "Best", "Budget", "Camera")

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        containerColor = PickWiseBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Search Bar Header (Screen 3)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
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

                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_text_input"),
                    placeholder = {
                        Text(
                            text = "What are you looking for?",
                            color = PickWiseTextMuted,
                            fontSize = 14.sp
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = PickWiseSurface,
                        unfocusedContainerColor = PickWiseSurface,
                        focusedBorderColor = PickWiseBlue,
                        unfocusedBorderColor = PickWiseCardBorder
                    ),
                    singleLine = true,
                    trailingIcon = {
                        if (textInput.isNotEmpty()) {
                            IconButton(onClick = { textInput = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = PickWiseTextMuted
                                )
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (textInput.isNotBlank()) {
                            viewModel.startResearch(textInput)
                        }
                    })
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (textInput.isNotBlank()) {
                            viewModel.startResearch(textInput)
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(PickWiseBlue)
                        .testTag("execute_search_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Submit Search",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // "Or try asking like this" (Screen 3)
            Text(
                text = "Or try asking like this",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PickWiseNavy,
                    fontSize = 15.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Query Suggestions list
            exampleQueries.forEach { query ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            textInput = query
                            viewModel.startResearch(query)
                        }
                        .border(1.dp, PickWiseCardBorder, RoundedCornerShape(10.dp)),
                    color = PickWiseSurface
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PickWiseBlue)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = query,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = PickWiseTextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Big Voice Search Card (Screen 3: "Search by voice", "Tap to speak")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel.openVoiceDialog(true) }
                    .testTag("voice_search_card"),
                colors = CardDefaults.cardColors(containerColor = PickWiseSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(PickWiseBlueContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Mic",
                            tint = PickWiseBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Search by voice",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PickWiseNavy,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Tap to speak (English & বাংলা supported)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PickWiseTextSecondary,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Suggestion chips row (Screen 3)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(suggestionChips) { chip ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PickWiseSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder),
                        modifier = Modifier.clickable {
                            textInput = "$textInput $chip".trim()
                        }
                    ) {
                        Text(
                            text = chip,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PickWiseTextSecondary,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
