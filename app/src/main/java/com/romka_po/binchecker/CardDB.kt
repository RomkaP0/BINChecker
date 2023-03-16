package com.romka_po.binchecker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.romka_po.binchecker.interfaces.CardDBDao
import com.romka_po.binchecker.model.CardMainInfo
import com.romka_po.binchecker.model.DateConverter

//@Database(entities = [CardMainInfo::class, CountryInfo::class, BankInfo::class], version = 1, exportSchema = false)
@Database(entities = [CardMainInfo::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class CardDB: RoomDatabase() {
    abstract fun getCardDBDao(): CardDBDao

    companion object {
        @Volatile
        private var instance: CardDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CardDB::class.java,
                "card_info.db"
            ).build()
    }
}