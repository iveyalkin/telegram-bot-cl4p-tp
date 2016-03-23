package com.rocketraccoons.hyenas.cl4ptp.bean

import com.rocketraccoons.hyenas.cl4ptp.constant.ApiConstants
import com.rocketraccoons.hyenas.cl4ptp.model.ApiResponseUpdates
import com.rocketraccoons.hyenas.cl4ptp.model.ApiResponseUser
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
class RestTemplateClient(restTemplate: RestTemplate, apiConstants: ApiConstants) : RestClient {
    private val restDelegate: RestTemplate
    private var apiConstants: ApiConstants

    private val stringResponseExtractor: ResponseExtractor<String>

    init {
        restDelegate = restTemplate
        this.apiConstants = apiConstants
        stringResponseExtractor = HttpMessageConverterExtractor<String>(String::class.java,
                listOf(StringHttpMessageConverter(Charsets.UTF_8)))
    }

    override fun getMe() = restDelegate.getForObject(apiConstants.telegramUrl("getMe"), ApiResponseUser::class.java).result

    override fun getUpdates(): List<Update> = restDelegate.getForObject(apiConstants.telegramUrl("getUpdates"), ApiResponseUpdates::class.java).result

    override fun sendMessage(chatId: Long, text: String, replyToMessageId: Long?) = restDelegate.execute<Unit>(apiConstants.telegramUrl("sendMessage"), HttpMethod.POST,
            RequestCallback {
                // explicitly convert to String, since there is no default HttpMessageConverter for Long, and Spring assumes APPLICATION_FORM_URLENCODED is all about Strings... sucks ):
                val paramMap = mutableMapOf("text" to listOf(text), "chat_id" to listOf(chatId.toString()))
                if (null != replyToMessageId) {
                    // same thing, see "chat_id" comment
                    paramMap.put("reply_to_message_id", listOf(replyToMessageId.toString()))
                }
                FormHttpMessageConverter().write(LinkedMultiValueMap<String, String>(paramMap),
                        MediaType.APPLICATION_FORM_URLENCODED, it)
            }, null)

    override fun fetchQuote() = restDelegate.execute(apiConstants.quoteUrl, HttpMethod.GET, RequestCallback { }, stringResponseExtractor)
}
