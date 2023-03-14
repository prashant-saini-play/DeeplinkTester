package com.app.deeplinktester.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "deeplink_table")
class DeepLinkData(@PrimaryKey @ColumnInfo(name = "deeplink") val deeplink: String,
                   @ColumnInfo(name = "timestamp") val timeStamp: Long)
