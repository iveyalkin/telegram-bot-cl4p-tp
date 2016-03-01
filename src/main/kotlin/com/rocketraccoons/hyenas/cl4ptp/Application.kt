package com.rocketraccoons.hyenas.cl4ptp

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Created by instu_000 on 2/19/2016.
 */
@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude = arrayOf(JacksonAutoConfiguration::class))
open class Application

fun main(args: Array<String>) {
    val port = System.getenv("PORT")
    if (!port.isNullOrBlank()) {
        System.setProperty("server.port", port) // watch out! important to start server within heroku
    }
    SpringApplicationBuilder(Application::class.java)
            .properties(mapOf("spring.http.converters.preferred-json-mapper" to "gson"))
            .build()
            .run(*args)
}
