package com.example.data.ai

import com.example.data.model.Product

data class AIProviderConfig(
    val id: String,
    val name: String,
    val currentModel: String,
    val availableModels: List<String>,
    val isEnabled: Boolean = true,
    val apiKeyMasked: String = "sk-•••••••••••••24",
    val latencyMs: Long = 240,
    val errorRatePercent: Double = 0.4,
    val temperature: Float = 0.2f,
    val maxTokens: Int = 2048,
    val totalRequestsToday: Int = 142,
    val estimatedCostToday: Double = 0.48
)

data class FeatureRouting(
    val featureName: String,
    val primaryProvider: String,
    val primaryModel: String,
    val fallbackProvider: String,
    val fallbackModel: String
)

data class CostControlSettings(
    val dailyBudgetUsd: Double = 15.0,
    val monthlyBudgetUsd: Double = 350.0,
    val perUserDailyLimit: Int = 40,
    val maxParallelRequests: Int = 6,
    val maxResearchDepthSources: Int = 12,
    val currentDailySpentUsd: Double = 3.82
)

data class AIRecommendationOutput(
    val topProduct: Product,
    val confidenceScore: Int,
    val reasons: List<String>,
    val cautionaryNotes: List<String>,
    val providerUsed: String,
    val modelUsed: String,
    val latencyMs: Long
)

interface AIProvider {
    val id: String
    val displayName: String
    suspend fun generateRecommendation(prompt: String, candidateProducts: List<Product>): AIRecommendationOutput
    suspend fun testConnection(): Pair<Boolean, String>
}

class GeminiAIProvider(
    override val id: String = "gemini",
    override val displayName: String = "Google Gemini"
) : AIProvider {
    override suspend fun generateRecommendation(prompt: String, candidateProducts: List<Product>): AIRecommendationOutput {
        val selected = candidateProducts.firstOrNull() ?: throw IllegalStateException("No products found")
        return AIRecommendationOutput(
            topProduct = selected,
            confidenceScore = selected.aiMatchScore,
            reasons = selected.whyRecommended,
            cautionaryNotes = selected.thingsToKnow,
            providerUsed = displayName,
            modelUsed = "gemini-1.5-pro",
            latencyMs = 210
        )
    }

    override suspend fun testConnection(): Pair<Boolean, String> {
        return true to "Connected successfully (Latency: 198ms)"
    }
}

class OpenAIProvider(
    override val id: String = "openai",
    override val displayName: String = "OpenAI"
) : AIProvider {
    override suspend fun generateRecommendation(prompt: String, candidateProducts: List<Product>): AIRecommendationOutput {
        val selected = candidateProducts.firstOrNull() ?: throw IllegalStateException("No products")
        return AIRecommendationOutput(
            topProduct = selected,
            confidenceScore = selected.aiMatchScore,
            reasons = selected.whyRecommended,
            cautionaryNotes = selected.thingsToKnow,
            providerUsed = displayName,
            modelUsed = "gpt-4o",
            latencyMs = 380
        )
    }

    override suspend fun testConnection(): Pair<Boolean, String> {
        return true to "Connected successfully (Latency: 340ms)"
    }
}

class XAIProvider(
    override val id: String = "xai",
    override val displayName: String = "xAI (Grok)"
) : AIProvider {
    override suspend fun generateRecommendation(prompt: String, candidateProducts: List<Product>): AIRecommendationOutput {
        val selected = candidateProducts.firstOrNull() ?: throw IllegalStateException("No products")
        return AIRecommendationOutput(
            topProduct = selected,
            confidenceScore = selected.aiMatchScore,
            reasons = selected.whyRecommended,
            cautionaryNotes = selected.thingsToKnow,
            providerUsed = displayName,
            modelUsed = "grok-2",
            latencyMs = 295
        )
    }

    override suspend fun testConnection(): Pair<Boolean, String> {
        return true to "Connected successfully (Latency: 280ms)"
    }
}

class AIProviderRouter {
    private val providers = mutableMapOf<String, AIProvider>(
        "gemini" to GeminiAIProvider(),
        "openai" to OpenAIProvider(),
        "xai" to XAIProvider()
    )

    var defaultProviderId: String = "gemini"
    var fallbackProviderId: String = "openai"

    fun getActiveProvider(feature: String = "recommendation"): AIProvider {
        return providers[defaultProviderId] ?: providers["gemini"]!!
    }

    fun getFallbackProvider(): AIProvider {
        return providers[fallbackProviderId] ?: providers["openai"]!!
    }
}
