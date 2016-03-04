package com.rocketraccoons.hyenas.cl4ptp.bean

/**
 * Created by instu_000 on 3/4/2016.
 */
interface QuoteProcessor {
    fun handleQuote(rawInputQuote: String): String
}