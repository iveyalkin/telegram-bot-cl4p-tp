package com.rocketraccoons.hyenas.cl4ptp.bean

import com.rocketraccoons.hyenas.cl4ptp.model.Update
import com.rocketraccoons.hyenas.cl4ptp.model.User

/**
 * Created by instu_000 on 3/1/2016.
 */
interface RestClient {
    // telegram API
    fun getMe(): User
    fun getUpdates(): List<Update>?
    fun sendMessage(chatId: Long, text: String, replyToMessageId: Long? = null)

    // third-party API
    fun fetchQuote(): String

    fun fetchFridaySpecial(): String
}
