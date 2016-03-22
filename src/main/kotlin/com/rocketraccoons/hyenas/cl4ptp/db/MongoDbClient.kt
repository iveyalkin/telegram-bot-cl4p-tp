package com.rocketraccoons.hyenas.cl4ptp.db

import com.rocketraccoons.hyenas.cl4ptp.db.schema.FetchedQuotes
import com.rocketraccoons.hyenas.cl4ptp.db.schema.Users
import com.rocketraccoons.hyenas.cl4ptp.model.Chat
import com.rocketraccoons.hyenas.cl4ptp.model.Quote
import com.rocketraccoons.hyenas.cl4ptp.model.User
import kotlinx.nosql.DocumentSchemaQueryParams
import kotlinx.nosql.equal
import kotlinx.nosql.mongodb.MongoDB
import org.apache.log4j.Logger
import java.util.*

/**
 * Created by instu_000 on 3/4/2016.
 */
class MongoDbClient(mongoDB: MongoDB, val logger: Logger) : DatabaseClient {
    val database: MongoDB

    init {
        database = mongoDB
    }

    override fun setFetchedQuote(quote: Quote) {
        database.withSession {
            FetchedQuotes.insert(quote)
        }
    }

    override fun lastFetchedQuote(providerType: String): Quote? {
        database.withSession {
            FetchedQuotes.find { provider.equal(providerType) }.maxBy { it.date }
        }

        val quotes = LinkedList<Quote>()
        for (quote in database.session.find(DocumentSchemaQueryParams(FetchedQuotes))) {
            if (quote.provider == providerType) {
                quotes.add(quote)
            }
        }
        return quotes.maxBy { it.date }
    }

    override fun setBotUser(botUser: User) {
        database.withSession {
            botUser.isMe = true
            Users.insert(botUser)
        }
    }

    override fun getBotUser() = database.withSession {
        Users.find { isMe.equal(true) }.single()
    }

    override fun getConnectedChats(): Chat {
        throw UnsupportedOperationException()
    }
}