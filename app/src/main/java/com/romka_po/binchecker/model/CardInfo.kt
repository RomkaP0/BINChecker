package com.romka_po.binchecker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardMainInfoTable")
data class CardMainInfo(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "bin")
    var bin: String = "",

    @ColumnInfo(name = "bank")
    var bank: String? = null,
//
//    @ColumnInfo(name = "length")
//    var len: Int = 0,
//
//    @ColumnInfo(name = "luhn")
//    var luhn: Boolean = false,
//
//    @ColumnInfo(name = "scheme")
//    var scheme: String? = null,
//
//    @ColumnInfo(name = "type")
//    var type: String? = null,
//
//    @ColumnInfo(name = "brand")
//    var brand: String? = null,
//
//    @ColumnInfo(name = "prepaid")
//    var prepaid: Boolean = false,
//
//    @ColumnInfo(name = "country")
//    var country: String? = null
)
//
//@Entity(tableName = "countryTable")
//data class CountryInfo(
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = "country")
//    var name: String= "",
//
//    @ColumnInfo(name="currency")
//    var currency: String? = null,
//
//    @ColumnInfo(name="latitude")
//    var latitude: Int = 0,
//
//    @ColumnInfo(name="longitude")
//    var longitude: Int = 0
//)
//
//@Entity(tableName = "bankTable")
//data class BankInfo(
//
//    @PrimaryKey(autoGenerate = false)
//    @ColumnInfo(name = "bank")
//    var name: String = "",
//
//    @ColumnInfo(name = "url")
//    var url: String? = null,
//
//    @ColumnInfo(name = "phone")
//    var phone: String? = null
//)