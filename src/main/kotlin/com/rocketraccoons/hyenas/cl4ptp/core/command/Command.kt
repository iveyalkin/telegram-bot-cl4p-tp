package com.rocketraccoons.hyenas.cl4ptp.core.command

import com.rocketraccoons.hyenas.cl4ptp.model.Message
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload

/**
 * Created by instu_000 on 4/3/2016.
 */
interface Command { // TODO: turn into functional object?
    val message: Message
    val args: Array<String>
        get() = emptyArray()
    fun execute(): UpdateSendMessagePayload?
}
