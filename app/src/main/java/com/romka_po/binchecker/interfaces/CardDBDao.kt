package com.romka_po.binchecker.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.romka_po.binchecker.model.CardMainInfo

@Dao
interface CardDBDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(card: CardMainInfo)

    @Update
    fun update(card: CardMainInfo)

    @Query("SELECT * FROM cardMainInfoTable WHERE bin = :key")
    fun get(key: Int): CardMainInfo?

    @Query("DELETE FROM cardMainInfoTable")
    fun clear()


    @Query("SELECT * FROM cardMainInfoTable ORDER BY bin DESC")
    suspend fun getAllCard(): List<CardMainInfo>
}