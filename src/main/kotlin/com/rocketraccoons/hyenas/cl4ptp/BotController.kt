package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.bean.QuoteProcessor
import com.rocketraccoons.hyenas.cl4ptp.bean.RestClient
import com.rocketraccoons.hyenas.cl4ptp.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.model.Update
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
        val quoteProcessor: QuoteProcessor,
        /*val messageProcessor: IMessageProcessor<Update>,*/
        val environmentVars: EnvironmentConstants,
        val logger: Logger
) {

    var lastUpdateId: Long = 0L;

    @RequestMapping("/{uuid}/botHook", headers = arrayOf("Content-Type=application/json"), method = arrayOf(RequestMethod.POST))
    fun onHook(@RequestBody update: Update, @PathVariable uuid: String) {
        checkExpoisedApiRequest (uuid) {
            logger.trace("Received webhook callback with: $update")

            if (update.updateId > lastUpdateId) {
                if (update.message.text?.contains("/help", true) ?: false) {
                    restClient.sendMessage(update.message.chat.id, "Not yet. Keep Calm.")
                }
                //restClient.sendMessage(CHAT_ID, "Update id ${update.updateId}: ${update.message}")

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

                lastUpdateId = update.updateId
            }
        }
    }

    @RequestMapping("/{uuid}/lulz", method = arrayOf(RequestMethod.POST))
    fun sendLulz(@PathVariable uuid: String) {
        checkExpoisedApiRequest (uuid) {
            logger.trace("Received lulz request")
            restClient.sendMessage(CHAT_ID, quoteProcessor.handleQuote(restClient.fetchQuote()))
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
        val CHAT_ID = /*167604177*/ -1001045537736 // rueHbl
    }
}