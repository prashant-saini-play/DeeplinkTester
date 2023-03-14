package com.app.deeplinktester.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.deeplinktester.room.model.DeepLinkData
import kotlinx.coroutines.flow.Flow

@Dao
interface DeepLinkDao {

    @Query("SELECT * FROM deeplink_table ORDER BY timestamp DESC")
    fun getAllDeepLinks(): Flow<List<DeepLinkData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(deepLinkData: DeepLinkData)

    @Query("DELETE FROM deeplink_table")
    suspend fun deleteAll()

    @Query("DELETE FROM deeplink_table WHERE deeplink = :deepLink")
    suspend fun delete(deepLink: String)
}