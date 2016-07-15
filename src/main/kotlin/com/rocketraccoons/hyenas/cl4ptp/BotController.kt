package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.bean.QuoteProcessor
import com.rocketraccoons.hyenas.cl4ptp.bean.RestClient
import com.rocketraccoons.hyenas.cl4ptp.core.MessageProcessor
import com.rocketraccoons.hyenas.cl4ptp.core.command.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.db.DatabaseClient
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import com.rocketraccoons.hyenas.cl4ptp.model.UpdateSendMessagePayload
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@RestController
@ResponseBody
@RequestMapping(produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class BotController @Autowired constructor(
        val restClient: RestClient,
        val commands: BotCommands,
        val messages: BotMessages,
        val databaseClient: DatabaseClient,
        val quoteProcessor: QuoteProcessor,
        val messageProcessor: MessageProcessor,
        val environmentVars: EnvironmentConstants,
        val logger: Logger
) {

    @RequestMapping("/{uuid}/botHook", headers = arrayOf("Content-Type=application/json"), method = arrayOf(RequestMethod.POST))
    fun onHook(@RequestBody update: Update, @PathVariable uuid: String): UpdateSendMessagePayload? {
        return checkExpoisedApiRequest (uuid) {
            logger.info("Received webhook callback with: $update")
            messageProcessor.process(update)?.execute() ?: null
        }
    }

    @RequestMapping("/{uuid}/lulz", method = arrayOf(RequestMethod.POST))
    fun sendLulz(@PathVariable uuid: String) {
        checkExpoisedApiRequest (uuid) {
            logger.info("Received lulz request")
            restClient.sendMessage(CHAT_ID, quoteProcessor.handleQuote(restClient.fetchQuote()))
        }
    }

    @RequestMapping("/{uuid}/friday", method = arrayOf(RequestMethod.POST))
    fun sendFridaySpecial(@PathVariable uuid: String) {
        checkExpoisedApiRequest (uuid) {
            logger.info("Received friday special request")
            restClient.sendMessage(CHAT_ID, restClient.fetchFridaySpecial())
        }
    }

    // Total control - setup a model and return the view name yourself. Or consider
    // subclassing ExceptionHandlerExceptionResolver (see below).
    @ExceptionHandler(Exception::class)
    fun handleError(req: HttpServletRequest, exception: Exception): ModelAndView {
        logger.error("Request: ${req.requestURL} raised exception. ${
        if (exception is HttpClientErrorException) {
            "Client API returned: ${exception.responseBodyAsString}."
        } else ""
        }", exception)

        val mav = ModelAndView()
        //mav.addObject("exception", exception)
        //mav.addObject("url", req.requestURL)
        mav.viewName = "error"
        return mav
    }

    fun <T> checkExpoisedApiRequest(uuid: String, action: () -> T): T? {
        if (uuid == environmentVars.webhookUuid) {
            return action()
        }
        return null
    }

    // TODO: c
    companion object {
        val CHAT_ID = /*167604177*/ -1001045537736L // rueHbl
        val BAH_ID = 142794370L
    }
}