package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.PickWiseRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("PickWise", appName)
    }

    @Test
    fun `verify catalog contains reference products`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = PickWiseRepository(context)

        val asus = repository.catalog.firstOrNull { it.id == "asus_vivobook_15" }
        assertNotNull(asus)
        assertEquals("ASUS VivoBook 15", asus!!.name)
        assertEquals(54990.0, asus.price, 0.01)
        assertEquals("Best Overall", asus.bestBadge)
        assertEquals(92, asus.aiScore)
        assertEquals(6, asus.allOffers.size)
        assertEquals(4.4, asus.rating, 0.01)

        val hp = repository.catalog.firstOrNull { it.id == "hp_pavilion_15" }
        assertNotNull(hp)
        assertEquals(57990.0, hp!!.price, 0.01)
    }

    @Test
    fun `verify link analyzer identifies deals`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = PickWiseRepository(context)

        val result = repository.analyzeProductLink("https://www.amazon.com/dp/B0BY2V5J3B")
        assertNotNull(result)
        assertTrue(result.betterDealFound)
        assertEquals("Amazon BD", result.betterStore)
        assertEquals(54990.0, result.betterPrice!!, 0.01)
    }

    @Test
    fun `verify search results fallback`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repository = PickWiseRepository(context)

        val results = repository.searchProducts("laptop")
        assertTrue(results.isNotEmpty())
        assertEquals("ASUS VivoBook 15", results.first().name)
    }
}
