package de.universegame.cmm

import org.http4k.core.Status.Companion.OK
import org.http4k.core.Method.DELETE
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.http4k.core.*
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import org.http4k.core.Body
import org.http4k.core.Response
import org.http4k.core.with

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.apihandler.apiRoutes
import de.universegame.cmm.database.initialize
import de.universegame.cmm.jsonclass.*

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
        "/api" bind apiRoutes
    )

    initialize()

    val server = handler.asServer(Netty(9000)).start()

    while (readLine() != "e");
    server.stop()
}