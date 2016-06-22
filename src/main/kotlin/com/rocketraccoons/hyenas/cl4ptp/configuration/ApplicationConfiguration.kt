package com.rocketraccoons.hyenas.cl4ptp.configuration

import com.rocketraccoons.hyenas.cl4ptp.constant.ApiConstants
import com.rocketraccoons.hyenas.cl4ptp.constant.ApiConstantsImpl
import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants
import org.apache.log4j.LogManager
import org.apache.log4j.Logger
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

/**
 * Created by instu_000 on 2/21/2016.
 */
@Configuration
open class ApplicationConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    open fun environmentConstants(): EnvironmentConstants {
        return EnvironmentConstants (
                authenticationToken = BuildConfig.BOT_AUTHENTICATION_TOKEN ?: "",
                webhookUuid = BuildConfig.BOT_WEBHOOK_UUID ?: "",
                mongoUri = BuildConfig.MONGO_URI ?: ""
        )
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    open fun apiConstants(environmentConstants: EnvironmentConstants) : ApiConstants = ApiConstantsImpl(environmentConstants)

    @Bean
    open fun loggerBean(): Logger {
        val logger = LogManager.getLogger("CL4P-TP-Log")
        return logger
    }
}