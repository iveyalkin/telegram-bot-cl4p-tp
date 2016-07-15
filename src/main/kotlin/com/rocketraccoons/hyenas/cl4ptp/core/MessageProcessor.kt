package com.rocketraccoons.hyenas.cl4ptp.core

import com.rocketraccoons.hyenas.cl4ptp.contains
import com.rocketraccoons.hyenas.cl4ptp.core.command.Command
import com.rocketraccoons.hyenas.cl4ptp.core.command.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.extension.command.getMessageHandlerTask
import com.rocketraccoons.hyenas.cl4ptp.model.Message
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload

/**
 * Created by instu_000 on 2/28/2016.
 */
interface MessageProcessor {
    fun process(update: Update): Command?
}

class MessageProcessorImpl(val botCommands: BotCommands, storage: ValueStorage) : MessageProcessor {
    var lastUpdate: Long? = null

    override fun process(update: Update): Command? {
        val message = update.message
        if ((null == lastUpdate || (update.updateId > lastUpdate!!)) && null != message && null != message.text) {
            val text = message.text
            lastUpdate = update.updateId
            val commandStart = text.indexOf(botCommands.commandPrefix) + 1 // next to the prefix
            val directMessageStart = text.indexOf(botCommands.directMessagePrefix) + 1 // next to the prefix
            // TODO: temporary fix
            if (directMessageStart > 0) {
                var endIndex = text.indexOf(' ', directMessageStart)
                if (endIndex > -1) endIndex = text.length
                if (endIndex > directMessageStart) {
                    // TODO: hardcoded! bot gets messages if @ is not at the beginning of the message
                    if (!text.substring(directMessageStart, endIndex).equals("claptrap4bot")) {
                        return null
                    }
                }
            }
            when {
                ((commandStart > 0 && directMessageStart < 1) || (commandStart > 0 && directMessageStart > commandStart)) -> {
                    val endIndex = text.indexOf(' ', commandStart)
                    if (endIndex <= commandStart) {
                        // empty command
                        // TODO: temporary. implement
                        return null
                    }
                    val commandString = text.substring(commandStart, if (endIndex > -1) endIndex else text.length)
                            .split(botCommands.directMessagePrefix)[0]
                    val command = botCommands.getMessageHandlerTask(commandString, message)
                    return command
                }
                ((directMessageStart > 0 && commandStart < 0) || (directMessageStart > 0 && commandStart > directMessageStart)) -> {
                    return object : Command {
                        override val message: Message
                            get() = message

                        override fun execute(): UpdateSendMessagePayload? {
                            return UpdateSendMessagePayload(
                                    message.chat.id,
                                    "What do you need, ${message.from.firstName}?!",
                                    message.messageId
                            )
                        }
                    }
                }
                // TODO: just for lulz
                (text.contains("работу")) -> {
                    return object : Command {
                        override val message: Message
                            get() = message

                        override fun execute(): UpdateSendMessagePayload? {
                            return UpdateSendMessagePayload(message.chat.id, "Витя, смени работу!", message.messageId)
                        }
                    }
                }
                else -> {
                    // ignore unknown message
                }
            }
        }
        return null
    }
}