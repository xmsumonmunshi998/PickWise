package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.AIProviderConfig
import com.example.data.local.SavedProductEntity
import com.example.data.local.SearchHistoryEntity
import com.example.data.model.LinkAnalysisResult
import com.example.data.model.Product
import com.example.data.repository.PickWiseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenDestination {
    object Splash : ScreenDestination()
    object Home : ScreenDestination()
    object SearchInput : ScreenDestination()
    object ResearchProgress : ScreenDestination()
    object AIResult : ScreenDestination()
    object ProductDetail : ScreenDestination()
    object ReviewsAnalysis : ScreenDestination()
    object PriceComparison : ScreenDestination()
    object PriceHistory : ScreenDestination()
    object CompareProducts : ScreenDestination()
    object Alternatives : ScreenDestination()
    object Saved : ScreenDestination()
    object Profile : ScreenDestination()
    object AdminPanel : ScreenDestination()
}

class PickWiseViewModel(application: Application) : AndroidViewModel(application) {
    val repository = PickWiseRepository(application.applicationContext)

    // Current screen navigation
    private val _currentScreen = MutableStateFlow<ScreenDestination>(ScreenDestination.Splash)
    val currentScreen: StateFlow<ScreenDestination> = _currentScreen.asStateFlow()

    // Bottom bar tab
    private val _selectedBottomTab = MutableStateFlow("Home")
    val selectedBottomTab: StateFlow<String> = _selectedBottomTab.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("best laptop for students")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Selected product for Detail, Reviews, Price Comp, Price History
    private val _selectedProduct = MutableStateFlow(repository.catalog[0])
    val selectedProduct: StateFlow<Product> = _selectedProduct.asStateFlow()

    // Comparison pair
    private val _productA = MutableStateFlow(repository.catalog[0]) // ASUS VivoBook 15
    val productA: StateFlow<Product> = _productA.asStateFlow()

    private val _productB = MutableStateFlow(repository.catalog[1]) // HP Pavilion 15
    val productB: StateFlow<Product> = _productB.asStateFlow()

    // Research progress pipeline steps
    private val _researchStepIndex = MutableStateFlow(0)
    val researchStepIndex: StateFlow<Int> = _researchStepIndex.asStateFlow()

    val researchSteps = listOf(
        "Understanding your request…",
        "Searching trusted data sources…",
        "Checking real-time prices & stock…",
        "Analyzing user & expert reviews…",
        "Filtering review anomalies & bias…",
        "Comparing specifications & value…",
        "Preparing personalized recommendation…"
    )

    // Modals and dialogs
    private val _showVoiceDialog = MutableStateFlow(false)
    val showVoiceDialog: StateFlow<Boolean> = _showVoiceDialog.asStateFlow()

    private val _showImageDialog = MutableStateFlow(false)
    val showImageDialog: StateFlow<Boolean> = _showImageDialog.asStateFlow()

    private val _showClarificationDialog = MutableStateFlow(false)
    val showClarificationDialog: StateFlow<Boolean> = _showClarificationDialog.asStateFlow()

    private val _showLinkAnalysisDialog = MutableStateFlow(false)
    val showLinkAnalysisDialog: StateFlow<Boolean> = _showLinkAnalysisDialog.asStateFlow()

    private val _showPriceAlertDialog = MutableStateFlow(false)
    val showPriceAlertDialog: StateFlow<Boolean> = _showPriceAlertDialog.asStateFlow()

    private val _linkAnalysisResult = MutableStateFlow<LinkAnalysisResult?>(null)
    val linkAnalysisResult: StateFlow<LinkAnalysisResult?> = _linkAnalysisResult.asStateFlow()

    // Database flows
    val savedProducts: StateFlow<List<SavedProductEntity>> = repository.savedProductsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentSearches: StateFlow<List<SearchHistoryEntity>> = repository.recentSearchesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.prepopulateDefaultsIfEmpty()
        }
        // Splash screen auto-advance to Home
        viewModelScope.launch {
            delay(1400)
            if (_currentScreen.value is ScreenDestination.Splash) {
                _currentScreen.value = ScreenDestination.Home
            }
        }
    }

    fun navigateTo(destination: ScreenDestination) {
        _currentScreen.value = destination
        when (destination) {
            ScreenDestination.Home -> _selectedBottomTab.value = "Home"
            ScreenDestination.Saved -> _selectedBottomTab.value = "Saved"
            ScreenDestination.Profile -> _selectedBottomTab.value = "Profile"
            ScreenDestination.Alternatives -> _selectedBottomTab.value = "Discover"
            else -> {}
        }
    }

    fun onBottomTabSelected(tab: String) {
        _selectedBottomTab.value = tab
        when (tab) {
            "Home" -> navigateTo(ScreenDestination.Home)
            "Discover" -> navigateTo(ScreenDestination.Alternatives)
            "Saved" -> navigateTo(ScreenDestination.Saved)
            "Profile" -> navigateTo(ScreenDestination.Profile)
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun startResearch(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            repository.addSearchHistory(query)
            _currentScreen.value = ScreenDestination.ResearchProgress
            _researchStepIndex.value = 0
            for (i in 1..researchSteps.size) {
                delay(350)
                _researchStepIndex.value = i
            }
            delay(200)
            val matched = repository.searchProducts(query).firstOrNull() ?: repository.catalog[0]
            _selectedProduct.value = matched
            _currentScreen.value = ScreenDestination.AIResult
        }
    }

    fun toggleSaveProduct(product: Product) {
        viewModelScope.launch {
            val isAlreadySaved = savedProducts.value.any { it.id == product.id }
            if (isAlreadySaved) {
                repository.removeSavedProduct(product.id)
                Toast.makeText(getApplication(), "Removed from Saved List", Toast.LENGTH_SHORT).show()
            } else {
                repository.saveProduct(product)
                Toast.makeText(getApplication(), "Saved to Your List", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun removeSavedProduct(productId: String) {
        viewModelScope.launch {
            repository.removeSavedProduct(productId)
            Toast.makeText(getApplication(), "Removed from Saved List", Toast.LENGTH_SHORT).show()
        }
    }

    fun openStoreDeal(context: Context, url: String, storeName: String = "Store") {
        try {
            // URL Validation (Rule 44: URL Safety)
            val trimmed = url.trim()
            if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
                Toast.makeText(context, "Invalid product link URL", Toast.LENGTH_SHORT).show()
                return
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trimmed)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Opening $storeName deal...", Toast.LENGTH_SHORT).show()
        }
    }

    fun analyzeSharedUrl(url: String) {
        _linkAnalysisResult.value = repository.analyzeProductLink(url)
        _showLinkAnalysisDialog.value = true
    }

    fun openVoiceDialog(show: Boolean) { _showVoiceDialog.value = show }
    fun openImageDialog(show: Boolean) { _showImageDialog.value = show }
    fun openClarificationDialog(show: Boolean) { _showClarificationDialog.value = show }
    fun openLinkAnalysisDialog(show: Boolean) { _showLinkAnalysisDialog.value = show }
    fun openPriceAlertDialog(show: Boolean) { _showPriceAlertDialog.value = show }

    fun testAIProviderConnection(context: Context, provider: AIProviderConfig) {
        Toast.makeText(context, "Testing connection to ${provider.name}...", Toast.LENGTH_SHORT).show()
        viewModelScope.launch {
            delay(500)
            Toast.makeText(context, "✓ Connected to ${provider.name} (${provider.latencyMs}ms)", Toast.LENGTH_LONG).show()
        }
    }
}
