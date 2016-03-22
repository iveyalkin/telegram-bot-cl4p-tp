package com.rocketraccoons.hyenas.cl4ptp.configuration

import com.mongodb.MongoClientOptions
import com.mongodb.MongoClientURI
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.rocketraccoons.hyenas.cl4ptp.db.DatabaseClient
import com.rocketraccoons.hyenas.cl4ptp.db.MongoDbClient
import com.rocketraccoons.hyenas.cl4ptp.db.schema.Users
import com.rocketraccoons.hyenas.cl4ptp.model.EnvironmentConstants
import kotlinx.nosql.CreateDrop
import kotlinx.nosql.SchemaGenerationAction
import kotlinx.nosql.mongodb.MongoDB
import kotlinx.nosql.mongodb.MongoDBSession
import org.apache.log4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by instu_000 on 3/4/2016.
 */
@Configuration
open class DatabseConfiguration {

    @Bean
    open fun mongoDb(environmentConstants: EnvironmentConstants, schemaGenerationAction: SchemaGenerationAction<MongoDBSession>): MongoDB {
        val uri = MongoClientURI(environmentConstants.mongoUri)
        val seeds: Array<ServerAddress> = uri.getHosts()!!.map { host ->
            if (host.indexOf(':') > 0) {
                val tokens = host.split(':')
                ServerAddress(tokens[0], tokens[1].toInt())
            } else
                ServerAddress(host)
        }.toTypedArray()
        val database: String = if (uri.getDatabase() != null) uri.getDatabase()!! else "test"
        val options: MongoClientOptions = uri.getOptions()!!
        val credentials = if (uri.getUsername() != null)
            arrayOf(MongoCredential.createScramSha1Credential(uri.getUsername(), database, uri.getPassword())!!)
        else arrayOf()
        return MongoDB(seeds, database, credentials, options, arrayOf(Users), schemaGenerationAction)
    }

    @Bean
    open fun schemaGenerationAction(): SchemaGenerationAction<MongoDBSession> = CreateDrop(onCreate = {
        // TODO: nothing to do yet
    }, onDrop = {
        // TODO: nothing to do yet
    })

    @Bean
    open fun database(mongoDb: MongoDB, logger: Logger): DatabaseClient = MongoDbClient(mongoDb, logger)
}