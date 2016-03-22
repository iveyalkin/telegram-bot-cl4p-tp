package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import com.rocketraccoons.hyenas.cl4ptp.bean.QuoteProcessor
import com.rocketraccoons.hyenas.cl4ptp.bean.RestClient
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@ResponseBody
@RequestMapping(produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class BotController @Autowired constructor(
        val restClient: RestClient,
        val commands: BotCommands,
        val messages: BotMessages,
        val quoteProcessor: QuoteProcessor,
        /*val messageProcessor: IMessageProcessor<Update>,*/
        val environmentVars: EnvironmentConstants,
        val logger: Logger
) {

    @RequestMapping("/botHook", method = arrayOf(RequestMethod.POST))
    fun onHook(@RequestParam(name = "hookUuid", defaultValue = "") webhookUuid: String, @RequestBody update: Update) {
        if (webhookUuid == environmentVars.webhookUuid) {
            restClient.sendMessage(CHAT_ID, "Update id ${update.updateId}: ${update.message}")

            /*val updateHandler = messageProcessor.process(update)
            while(updateHandler.hasNext()) {
                val (command, context) = updateHandler.next
                when (command) {
                    how to config from json?
                    "greeting" -> {}
                    "ping" -> {}
                    else -> {}
                }
            }*/
        }
    }

    @RequestMapping("/lulz")
    fun sendLulz() {
        restClient.sendMessage(CHAT_ID, quoteProcessor.handleQuote(restClient.fetchQuote()))
    }

    // TODO: temporary
    companion object {
        val CHAT_ID = "167604177" // rueHbl"-110657123"
    }
}