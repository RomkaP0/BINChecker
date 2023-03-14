package com.romka_po.binchecker.model

import androidx.annotation.Keep

@Keep
class Card {
    var number: Number? = null
    var scheme: String? = null
    var type: String? = null
    var brand: String? = null
    var prepaid = false
    var country: Country? = null
    var bank: Bank? = null

}


class Bank {
    var name: String? = null
    var url: String? = null
    var phone: String? = null
}

class Country {

    var name: String? = null
    var currency: String? = null
    var latitude = 0
    var longitude = 0
}

class Number {
    var length = 0
    var luhn = false
}

