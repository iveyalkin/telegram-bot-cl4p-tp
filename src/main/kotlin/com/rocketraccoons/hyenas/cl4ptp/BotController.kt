package com.rocketraccoons.hyenas.cl4ptp

import com.google.gson.Gson
import com.rocketraccoons.hyenas.cl4ptp.configuration.bean.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@ResponseBody
@RequestMapping(produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class BotController {

    @Autowired
    lateinit var gson: Gson;

    @Autowired
    lateinit var commands: BotCommands

    @Autowired
    lateinit var messages: BotMessages

    @Autowired
    lateinit var environmentVars: EnvironmentConstants

    @RequestMapping()
    fun onHome(): BotMessages = messages

    @RequestMapping("/bot", method = arrayOf(RequestMethod.POST))
    fun onHook(@RequestParam(name = "", defaultValue = "") update: Update) {

    }
}