package com.rocketraccoons.hyenas.cl4ptp.db

import com.rocketraccoons.hyenas.cl4ptp.model.Chat
import com.rocketraccoons.hyenas.cl4ptp.model.Quote
import com.rocketraccoons.hyenas.cl4ptp.model.User

/**
 * Created by instu_000 on 3/4/2016.
 */
interface DatabaseClient {
    fun getBotUser(): User
    fun setBotUser(botUser: User)
    fun getConnectedChats(): Chat
    fun setFetchedQuote(quote: Quote)
    open fun lastFetchedQuote(providerType: String): Quote?
}