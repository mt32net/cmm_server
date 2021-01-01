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
    val handler = routes(
        "/api" bind apiRoutes,
        "/test" bind Method.GET to { request: Request ->
            Response(Status.OK).with(
                Body.auto<Test>().toLens() of Test()
            )
        }
    )
    loadConfig("./config.json")
    initializeDB()

    val server = handler.asServer(Netty(config.serverConfig.serverPort)).start()

    while (readLine() != "e");
    server.stop()
}