package com.app.deeplinktester

import android.app.Application
import com.app.deeplinktester.repository.DeepLinkRepository
import com.app.deeplinktester.room.database.DeepLinkDataBase

class DeepLinkApplication : Application() {

    private val database by lazy { DeepLinkDataBase.getDatabase(this) }
    val repository by lazy { DeepLinkRepository(database.deepLinkDao()) }
}