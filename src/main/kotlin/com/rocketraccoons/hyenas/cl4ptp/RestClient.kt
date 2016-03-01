package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.model.Update

/**
 * Created by instu_000 on 3/1/2016.
 */
interface RestClient {
    fun getUpdates(): Update
    fun sendMessage(chatId: String, text: String)
    fun fetchQuote(): String
}
