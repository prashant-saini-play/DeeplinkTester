package com.app.deeplinktester.repository

import androidx.annotation.WorkerThread
import com.app.deeplinktester.room.dao.DeepLinkDao
import com.app.deeplinktester.room.model.DeepLinkData
import kotlinx.coroutines.flow.Flow

class DeepLinkRepository(private val deepLinkDao: DeepLinkDao) {

    val allDeepLinks: Flow<List<DeepLinkData>> = deepLinkDao.getAllDeepLinks()

    @WorkerThread
    suspend fun insert(deepLinkData: DeepLinkData) {
        deepLinkDao.insert(deepLinkData)
    }

    @WorkerThread
    suspend fun delete(deepLink: String) {
        deepLinkDao.delete(deepLink)
    }
}