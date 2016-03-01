package com.rocketraccoons.hyenas.cl4ptp.configuration

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.rocketraccoons.hyenas.cl4ptp.RestClient
import com.rocketraccoons.hyenas.cl4ptp.RestClientImpl
import com.rocketraccoons.hyenas.cl4ptp.constant.EnvironmentConstants
import com.rocketraccoons.hyenas.cl4ptp.constant.ApiConstantsImpl
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpRequest
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.net.URI

/**
 * Created by instu_000 on 2/23/2016.
 */
@Configuration
open class NetworkClientConfiguration {

    val gson: Gson

    constructor() {
        gson = GsonBuilder().
                setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Update::class.java,
                        JsonDeserializer<com.rocketraccoons.hyenas.cl4ptp.model.Update> { je, type, jdc ->
                            jdc?.deserialize(je?.asJsonObject?.get("result")?.asJsonArray?.get(0), type)
                        })
                .create()
    }

    @Bean
    open fun gsonBean(): Gson {
        return gson
    }

    @Bean
    open fun restTemplateBean(): RestTemplate {
        val rest = RestTemplate()
        val messageConverter = GsonHttpMessageConverter()
        messageConverter.gson = gson
        rest.messageConverters = listOf(messageConverter)
        return rest;
    }

    @Bean
    open fun restClienBean(restTemplate: RestTemplate, environmentVars: EnvironmentConstants): RestClient {
        return RestClientImpl(restTemplate, ApiConstantsImpl(environmentVars))
    }

    val customRequestFabric = object : ClientHttpRequestFactory {
        val factory = SimpleClientHttpRequestFactory()

        override fun createRequest(uri: URI?, method: HttpMethod?): ClientHttpRequest? {
            val createRequest = factory.createRequest(uri, method)
            createRequest.headers.set("Accept", "application/json")
            return createRequest
        }
    }
}