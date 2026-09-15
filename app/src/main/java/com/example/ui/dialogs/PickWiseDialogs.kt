package com.example.ui.dialogs

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.PickWiseBlue
import com.example.ui.theme.PickWiseBlueContainer
import com.example.ui.theme.PickWiseCardBorder
import com.example.ui.theme.PickWiseGreen
import com.example.ui.theme.PickWiseGreenLight
import com.example.ui.theme.PickWiseNavy
import com.example.ui.theme.PickWiseSurface
import com.example.ui.theme.PickWiseTextMuted
import com.example.ui.theme.PickWiseTextPrimary
import com.example.ui.theme.PickWiseTextSecondary
import com.example.ui.viewmodel.PickWiseViewModel

/**
 * Voice Search Dialog with audio pulse animation
 */
@Composable
fun VoiceSearchDialog(
    viewModel: PickWiseViewModel,
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = PickWiseSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PickWiseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size((70 * pulseScale).dp)
                            .clip(CircleShape)
                            .background(PickWiseBlue.copy(alpha = 0.15f))
                    )
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(PickWiseBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Mic",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Listening…",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Try: “Best laptop for students under 60000” or “১০ হাজার টাকায় ভালো ফোন”",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = PickWiseTextSecondary,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onDismiss()
                        viewModel.startResearch("best laptop for students")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Search Sample Voice Query", color = Color.White)
                }
            }
        }
    }
}

/**
 * Image Search Dialog
 */
@Composable
fun ImageSearchDialog(
    viewModel: PickWiseViewModel,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = PickWiseSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Visual Shopping Search",
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 16.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PickWiseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = PickWiseBlue,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Take a photo of any device, gadget, or shoes to identify and price-compare.",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            color = PickWiseTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                            viewModel.startResearch("ASUS VivoBook 15")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Capture Photo", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = {
                            onDismiss()
                            viewModel.startResearch("Sony WH-1000XM4")
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("From Gallery", fontSize = 12.sp, color = PickWiseNavy)
                    }
                }
            }
        }
    }
}

/**
 * AI Clarification Assistant Dialog (Section 15)
 */
@Composable
fun ClarificationDialog(
    viewModel: PickWiseViewModel,
    onDismiss: () -> Unit
) {
    var selectedBudget by remember { mutableStateOf("৳ 50,000 - ৳ 65,000") }
    var selectedUse by remember { mutableStateOf("University & Coding") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = PickWiseSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "AI Shopping Assistant",
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 17.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PickWiseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Help us narrow down the perfect match for you with 2 quick questions:",
                    fontSize = 12.sp,
                    color = PickWiseTextSecondary
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "1. What is your approximate budget?",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = PickWiseNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                listOf("Under ৳ 50,000", "৳ 50,000 - ৳ 65,000", "৳ 70,000+").forEach { opt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedBudget = opt }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (selectedBudget == opt) PickWiseBlue else Color(0xFFCBD5E1))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = opt, fontSize = 12.sp, color = PickWiseNavy)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "2. Primary intended use?",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = PickWiseNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                listOf("University & Coding", "Heavy Gaming", "Office & Zoom").forEach { opt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedUse = opt }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (selectedUse == opt) PickWiseBlue else Color(0xFFCBD5E1))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = opt, fontSize = 12.sp, color = PickWiseNavy)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        onDismiss()
                        viewModel.startResearch("laptop for $selectedUse ($selectedBudget)")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Find My Best Laptop Match", color = Color.White)
                }
            }
        }
    }
}

/**
 * Link Analysis Dialog (Section 30: "Analyze Product Link - Before You Buy")
 */
@Composable
fun LinkAnalysisDialog(
    viewModel: PickWiseViewModel,
    onDismiss: () -> Unit
) {
    var urlInput by remember { mutableStateOf("https://www.amazon.com/dp/B0BY2V5J3B") }
    var result by remember { mutableStateOf(viewModel.linkAnalysisResult.value) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = PickWiseSurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Analyze Link Before You Buy",
                        fontWeight = FontWeight.Bold,
                        color = PickWiseNavy,
                        fontSize = 16.sp
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PickWiseTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = urlInput,
                    onValueChange = { urlInput = it },
                    label = { Text("Paste product link") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PickWiseBlue,
                        unfocusedBorderColor = PickWiseCardBorder
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        result = viewModel.repository.analyzeProductLink(urlInput)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Run AI Deep Check")
                }

                if (result != null) {
                    val res = result!!
                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PickWiseCardBorder),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = PickWiseGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (res.betterDealFound) "Cheaper Deal Available!" else "Price Verified Good",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = if (res.betterDealFound) Color(0xFFB45309) else PickWiseGreen
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = res.priceAssessment,
                                fontSize = 11.sp,
                                color = PickWiseTextSecondary
                            )
                            if (res.betterPrice != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Best alternative: ৳ %,.0f at ${res.betterStore}".format(res.betterPrice),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = PickWiseBlue
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = res.recommendationAdvice,
                                fontSize = 11.sp,
                                color = PickWiseTextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Price Alert Dialog
 */
@Composable
fun PriceAlertDialog(
    viewModel: PickWiseViewModel,
    onDismiss: () -> Unit
) {
    val product = viewModel.selectedProduct.value
    var targetInput by remember { mutableStateOf("52000") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Set Price Drop Alert",
                fontWeight = FontWeight.Bold,
                color = PickWiseNavy,
                fontSize = 16.sp
            )
        },
        text = {
            Column {
                Text(
                    text = "We will notify you immediately when ${product.name} falls below your target price.",
                    fontSize = 12.sp,
                    color = PickWiseTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = targetInput,
                    onValueChange = { targetInput = it },
                    label = { Text("Target Price (৳)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PickWiseBlue,
                        unfocusedBorderColor = PickWiseCardBorder
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PickWiseBlue)
            ) {
                Text("Activate Alert")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel", color = PickWiseTextSecondary)
            }
        }
    )
}
