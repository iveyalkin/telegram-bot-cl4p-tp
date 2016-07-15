package com.rocketraccoons.hyenas.cl4ptp.extension.command

import com.rocketraccoons.hyenas.cl4ptp.core.command.Command
import com.rocketraccoons.hyenas.cl4ptp.core.command.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.model.Message

/**
 * Created by instu_000 on 4/3/2016.
 */
fun BotCommands.getMessageHandlerTask(command: String, message: Message): Command {
    val commandIndex = commands.indexOf(command)
    // TODO: use reflection to retrieve class?
    when (commandIndex) {
        0 -> return HelpCommand(
                message,
                message.text!!.substring(message.text.indexOf(commandPrefix + command) + (commandPrefix + command).length)
                        .trim()
                        .split(" ")
                        .toTypedArray()
        )
        1 -> return EchoCommand(message)
        else -> return UnsupportedCommand(message)
    }
}