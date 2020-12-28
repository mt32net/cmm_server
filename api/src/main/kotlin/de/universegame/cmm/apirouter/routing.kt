package de.universegame.cmm.apirouter

import de.universegame.cmm.apirouter.modules.moduleRoutes
import de.universegame.cmm.modules.info.getJSONCMMInfoResponse
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes


var apiRoutes =
    routes(
        "/info" bind GET to { request: Request -> getJSONCMMInfoResponse(request) },
        "/devices" bind deviceRouting,
        "/modules" bind moduleRoutes,
        "/user" bind userRouting
    )