package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "saved_products")
data class SavedProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val brand: String,
    val price: Double,
    val currency: String,
    val imageResId: Int,
    val rating: Double,
    val reviewCount: Int,
    val savedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val query: String,
    val timeAgoLabel: String = "Just now",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "price_alerts")
data class PriceAlertEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val productName: String,
    val targetPrice: Double,
    val currentPrice: Double,
    val currency: String,
    val isTriggered: Boolean = false,
    val createdTimestamp: Long = System.currentTimeMillis()
)

@Dao
interface PickWiseDao {
    @Query("SELECT * FROM saved_products ORDER BY savedTimestamp DESC")
    fun getAllSavedProducts(): Flow<List<SavedProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedProduct(product: SavedProductEntity)

    @Delete
    suspend fun deleteSavedProduct(product: SavedProductEntity)

    @Query("DELETE FROM saved_products WHERE id = :productId")
    suspend fun deleteSavedProductById(productId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_products WHERE id = :productId)")
    fun isProductSaved(productId: String): Flow<Boolean>

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 20")
    fun getRecentSearches(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(history: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE id = :id")
    suspend fun deleteSearch(id: Long)

    @Query("DELETE FROM search_history")
    suspend fun clearAllSearches()

    @Query("SELECT * FROM price_alerts ORDER BY createdTimestamp DESC")
    fun getAllPriceAlerts(): Flow<List<PriceAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceAlert(alert: PriceAlertEntity)

    @Query("DELETE FROM price_alerts WHERE id = :id")
    suspend fun deletePriceAlert(id: String)
}

@Database(
    entities = [SavedProductEntity::class, SearchHistoryEntity::class, PriceAlertEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pickWiseDao(): PickWiseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pickwise_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
