package com.rocketraccoons.hyenas.cl4ptp.extension.command;

import com.rocketraccoons.hyenas.cl4ptp.asResponseTo
import com.rocketraccoons.hyenas.cl4ptp.core.command.Command
import com.rocketraccoons.hyenas.cl4ptp.model.Message
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload

/**
 * Created by instu_000 on 4/3/2016.
 */
class EchoCommand(override val message: Message) : Command {
    override fun execute(): UpdateSendMessagePayload? {
        val repeatQuot = message.text!!.substringAfter("/echo", "")
                //.substringAfter("@${databaseClient.getBotUser().username}")
                .substringAfter(' ', "")
                .trim()
        if (repeatQuot.isNotEmpty()) {
            return "$repeatQuot $repeatQuot $repeatQuot".asResponseTo(message)
        }
        return "I'll repeat the rest of a message following the ${"/echo"} command.".asResponseTo(message)
    }

}