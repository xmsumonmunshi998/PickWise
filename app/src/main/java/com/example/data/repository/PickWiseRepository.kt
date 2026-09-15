package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.ai.AIProviderConfig
import com.example.data.ai.AIProviderRouter
import com.example.data.ai.CostControlSettings
import com.example.data.ai.FeatureRouting
import com.example.data.local.AppDatabase
import com.example.data.local.SavedProductEntity
import com.example.data.local.SearchHistoryEntity
import com.example.data.model.CustomerReview
import com.example.data.model.LinkAnalysisResult
import com.example.data.model.PriceHistoryPoint
import com.example.data.model.Product
import com.example.data.model.ProductHighlight
import com.example.data.model.ReviewSummary
import com.example.data.model.ReviewTrustInfo
import com.example.data.model.StoreOffer
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PickWiseRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val dao = database.pickWiseDao()
    val aiRouter = AIProviderRouter()

    // Default User Profile
    var userProfile = UserProfile(
        name = "Rafi Islam",
        email = "rafi****@gmail.com",
        isPremium = true,
        language = "English",
        currency = "৳ BDT"
    )

    // Admin Configurations
    val aiProviders = mutableListOf(
        AIProviderConfig(
            id = "gemini",
            name = "Google Gemini",
            currentModel = "gemini-1.5-pro",
            availableModels = listOf("gemini-1.5-pro", "gemini-1.5-flash", "gemini-2.0-flash"),
            isEnabled = true,
            latencyMs = 210,
            errorRatePercent = 0.2,
            totalRequestsToday = 194,
            estimatedCostToday = 0.58
        ),
        AIProviderConfig(
            id = "openai",
            name = "OpenAI",
            currentModel = "gpt-4o",
            availableModels = listOf("gpt-4o", "gpt-4o-mini", "o1-preview"),
            isEnabled = true,
            latencyMs = 380,
            errorRatePercent = 0.5,
            totalRequestsToday = 86,
            estimatedCostToday = 1.12
        ),
        AIProviderConfig(
            id = "xai",
            name = "xAI (Grok)",
            currentModel = "grok-2",
            availableModels = listOf("grok-2", "grok-2-mini"),
            isEnabled = true,
            latencyMs = 295,
            errorRatePercent = 0.8,
            totalRequestsToday = 34,
            estimatedCostToday = 0.42
        )
    )

    var featureRoutings = mutableListOf(
        FeatureRouting("Intent Clarification", "Google Gemini", "gemini-1.5-flash", "OpenAI", "gpt-4o-mini"),
        FeatureRouting("Web Research Synthesis", "Google Gemini", "gemini-1.5-pro", "OpenAI", "gpt-4o"),
        FeatureRouting("Review & Trust Analysis", "OpenAI", "gpt-4o", "Google Gemini", "gemini-1.5-pro"),
        FeatureRouting("Product Recommendations", "Google Gemini", "gemini-1.5-pro", "xAI (Grok)", "grok-2"),
        FeatureRouting("Price History Forecasting", "xAI (Grok)", "grok-2", "Google Gemini", "gemini-1.5-flash")
    )

    var costSettings = CostControlSettings(
        dailyBudgetUsd = 20.0,
        monthlyBudgetUsd = 500.0,
        perUserDailyLimit = 50,
        maxParallelRequests = 8,
        maxResearchDepthSources = 15,
        currentDailySpentUsd = 2.12
    )

    // Store Offers for ASUS VivoBook 15 (Matches Reference Screen 7 exactly)
    private val asusOffers = listOf(
        StoreOffer(
            id = "amazon_bd",
            storeName = "Amazon Bangladesh",
            price = 54990.0,
            currency = "৳",
            url = "https://www.amazon.com/dp/B0BY2V5J3B",
            badge = "Best Price",
            stockStatus = "In Stock",
            deliveryEstimate = "2-3 days delivery",
            rating = 4.9,
            lastChecked = "10m ago"
        ),
        StoreOffer(
            id = "daraz",
            storeName = "Daraz",
            price = 56499.0,
            currency = "৳",
            url = "https://www.daraz.com.bd/products/asus-vivobook-15",
            badge = null,
            stockStatus = "In Stock",
            deliveryEstimate = "1-3 days delivery",
            rating = 4.7,
            lastChecked = "25m ago"
        ),
        StoreOffer(
            id = "gadget_gear",
            storeName = "Gadget & Gear",
            price = 57200.0,
            currency = "৳",
            url = "https://gadgetandgear.com/products/asus-vivobook-15",
            badge = null,
            stockStatus = "In Stock",
            deliveryEstimate = "2-4 days delivery",
            rating = 4.8,
            lastChecked = "1h ago"
        ),
        StoreOffer(
            id = "star_tech",
            storeName = "Star Tech",
            price = 58500.0,
            currency = "৳",
            url = "https://www.startech.com.bd/asus-vivobook-15",
            badge = null,
            stockStatus = "In Stock",
            deliveryEstimate = "2-4 days delivery",
            rating = 4.8,
            lastChecked = "45m ago"
        ),
        StoreOffer(
            id = "pickaboo",
            storeName = "Pickaboo",
            price = 59000.0,
            currency = "৳",
            url = "https://www.pickaboo.com/product/asus-vivobook-15",
            badge = null,
            stockStatus = "In Stock",
            deliveryEstimate = "2-4 days delivery",
            rating = 4.6,
            lastChecked = "2h ago"
        ),
        StoreOffer(
            id = "ryans",
            storeName = "Ryans Computers",
            price = 59500.0,
            currency = "৳",
            url = "https://www.ryans.com/asus-vivobook-15",
            badge = null,
            stockStatus = "In Stock",
            deliveryEstimate = "1-2 days delivery",
            rating = 4.7,
            lastChecked = "3h ago"
        )
    )

    // Price History (Matches Reference Screen 8)
    private val asusPriceHistory = listOf(
        PriceHistoryPoint("Apr", 61500.0),
        PriceHistoryPoint("May", 59800.0),
        PriceHistoryPoint("Jun", 57200.0),
        PriceHistoryPoint("Jul", 54990.0, isCurrent = true)
    )

    // Review Summary & Trust Analysis (Matches Reference Screen 6)
    private val asusReviewSummary = ReviewSummary(
        overallRating = 4.4,
        totalReviews = 2843,
        positiveSentimentPercent = 86,
        negativeSentimentPercent = 14,
        starDistribution = listOf(
            5 to 68,
            4 to 18,
            3 to 8,
            2 to 4,
            1 to 2
        ),
        whatUsersLiked = listOf(
            "Good performance",
            "Long battery life",
            "Value for money",
            "Nice display quality"
        ),
        commonComplaints = listOf(
            "Build quality could be better",
            "Sound quality is average",
            "Gets warm during heavy use"
        ),
        sampleReviews = listOf(
            CustomerReview(
                author = "Tariq Ahmed",
                rating = 5,
                date = "2 days ago",
                verifiedPurchase = true,
                title = "Excellent laptop for university coursework and programming",
                comment = "Battery comfortably lasts 7-8 hours on light coding and web browsing. The Intel Core i5 handles multitasking smoothly. Highly recommend at this price point."
            ),
            CustomerReview(
                author = "Sadia Chowdhury",
                rating = 4,
                date = "1 week ago",
                verifiedPurchase = true,
                title = "Great value, audio is slightly tinny",
                comment = "Performance and screen are top tier for ৳54,990. The speakers are just okay for movies, but headphones solve it."
            ),
            CustomerReview(
                author = "Farhan Rahman",
                rating = 5,
                date = "2 weeks ago",
                verifiedPurchase = true,
                title = "Fast boot up and reliable keyboard",
                comment = "Key travel is very comfortable for typing essays. Boots in less than 7 seconds thanks to NVMe SSD."
            )
        )
    )

    private val asusReviewTrust = ReviewTrustInfo(
        status = "High",
        description = "No signs of fake reviews detected.",
        score = 94,
        details = listOf(
            "Natural review distribution over 14 months",
            "Verified purchase cross-check rate: 91%",
            "Sentiment consistency across retail platforms",
            "Duplication anomaly rate: 0.02% (Extremely clean)"
        )
    )

    // Main Catalog of Products
    val catalog: List<Product> = listOf(
        Product(
            id = "asus_vivobook_15",
            name = "ASUS VivoBook 15",
            brand = "ASUS",
            category = "Laptop",
            subtitle = "15.6\" FHD Laptop | Intel Core i5 | 8GB RAM | 512GB SSD",
            imageDrawableRes = R.drawable.img_asus_vivobook,
            price = 54990.0,
            originalPrice = 59990.0,
            discountPercent = 8,
            currency = "৳",
            rating = 4.4,
            reviewCount = 2843,
            aiMatchScore = 92,
            matchTag = "Excellent Match",
            matchDescription = "Perfect for students",
            bestBadge = "Best Overall",
            highlights = listOf(
                ProductHighlight("Student Friendly", null, "student"),
                ProductHighlight("Good Battery", "up to 8 hours", "battery"),
                ProductHighlight("Lightweight", "1.7 kg", "weight"),
                ProductHighlight("Value for Money", null, "value")
            ),
            whyRecommended = listOf(
                "Great performance for daily tasks",
                "Good battery life (up to 8 hours)",
                "Positive user reviews (4.4/5)",
                "Best price in your budget"
            ),
            thingsToKnow = listOf(
                "Average microphone quality in noisy rooms",
                "Speakers are modest; best used with headphones",
                "Chassis is durable plastic rather than aluminum"
            ),
            specifications = listOf(
                "Processor" to "Intel Core i5 (12th Gen)",
                "RAM" to "8GB DDR4",
                "Storage" to "512GB SSD",
                "Display" to "15.6\" FHD (1920x1080)",
                "Operating System" to "Windows 11 Home",
                "Battery Life" to "Up to 8 hours",
                "Weight" to "1.7 kg",
                "Warranty" to "2 Years Official"
            ),
            primaryStore = asusOffers[0],
            allOffers = asusOffers,
            priceTrendNote = "8% lower than average price",
            buyAdvice = "The price is currently 8% lower than the average of the last 30 days.",
            priceHistory = asusPriceHistory,
            reviewSummary = asusReviewSummary,
            reviewTrust = asusReviewTrust
        ),
        Product(
            id = "hp_pavilion_15",
            name = "HP Pavilion 15",
            brand = "HP",
            category = "Laptop",
            subtitle = "15.6\" FHD Laptop | Intel Core i5 | 8GB RAM | 512GB SSD",
            imageDrawableRes = R.drawable.img_hp_pavilion,
            price = 57990.0,
            originalPrice = 62000.0,
            discountPercent = 6,
            currency = "৳",
            rating = 4.3,
            reviewCount = 1240,
            aiMatchScore = 88,
            matchTag = "Great Alternative",
            matchDescription = "Premium aluminum palm rest",
            bestBadge = "Best Alternative",
            highlights = listOf(
                ProductHighlight("Aluminum Finish", null, "value"),
                ProductHighlight("Battery", "Up to 7 hours", "battery"),
                ProductHighlight("B&O Audio", null, "student")
            ),
            whyRecommended = listOf(
                "Sleek build with aluminum keyboard deck",
                "Bang & Olufsen tuned dual speakers",
                "Fast charging up to 50% in 45 minutes"
            ),
            thingsToKnow = listOf(
                "Slightly heavier at 1.8 kg",
                "Price is ৳3,000 higher than ASUS"
            ),
            specifications = listOf(
                "Processor" to "Intel Core i5 (12th Gen)",
                "RAM" to "8GB DDR4",
                "Storage" to "512GB SSD",
                "Display" to "15.6\" FHD (1920x1080)",
                "Operating System" to "Windows 11 Home",
                "Battery Life" to "Up to 7 hours",
                "Weight" to "1.8 kg",
                "Warranty" to "2 Years Official"
            ),
            primaryStore = StoreOffer(
                id = "hp_daraz",
                storeName = "Daraz Official",
                price = 57990.0,
                url = "https://www.daraz.com.bd",
                stockStatus = "In Stock",
                deliveryEstimate = "2-3 days delivery",
                badge = "Verified Store"
            ),
            allOffers = listOf(
                StoreOffer("daraz_hp", "Daraz Mall", 57990.0, "৳", "https://www.daraz.com.bd", "Best Price"),
                StoreOffer("star_hp", "Star Tech", 58900.0, "৳", "https://www.startech.com.bd")
            ),
            priceTrendNote = "Stable price over past 60 days",
            buyAdvice = "Price is stable. Buy if you prefer aluminum keyboard deck.",
            priceHistory = listOf(
                PriceHistoryPoint("Apr", 62000.0),
                PriceHistoryPoint("May", 60500.0),
                PriceHistoryPoint("Jun", 58500.0),
                PriceHistoryPoint("Jul", 57990.0, isCurrent = true)
            ),
            reviewSummary = ReviewSummary(
                overallRating = 4.3,
                totalReviews = 1240,
                positiveSentimentPercent = 83,
                negativeSentimentPercent = 17,
                starDistribution = listOf(5 to 64, 4 to 20, 3 to 9, 2 to 4, 1 to 3),
                whatUsersLiked = listOf("Quality chassis", "Crisp audio", "Comfortable touchpad"),
                commonComplaints = listOf("Display brightness could be higher outdoors")
            ),
            reviewTrust = ReviewTrustInfo()
        ),
        Product(
            id = "lenovo_ideapad_3",
            name = "Lenovo IdeaPad Slim 3",
            brand = "Lenovo",
            category = "Laptop",
            subtitle = "15.6\" FHD | AMD Ryzen 5 | 8GB RAM | 512GB SSD",
            imageDrawableRes = R.drawable.img_asus_vivobook,
            price = 52990.0,
            originalPrice = 56000.0,
            discountPercent = 5,
            currency = "৳",
            rating = 4.2,
            reviewCount = 980,
            aiMatchScore = 86,
            matchTag = "Budget Pick",
            matchDescription = "Lowest price for 512GB SSD",
            bestBadge = "Best Budget",
            highlights = listOf(
                ProductHighlight("Budget Friendly", null, "value"),
                ProductHighlight("Battery", "Up to 6.5 hours", "battery")
            ),
            whyRecommended = listOf(
                "Most affordable Core/Ryzen option",
                "Privacy physical webcam shutter",
                "Rapid charge technology"
            ),
            thingsToKnow = listOf(
                "TN display panel with narrower viewing angles"
            ),
            specifications = listOf(
                "Processor" to "AMD Ryzen 5 5500U",
                "RAM" to "8GB DDR4",
                "Storage" to "512GB SSD",
                "Display" to "15.6\" FHD",
                "Operating System" to "Windows 11 Home",
                "Battery Life" to "Up to 6.5 hours",
                "Weight" to "1.65 kg"
            ),
            primaryStore = StoreOffer("ryans_lenovo", "Ryans Computers", 52990.0, "৳", "https://www.ryans.com", "Best Price"),
            reviewSummary = ReviewSummary(4.2, 980, 80, 20, listOf(5 to 58, 4 to 22, 3 to 12, 2 to 5, 1 to 3), listOf("Affordable", "Speedy boot"), listOf("Screen angles")),
            reviewTrust = ReviewTrustInfo()
        ),
        Product(
            id = "acer_aspire_5",
            name = "Acer Aspire 5",
            brand = "Acer",
            category = "Laptop",
            subtitle = "15.6\" IPS FHD | Intel Core i3 12th Gen | 8GB RAM | 256GB SSD",
            imageDrawableRes = R.drawable.img_hp_pavilion,
            price = 49990.0,
            originalPrice = 53500.0,
            discountPercent = 7,
            currency = "৳",
            rating = 4.1,
            reviewCount = 760,
            aiMatchScore = 82,
            matchTag = "Best Value",
            matchDescription = "Under ৳50k with IPS display",
            bestBadge = "Best Value",
            highlights = listOf(
                ProductHighlight("Under ৳50K", null, "value"),
                ProductHighlight("IPS Screen", null, "student")
            ),
            whyRecommended = listOf(
                "Crisp IPS panel with rich viewing angles",
                "Ergonomic lift-hinge design for comfortable typing"
            ),
            thingsToKnow = listOf(
                "256GB storage; may require external drive or upgrade"
            ),
            specifications = listOf(
                "Processor" to "Intel Core i3 (12th Gen)",
                "RAM" to "8GB",
                "Storage" to "256GB SSD",
                "Display" to "15.6\" Full HD IPS",
                "Battery Life" to "Up to 7 hours"
            ),
            primaryStore = StoreOffer("star_acer", "Star Tech", 49990.0, "৳", "https://www.startech.com.bd", "Best Price"),
            reviewSummary = ReviewSummary(4.1, 760, 78, 22, listOf(5 to 55, 4 to 23, 3 to 14, 2 to 5, 1 to 3), listOf("IPS panel", "Good keyboard"), listOf("256GB SSD")),
            reviewTrust = ReviewTrustInfo()
        ),
        Product(
            id = "sony_wh1000xm4",
            name = "Sony WH-1000XM4",
            brand = "Sony",
            category = "Audio",
            subtitle = "Wireless Premium Noise-Canceling Headphones",
            imageDrawableRes = R.drawable.img_sony_headphones,
            price = 28990.0,
            originalPrice = 33000.0,
            discountPercent = 12,
            currency = "৳",
            rating = 4.7,
            reviewCount = 5420,
            aiMatchScore = 95,
            matchTag = "Top Audio Pick",
            matchDescription = "Industry leading noise cancellation",
            bestBadge = "Best Premium",
            highlights = listOf(
                ProductHighlight("30h Battery", "Up to 30 hours", "battery"),
                ProductHighlight("ANC Pioneer", null, "student"),
                ProductHighlight("Comfort Fit", null, "value")
            ),
            whyRecommended = listOf(
                "Superb active noise cancellation for travel & study",
                "Multipoint Bluetooth connection to 2 devices",
                "30-hour battery life with quick charge"
            ),
            thingsToKnow = listOf(
                "Not water-resistant; not designed for heavy rain/sports"
            ),
            specifications = listOf(
                "Type" to "Over-ear Wireless",
                "Battery Life" to "Up to 30 hours",
                "Noise Canceling" to "Dual Noise Sensor Technology",
                "Bluetooth" to "Version 5.0 with LDAC",
                "Weight" to "254 grams"
            ),
            primaryStore = StoreOffer("gadget_sony", "Gadget & Gear", 28990.0, "৳", "https://gadgetandgear.com", "Best Price"),
            reviewSummary = ReviewSummary(4.7, 5420, 92, 8, listOf(5 to 82, 4 to 12, 3 to 4, 2 to 1, 1 to 1), listOf("World class ANC", "Super comfortable", "Long battery"), listOf("Touch sensor in cold weather")),
            reviewTrust = ReviewTrustInfo()
        ),
        Product(
            id = "nike_revolution_6",
            name = "Nike Revolution 6",
            brand = "Nike",
            category = "Shoes",
            subtitle = "Men's Road Running & Walking Shoes",
            imageDrawableRes = R.drawable.img_nike_shoes,
            price = 6499.0,
            originalPrice = 7500.0,
            discountPercent = 13,
            currency = "৳",
            rating = 4.5,
            reviewCount = 1890,
            aiMatchScore = 89,
            matchTag = "Best Daily Runner",
            matchDescription = "Plush foam cushioning",
            bestBadge = "Best Value",
            highlights = listOf(
                ProductHighlight("Breathable Mesh", null, "student"),
                ProductHighlight("Flexible Sole", null, "weight")
            ),
            whyRecommended = listOf(
                "Soft foam midsole provides a smooth, stable ride",
                "Lightweight knit mesh hugs your foot in breathable comfort",
                "Computer-generated outsole design acts like a natural piston"
            ),
            thingsToKnow = listOf(
                "Fits snug; consider half size up for wide feet"
            ),
            specifications = listOf(
                "Sport" to "Running / Walking",
                "Upper" to "Recycled Mesh",
                "Outsole" to "Durable Rubber",
                "Weight" to "285 grams"
            ),
            primaryStore = StoreOffer("daraz_nike", "Daraz Official Store", 6499.0, "৳", "https://www.daraz.com.bd", "Best Price"),
            reviewSummary = ReviewSummary(4.5, 1890, 88, 12, listOf(5 to 72, 4 to 18, 3 to 6, 2 to 2, 1 to 2), listOf("Very light", "Good arch support", "Durable"), listOf("Narrow toe box")),
            reviewTrust = ReviewTrustInfo()
        ),
        Product(
            id = "samsung_galaxy_watch6",
            name = "Samsung Galaxy Watch6",
            brand = "Samsung",
            category = "Smartwatch",
            subtitle = "44mm Smartwatch | Sapphire Crystal | Wear OS",
            imageDrawableRes = R.drawable.img_galaxy_watch,
            price = 23990.0,
            originalPrice = 27500.0,
            discountPercent = 13,
            currency = "৳",
            rating = 4.6,
            reviewCount = 2150,
            aiMatchScore = 91,
            matchTag = "Top Smartwatch",
            matchDescription = "Best health & fitness tracking",
            bestBadge = "Best Overall",
            highlights = listOf(
                ProductHighlight("Sleep Coach", null, "student"),
                ProductHighlight("Sapphire Glass", null, "value"),
                ProductHighlight("ECG & BIA", null, "battery")
            ),
            whyRecommended = listOf(
                "20% larger display with thinner rotating bezel",
                "Advanced sleep coaching and body composition analysis",
                "Smooth Wear OS with Google Maps, Wallet & Assistant"
            ),
            thingsToKnow = listOf(
                "Battery requires daily or 1.5-day charging"
            ),
            specifications = listOf(
                "Display" to "1.5\" Super AMOLED (480x480)",
                "Glass" to "Sapphire Crystal",
                "OS" to "Wear OS Powered by Samsung",
                "Sensors" to "Optical Heart Rate, ECG, BIA, Barometer",
                "Water Resistance" to "5ATM + IP68 / MIL-STD-810H"
            ),
            primaryStore = StoreOffer("star_watch", "Star Tech", 23990.0, "৳", "https://www.startech.com.bd", "Best Price"),
            reviewSummary = ReviewSummary(4.6, 2150, 87, 13, listOf(5 to 74, 4 to 15, 3 to 6, 2 to 3, 1 to 2), listOf("Gorgeous screen", "Accurate sleep tracking", "Fast UI"), listOf("Battery lasts 1-1.5 days")),
            reviewTrust = ReviewTrustInfo()
        )
    )

    // Local DB Saved Products Flow
    val savedProductsFlow: Flow<List<SavedProductEntity>> = dao.getAllSavedProducts()

    // Local DB Recent Searches Flow
    val recentSearchesFlow: Flow<List<SearchHistoryEntity>> = dao.getRecentSearches()

    suspend fun saveProduct(product: Product) {
        dao.insertSavedProduct(
            SavedProductEntity(
                id = product.id,
                name = product.name,
                brand = product.brand,
                price = product.price,
                currency = product.currency,
                imageResId = product.imageDrawableRes,
                rating = product.rating,
                reviewCount = product.reviewCount
            )
        )
    }

    suspend fun removeSavedProduct(productId: String) {
        dao.deleteSavedProductById(productId)
    }

    fun isProductSaved(productId: String): Flow<Boolean> {
        return dao.isProductSaved(productId)
    }

    suspend fun addSearchHistory(query: String, timeAgo: String = "Just now") {
        dao.insertSearch(SearchHistoryEntity(query = query, timeAgoLabel = timeAgo))
    }

    suspend fun clearSearchHistory() {
        dao.clearAllSearches()
    }

    // Populate initial default saved products if empty so Saved Screen matches reference
    suspend fun prepopulateDefaultsIfEmpty() {
        // Prepopulate search history as seen in reference screen 2 & 3
        dao.insertSearch(SearchHistoryEntity(query = "wireless earbuds under 5000", timeAgoLabel = "2h ago"))
        dao.insertSearch(SearchHistoryEntity(query = "best laptop for students", timeAgoLabel = "5h ago"))
        dao.insertSearch(SearchHistoryEntity(query = "running shoes", timeAgoLabel = "1d ago"))

        // Prepopulate saved items as seen in reference screen 11
        saveProduct(catalog[0]) // ASUS VivoBook 15
        saveProduct(catalog[4]) // Sony WH-1000XM4
        saveProduct(catalog[5]) // Nike Revolution 6
        saveProduct(catalog[6]) // Samsung Galaxy Watch6
    }

    fun getProductById(id: String): Product {
        return catalog.find { it.id == id } ?: catalog[0]
    }

    fun searchProducts(query: String): List<Product> {
        val q = query.trim().lowercase()
        if (q.contains("laptop") || q.contains("student") || q.contains("vivobook") || q.contains("hp") || q.contains("lenovo") || q.contains("acer") || q.contains("computer")) {
            return catalog.filter { it.category == "Laptop" }
        }
        if (q.contains("earbud") || q.contains("headphone") || q.contains("sony") || q.contains("audio") || q.contains("sound") || q.contains("bass")) {
            return listOf(catalog[4])
        }
        if (q.contains("shoe") || q.contains("nike") || q.contains("running") || q.contains("feet")) {
            return listOf(catalog[5])
        }
        if (q.contains("watch") || q.contains("smartwatch") || q.contains("samsung") || q.contains("clock")) {
            return listOf(catalog[6])
        }
        // Bengali queries matching prompt examples
        if (q.contains("হাজার") || q.contains("টাকা") || q.contains("ভালো") || q.contains("চাই")) {
            if (q.contains("smartwatch") || q.contains("ঘড়ি")) {
                return listOf(catalog[6])
            }
            if (q.contains("headphone") || q.contains("earbud") || q.contains("হেডফোন")) {
                return listOf(catalog[4])
            }
            return catalog.filter { it.category == "Laptop" }
        }
        return catalog
    }

    // Link Analysis Feature (Section 30)
    fun analyzeProductLink(url: String): LinkAnalysisResult {
        val lower = url.lowercase()
        return if (lower.contains("amazon") || lower.contains("laptop") || lower.contains("vivobook")) {
            LinkAnalysisResult(
                originalUrl = url,
                detectedStore = "Amazon",
                productName = "ASUS VivoBook 15 (15.6\" FHD | i5 | 8GB | 512GB)",
                currentPrice = 58500.0,
                currency = "৳",
                isGoodPrice = false,
                priceAssessment = "৳3,510 above the verified lowest price in Bangladesh.",
                betterDealFound = true,
                betterPrice = 54990.0,
                betterStore = "Amazon Bangladesh Partner Store",
                betterDealUrl = "https://www.amazon.com/dp/B0BY2V5J3B",
                commonComplaints = listOf(
                    "Average speaker volume",
                    "Battery drops quickly under intense gaming"
                ),
                trustScore = 94,
                recommendationAdvice = "We recommend ordering from the Partner Store link to save ৳3,510 with authentic 2-year warranty."
            )
        } else {
            LinkAnalysisResult(
                originalUrl = url,
                detectedStore = "Online Merchant",
                productName = "Analyzed Product Offer",
                currentPrice = 28990.0,
                currency = "৳",
                isGoodPrice = true,
                priceAssessment = "Price is competitive within 3% of market average.",
                betterDealFound = false,
                commonComplaints = listOf("Standard shipping takes 3-5 days"),
                trustScore = 92,
                recommendationAdvice = "Price and store reputation are sound. You can proceed safely."
            )
        }
    }
}
