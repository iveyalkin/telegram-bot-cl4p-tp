package com.rocketraccoons.hyenas.cl4ptp.configuration

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.rocketraccoons.hyenas.cl4ptp.Constants
import com.rocketraccoons.hyenas.cl4ptp.configuration.bean.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.configuration.bean.environmentConstants
import com.rocketraccoons.hyenas.cl4ptp.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.InputStream
import java.lang.reflect.Type

/**
 * Created by instu_000 on 2/21/2016.
 */
@Configuration
open class ApplicationConfiguration {

    @Autowired
    lateinit var gson: Gson

    @Bean
    open fun botCommands(): BotCommands {
        return inflateResource("commands.json", BotCommands::class.java)
    }

    @Bean
    open fun botMessages(): BotMessages {
        return inflateResource("quotes.json", BotMessages::class.java)
    }

    @Bean
    open fun environmentConstants(): EnvironmentConstants {
        return environmentConstants {
            authenticationToken = System.getenv(Constants.TOKEN_ENVIRONMENT_VAR) ?: ""
            webhookUuid = System.getenv(Constants.WEBHOOK_UUID_ENVIRONMENT_VAR) ?: ""
        }
    }

    private fun <T> inflateResource(fileName: String, type: Type): T {
        var stream: InputStream? = null
        try {
            stream = javaClass.classLoader.getResourceAsStream(fileName)
            return gson.fromJson<T>(JsonReader(stream.reader(Charsets.UTF_8)), type)
        } finally {
            stream?.close()
        }
    }
}