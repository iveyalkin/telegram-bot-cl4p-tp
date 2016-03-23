package com.rocketraccoons.hyenas.cl4ptp.model

/**
 * Created by instu_000 on 3/23/2016.
 */
data class UpdateSendMessagePayload(val chatId: Long, val text: String, val replyToMessageId: Long?) {
    val method = "sendMessage"
}