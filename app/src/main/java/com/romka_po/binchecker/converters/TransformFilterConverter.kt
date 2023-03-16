package com.romka_po.binchecker.converters

import android.text.util.Linkify.TransformFilter
import java.util.regex.Matcher




class TransformFilterConverter:TransformFilter {
    override fun transformUrl(match: Matcher?, url: String): String {
        return url.replace(",","%2C") //remove ,
    }
}