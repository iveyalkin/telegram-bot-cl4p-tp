package com.rocketraccoons.hyenas.cl4ptp.extension.command;

import com.rocketraccoons.hyenas.cl4ptp.asResponseTo
import com.rocketraccoons.hyenas.cl4ptp.core.command.Command
import com.rocketraccoons.hyenas.cl4ptp.model.Message
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload

/**
 * Created by instu_000 on 4/3/2016.
 */
class UnsupportedCommand(override val message: Message) : Command {
    override fun execute(): UpdateSendMessagePayload {
        return "Unsupported command: $message.".asResponseTo(message)
    }
}
