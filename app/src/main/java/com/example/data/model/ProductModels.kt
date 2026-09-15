package com.example.data.model

data class Product(
    val id: String,
    val name: String,
    val brand: String,
    val category: String,
    val subtitle: String,
    val imageDrawableRes: Int,
    val price: Double,
    val originalPrice: Double? = null,
    val discountPercent: Int = 0,
    val currency: String = "৳",
    val rating: Double,
    val reviewCount: Int,
    val aiMatchScore: Int,
    val matchTag: String = "Excellent Match",
    val matchDescription: String = "Perfect for students",
    val bestBadge: String? = null, // e.g. "Best Overall", "Best Budget", "Best Value", "Best Alternative"
    val highlights: List<ProductHighlight> = emptyList(),
    val whyRecommended: List<String> = emptyList(),
    val thingsToKnow: List<String> = emptyList(),
    val specifications: List<Pair<String, String>> = emptyList(),
    val primaryStore: StoreOffer,
    val allOffers: List<StoreOffer> = emptyList(),
    val priceTrendNote: String = "8% lower than average price",
    val buyAdvice: String = "The price is currently 8% lower than the average of the last 30 days.",
    val priceHistory: List<PriceHistoryPoint> = emptyList(),
    val reviewSummary: ReviewSummary,
    val reviewTrust: ReviewTrustInfo,
    val isSaved: Boolean = false
)

data class ProductHighlight(
    val title: String,
    val subtitle: String? = null,
    val iconType: String // "student", "battery", "weight", "value"
)

data class StoreOffer(
    val id: String,
    val storeName: String,
    val price: Double,
    val currency: String = "৳",
    val url: String,
    val badge: String? = null, // e.g. "Best Price"
    val stockStatus: String = "In Stock",
    val deliveryEstimate: String = "2-3 days delivery",
    val rating: Double = 4.8,
    val lastChecked: String = "Updated 10m ago"
)

data class PriceHistoryPoint(
    val label: String,
    val price: Double,
    val isCurrent: Boolean = false
)

data class ReviewSummary(
    val overallRating: Double,
    val totalReviews: Int,
    val positiveSentimentPercent: Int,
    val negativeSentimentPercent: Int,
    val starDistribution: List<Pair<Int, Int>>, // Pair(stars, percentage)
    val whatUsersLiked: List<String>,
    val commonComplaints: List<String>,
    val sampleReviews: List<CustomerReview> = emptyList()
)

data class CustomerReview(
    val author: String,
    val rating: Int,
    val date: String,
    val verifiedPurchase: Boolean = true,
    val title: String,
    val comment: String,
    val helpfulCount: Int = 12
)

data class ReviewTrustInfo(
    val status: String = "High",
    val description: String = "No signs of fake reviews detected",
    val score: Int = 96,
    val details: List<String> = listOf(
        "Natural review distribution over 14 months",
        "Consistent syntax & vocabulary variety",
        "Verified purchase confirmation rate: 89%"
    )
)

data class UserProfile(
    val name: String = "Rafi Islam",
    val email: String = "rafi****@gmail.com",
    val isPremium: Boolean = true,
    val language: String = "English",
    val currency: String = "৳ BDT",
    val budgetStyle: String = "Value & Performance",
    val priorityPreference: String = "Long Battery Life",
    val brandLoyalty: String = "Neutral (Open to best deals)"
)

data class PriceAlert(
    val id: String,
    val productId: String,
    val productName: String,
    val targetPrice: Double,
    val currentPrice: Double,
    val currency: String = "৳",
    val active: Boolean = true
)

data class LinkAnalysisResult(
    val originalUrl: String,
    val detectedStore: String,
    val productName: String,
    val currentPrice: Double,
    val currency: String = "৳",
    val isGoodPrice: Boolean,
    val priceAssessment: String,
    val betterDealFound: Boolean,
    val betterPrice: Double? = null,
    val betterStore: String? = null,
    val betterDealUrl: String? = null,
    val commonComplaints: List<String>,
    val trustScore: Int,
    val recommendationAdvice: String
)
