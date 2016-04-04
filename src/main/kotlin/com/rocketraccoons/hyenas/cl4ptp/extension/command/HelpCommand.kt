package com.rocketraccoons.hyenas.cl4ptp.extension.command

import com.rocketraccoons.hyenas.cl4ptp.asResponseTo
import com.rocketraccoons.hyenas.cl4ptp.core.command.Command
import com.rocketraccoons.hyenas.cl4ptp.model.Message
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload

/**
 * Created by instu_000 on 4/3/2016.
 */
class HelpCommand(override val message: Message) : Command {
    override fun execute(): UpdateSendMessagePayload {
        // TODO: fetch help from json resource
        return ("/help\t- show general help (this text);\n" +
                "/help {command}\t- show {command} help (this text);\n" +
                "/echo\t- repeat the rest of a message following the ${"/echo"} command 3 times;").asResponseTo(message)
    }
}