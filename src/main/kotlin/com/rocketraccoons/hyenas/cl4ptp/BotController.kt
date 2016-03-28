package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.bean.QuoteProcessor
import com.rocketraccoons.hyenas.cl4ptp.bean.RestClient
import com.rocketraccoons.hyenas.cl4ptp.model.*
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
    fun onHook(@RequestBody update: Update, @PathVariable uuid: String): UpdateSendMessagePayload? {
        return checkExpoisedApiRequest (uuid) {
            logger.info("Received webhook callback with: $update")
            var response: UpdateSendMessagePayload? = null

            if (update.updateId > lastUpdateId) {
                lastUpdateId = update.updateId
                if (update.message.text?.contains("/help", true) ?: false) {
                    response = UpdateSendMessagePayload(update.message.chat.id, "Not yet. Keep Calm.", update.message.messageId)
                } else if (update.message.text?.contains("/echo", true) ?: false) {
                    val repeatQuot = update.message.text!!.substringAfter("/echo", "")
                    if (repeatQuot.isNotEmpty()) {
                        response = UpdateSendMessagePayload(update.message.chat.id, "$repeatQuot $repeatQuot $repeatQuot", update.message.messageId)
                    } else {
                        response = UpdateSendMessagePayload(update.message.chat.id, "I'll repeat the rest of a message following the ${"/echo"} command.", update.message.messageId)
                    }
//                    val updateHandler = messageProcessor.process(update)
//                    while(updateHandler.hasNext()) {
//                        val (command, context) = updateHandler.next
//                        when (command) {
//                            how to config from json?
//                            "greeting" -> {}
//                            "ping" -> {}
//                            else -> {}
//                        }
//                    }
                } else if (BAH_ID == update.message.from.id && update.message.text?.contains("?", true) ?: false) {
                    response = UpdateSendMessagePayload(update.message.chat.id, "Если ты Ваня, то можно все.", update.message.messageId)
                }
            }

            response
        }
    }

    @RequestMapping("/{uuid}/lulz", method = arrayOf(RequestMethod.POST))
    fun sendLulz(@PathVariable uuid: String) {
        checkExpoisedApiRequest (uuid) {
            logger.info("Received lulz request")
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
        val CHAT_ID = /*167604177*/ -1001045537736L // rueHbl
        val BAH_ID = 142794370L
    }
}