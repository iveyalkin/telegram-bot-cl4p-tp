package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.constant.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import com.rocketraccoons.hyenas.cl4ptp.model.Update
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
        /*val messageProcessor: IMessageProcessor<Update>,*/
        val environmentVars: EnvironmentConstants,
        val logger: Logger
) {

    @RequestMapping()
    fun onHome(): Update {
        return restClient.getUpdates()
    }

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
    fun sendLulz(): String {
        val bashQuote = handleBash(restClient.fetchQuote())
        restClient.sendMessage("-110657123", bashQuote)
        return bashQuote
    }
}



fun handleBash(html: String): String {
    val delims = arrayOf("<' + '/span><' + 'div", "<' + '/div><' + 'small><' + 'a");
    val strs = html.split(delimiters = *delims, ignoreCase = true);
    return strs[1].substring(strs[1].indexOf('>') + 1)
            .replace("<' + 'br>", "\n")
            .replace("<' + 'br />", "\n")
            .replace("&quot;", "\"")
            .replace("&lt;", "<")
            .replace("&gt;", ">");
}