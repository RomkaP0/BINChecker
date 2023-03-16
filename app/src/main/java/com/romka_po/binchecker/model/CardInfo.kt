package com.romka_po.binchecker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "cardMainInfoTable")
data class CardMainInfo(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "bin")
    var bin: String = "",

    @ColumnInfo(name = "bank")
    var bank: String? = null,

    @TypeConverters(DateConverter::class)
    @ColumnInfo(name = "datequery")
    var date: Date? = null

)
