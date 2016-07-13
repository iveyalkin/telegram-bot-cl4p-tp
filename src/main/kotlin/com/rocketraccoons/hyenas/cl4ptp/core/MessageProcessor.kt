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
            val commandStart = text.indexOf(botCommands.commandPrefix)
            val directMessageStart = text.indexOf(botCommands.directMessagePrefix)
            when {
                ((commandStart > -1 && directMessageStart < 0) || (commandStart > -1 && directMessageStart > commandStart)) -> {
                    val endIndex = text.indexOf(' ', commandStart + 1)
                    val commandString = text.substring(commandStart + 1, if (endIndex > -1) endIndex else text.length)
                            .split(botCommands.directMessagePrefix)[0]
                    val command = botCommands.getMessageHandlerTask(commandString, message)
                    return command
                }
                ((directMessageStart > -1 && commandStart < 0) || (directMessageStart > -1 && commandStart > directMessageStart)) -> return null
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