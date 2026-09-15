package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.dialogs.ClarificationDialog
import com.example.ui.dialogs.ImageSearchDialog
import com.example.ui.dialogs.LinkAnalysisDialog
import com.example.ui.dialogs.PriceAlertDialog
import com.example.ui.dialogs.VoiceSearchDialog
import com.example.ui.screens.AIResultScreen
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.AlternativesScreen
import com.example.ui.screens.CompareProductsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PriceComparisonScreen
import com.example.ui.screens.PriceHistoryScreen
import com.example.ui.screens.ProductDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ResearchProgressScreen
import com.example.ui.screens.ReviewsAnalysisScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.SearchInputScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.PickWiseViewModel
import com.example.ui.viewmodel.ScreenDestination

class MainActivity : ComponentActivity() {
    private val viewModel: PickWiseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Handle shared product link intent (Feature 30: "Analyze Product Link - Before You Buy")
        handleSharedIntent(intent)

        setContent {
            MyApplicationTheme {
                PickWiseApp(viewModel = viewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleSharedIntent(intent)
    }

    private fun handleSharedIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (!sharedText.isNullOrBlank()) {
                viewModel.analyzeSharedUrl(sharedText)
            }
        }
    }
}

@Composable
fun PickWiseApp(viewModel: PickWiseViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val showVoiceDialog by viewModel.showVoiceDialog.collectAsState()
    val showImageDialog by viewModel.showImageDialog.collectAsState()
    val showClarificationDialog by viewModel.showClarificationDialog.collectAsState()
    val showLinkAnalysisDialog by viewModel.showLinkAnalysisDialog.collectAsState()
    val showPriceAlertDialog by viewModel.showPriceAlertDialog.collectAsState()

    // Handle System Back Button
    BackHandler(enabled = currentScreen !is ScreenDestination.Home && currentScreen !is ScreenDestination.Splash) {
        when (currentScreen) {
            is ScreenDestination.SearchInput,
            is ScreenDestination.Alternatives,
            is ScreenDestination.Saved,
            is ScreenDestination.Profile,
            is ScreenDestination.AdminPanel,
            is ScreenDestination.CompareProducts -> viewModel.navigateTo(ScreenDestination.Home)

            is ScreenDestination.ResearchProgress -> viewModel.navigateTo(ScreenDestination.SearchInput)
            is ScreenDestination.AIResult -> viewModel.navigateTo(ScreenDestination.Home)

            is ScreenDestination.ProductDetail,
            is ScreenDestination.ReviewsAnalysis,
            is ScreenDestination.PriceComparison,
            is ScreenDestination.PriceHistory -> viewModel.navigateTo(ScreenDestination.AIResult)

            else -> viewModel.navigateTo(ScreenDestination.Home)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(targetState = currentScreen, label = "screen_transition") { screen ->
            when (screen) {
                is ScreenDestination.Splash -> SplashScreen()
                is ScreenDestination.Home -> HomeScreen(viewModel = viewModel)
                is ScreenDestination.SearchInput -> SearchInputScreen(viewModel = viewModel)
                is ScreenDestination.ResearchProgress -> ResearchProgressScreen(viewModel = viewModel)
                is ScreenDestination.AIResult -> AIResultScreen(viewModel = viewModel)
                is ScreenDestination.ProductDetail -> ProductDetailScreen(viewModel = viewModel)
                is ScreenDestination.ReviewsAnalysis -> ReviewsAnalysisScreen(viewModel = viewModel)
                is ScreenDestination.PriceComparison -> PriceComparisonScreen(viewModel = viewModel)
                is ScreenDestination.PriceHistory -> PriceHistoryScreen(viewModel = viewModel)
                is ScreenDestination.CompareProducts -> CompareProductsScreen(viewModel = viewModel)
                is ScreenDestination.Alternatives -> AlternativesScreen(viewModel = viewModel)
                is ScreenDestination.Saved -> SavedScreen(viewModel = viewModel)
                is ScreenDestination.Profile -> ProfileScreen(viewModel = viewModel)
                is ScreenDestination.AdminPanel -> AdminPanelScreen(viewModel = viewModel)
            }
        }

        // Modals & Dialogs
        if (showVoiceDialog) {
            VoiceSearchDialog(viewModel = viewModel, onDismiss = { viewModel.openVoiceDialog(false) })
        }
        if (showImageDialog) {
            ImageSearchDialog(viewModel = viewModel, onDismiss = { viewModel.openImageDialog(false) })
        }
        if (showClarificationDialog) {
            ClarificationDialog(viewModel = viewModel, onDismiss = { viewModel.openClarificationDialog(false) })
        }
        if (showLinkAnalysisDialog) {
            LinkAnalysisDialog(viewModel = viewModel, onDismiss = { viewModel.openLinkAnalysisDialog(false) })
        }
        if (showPriceAlertDialog) {
            PriceAlertDialog(viewModel = viewModel, onDismiss = { viewModel.openPriceAlertDialog(false) })
        }
    }
}
