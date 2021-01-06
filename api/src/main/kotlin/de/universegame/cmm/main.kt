package de.universegame.cmm

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.apirouter.apiRoutes
import de.universegame.cmm.database.initializeDB
import org.http4k.core.*
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

object CMMInfoJackson : ConfigurableJackson(
    KotlinModule()
        .asConfigurable()
        .withStandardMappings()
        .done()
        .deactivateDefaultTyping()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
)

fun main() {
    logger.init()
    log("Init Routes")
    val handler = routes(
        "/api" bind apiRoutes,
        "/test" bind Method.GET to { request: Request ->
            Response(Status.OK).with(
                Body.auto<Test>().toLens() of Test()
            )
        }
    )
    log("Loading config")
    val firstBoot = loadConfig("./config.json")
    if(!firstBoot) {
        log("Load DB")
        initializeDB()

        log("Starting server")
        val server = handler.asServer(Netty(config.serverConfig.serverPort)).start()
        log("To stop the server, type 'stop'")

        while (readLine() != "stop");
        log("Stopping server")
        server.stop()
    }else{
        log("Started server without existing config")
        log("Created config, not initialized database")
        log("Please set up the config file for proper functiononality")
    }
}