package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.model.Message
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload

/**
 * Created by instu_000 on 3/30/2016.
 */
fun String.asResponseTo(message: Message, appendReply: Boolean = false) =
        UpdateSendMessagePayload(
                message.chat.id,
                this,
                if (appendReply) message.messageId else null
        )

fun String.contains(vararg strings: String) = null != strings.find { this.contains(it, true) }