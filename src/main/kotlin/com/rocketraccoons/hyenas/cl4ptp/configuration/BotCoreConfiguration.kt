package com.rocketraccoons.hyenas.cl4ptp.configuration

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.rocketraccoons.hyenas.cl4ptp.bean.BashQuoteProcessor
import com.rocketraccoons.hyenas.cl4ptp.bean.BotInitializer
import com.rocketraccoons.hyenas.cl4ptp.bean.QuoteProcessor
import com.rocketraccoons.hyenas.cl4ptp.bean.RestClient
import com.rocketraccoons.hyenas.cl4ptp.core.MessageProcessor
import com.rocketraccoons.hyenas.cl4ptp.core.MessageProcessorImpl
import com.rocketraccoons.hyenas.cl4ptp.core.ValueStorage
import com.rocketraccoons.hyenas.cl4ptp.core.command.model.BotCommands
import com.rocketraccoons.hyenas.cl4ptp.db.DatabaseClient
import com.rocketraccoons.hyenas.cl4ptp.model.BotMessages
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.io.InputStream
import java.lang.reflect.Type

/**
 * Created by instu_000 on 2/28/2016.
 */
@Configuration
open class BotCoreConfiguration {

    @Autowired
    lateinit var gson: Gson

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    open fun valueStorage() = ValueStorage()

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    open fun botCommands(): BotCommands = inflateResource("commands.json", BotCommands::class.java)

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    open fun botMessages(): BotMessages = inflateResource("quotes.json", BotMessages::class.java)

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    open fun messageProcessor(botCommands: BotCommands, storage: ValueStorage): MessageProcessor = MessageProcessorImpl(botCommands, storage)

    private fun <T> inflateResource(fileName: String, type: Type): T {
        var stream: InputStream? = null
        try {
            stream = javaClass.classLoader.getResourceAsStream(fileName)
            return gson.fromJson<T>(JsonReader(stream.reader(Charsets.UTF_8)), type)
        } finally {
            stream?.close()
        }
    }

    @Bean
    open fun quoteProcessor(): QuoteProcessor = BashQuoteProcessor()

    @Bean
    open fun botInitializer(restClient: RestClient, databaseClient: DatabaseClient, botMessages: BotMessages, logger: Logger) = BotInitializer(restClient, databaseClient, botMessages, logger)
}