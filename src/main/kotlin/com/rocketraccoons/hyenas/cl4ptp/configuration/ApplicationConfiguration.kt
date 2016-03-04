package com.rocketraccoons.hyenas.cl4ptp.configuration

import com.rocketraccoons.hyenas.cl4ptp.constant.Constants
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
                authenticationToken = System.getenv(Constants.TOKEN_ENVIRONMENT_VAR) ?: "",
                webhookUuid = System.getenv(Constants.WEBHOOK_UUID_ENVIRONMENT_VAR) ?: ""
        )
    }

    @Bean
    open fun loggerBean(): Logger {
        val logger = LogManager.getLogger("CL4P-TP-Log")
        return logger
    }
}