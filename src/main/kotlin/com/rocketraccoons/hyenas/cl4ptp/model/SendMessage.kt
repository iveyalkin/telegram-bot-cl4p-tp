package com.rocketraccoons.hyenas.cl4ptp.model

/**
 * Created by instu_000 on 3/4/2016.
 */
data class SendMessage (val chatId: Int, val fullMessage: String, val parseMode: String?, val replyToMessageId: Int?) {
    val text: String
        get() {
            return fullMessage.substring(0..Message.maxMessageTextLength)
        }
}