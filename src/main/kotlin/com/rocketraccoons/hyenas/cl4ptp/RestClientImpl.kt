package com.rocketraccoons.hyenas.cl4ptp

import com.rocketraccoons.hyenas.cl4ptp.constant.ApiConstants
import com.rocketraccoons.hyenas.cl4ptp.model.Update
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpMessageConverterExtractor
import org.springframework.web.client.RequestCallback
import org.springframework.web.client.ResponseExtractor
import org.springframework.web.client.RestTemplate

/**
 * Created by instu_000 on 3/1/2016.
 */
class RestClientImpl : RestClient {
    private val restDelegate: RestTemplate
    private var apiConstants: ApiConstants

    private val stringResponseExtractor: ResponseExtractor<String>

    constructor(restTemplate: RestTemplate, apiConstants: ApiConstants) {
        restDelegate = restTemplate
        this.apiConstants = apiConstants
        stringResponseExtractor = HttpMessageConverterExtractor<String>(String::class.java,
                listOf(StringHttpMessageConverter(Charsets.UTF_8)))
    }

    override fun getUpdates(): Update = restDelegate.getForObject(apiConstants.telegramUrl("getUpdates"), Update::class.java)

    override fun sendMessage(chatId: String, text: String) = restDelegate.execute<Unit>(apiConstants.telegramUrl("sendMessage"), HttpMethod.POST,
            RequestCallback {
                FormHttpMessageConverter().write(LinkedMultiValueMap<String, String>(
                        mapOf("text" to listOf(text), "chat_id" to listOf(chatId))),
                        MediaType.APPLICATION_FORM_URLENCODED, it)
            }, null)

    override fun fetchQuote() = restDelegate.execute(apiConstants.quoteUrl, HttpMethod.GET, RequestCallback { }, stringResponseExtractor)
}