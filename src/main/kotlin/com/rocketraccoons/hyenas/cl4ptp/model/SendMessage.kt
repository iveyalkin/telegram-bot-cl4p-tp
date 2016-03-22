package com.rocketraccoons.hyenas.cl4ptp.model

import com.google.gson.annotations.Expose

/**
 * Created by instu_000 on 3/4/2016.
 */
data class SendMessage (val chatId: Int, val parseMode: String?, val replyToMessageId: Int?, @Expose val fullMessage: String) {
    val text: String
        get() {
            return fullMessage.substring(0..Message.maxMessageTextLength)
        }
}