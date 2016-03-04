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

    @RequestMapping("/bot", method = arrayOf(RequestMethod.POST))
    fun onHook(@RequestParam(name = "", defaultValue = "") path: String, @RequestBody update: Update) {
        if (path.startsWith("/bot{${environmentVars.webhookUuid}}", true)) {
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
        restClient.sendMessage("-110657123", quoteProcessor.handleQuote(restClient.fetchQuote()))
    }
}