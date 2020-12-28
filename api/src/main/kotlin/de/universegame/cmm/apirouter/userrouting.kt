package de.universegame.cmm.apirouter

import de.universegame.cmm.modules.userInfo.createUserResponse
import de.universegame.cmm.modules.userInfo.getUUIDResponse
import de.universegame.cmm.modules.userInfo.loginUserResponse
import de.universegame.cmm.modules.userInfo.verfiyUserResponse
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes

var userRouting = routes(
    "/register" bind GET to { request: Request -> createUserResponse(request) },
    "/verify" bind GET to { request: Request -> verfiyUserResponse(request) },
    "/login" bind GET to { request: Request -> loginUserResponse(request) },
    "/uuid" bind GET to {request: Request ->  getUUIDResponse(request)}
)