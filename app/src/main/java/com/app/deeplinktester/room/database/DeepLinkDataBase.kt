package com.app.deeplinktester.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.deeplinktester.room.dao.DeepLinkDao
import com.app.deeplinktester.room.model.DeepLinkData
import kotlinx.coroutines.CoroutineScope

@Database(entities = [DeepLinkData::class], version = 1)
public abstract class DeepLinkDataBase : RoomDatabase() {

    abstract fun deepLinkDao(): DeepLinkDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DeepLinkDataBase? = null

        fun getDatabase(context: Context): DeepLinkDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DeepLinkDataBase::class.java,
                    "deeplink_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}