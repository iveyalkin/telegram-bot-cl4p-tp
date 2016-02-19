package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.model.SimpleModel
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class BotController {

    private val tokenEnvironmentVar = "BOT_AUTHENTICATION_TOKEN"

    @RequestMapping("/home")
    fun onUpdate(): SimpleModel {
        val token: String? = System.getenv(tokenEnvironmentVar)

        return SimpleModel()
    }

    @RequestMapping()
    fun onHome(): String = "Welcome..."
}