package de.universegame.cmm.apirouter

import de.universegame.cmm.modules.deviceInfo.getJSONCMMDeviceResponse
import de.universegame.cmm.modules.deviceInfo.updateDeviceInfoResponse
import de.universegame.cmm.modules.deviceList.getJSONCMMDeviceListResponse
import de.universegame.cmm.modules.forgottenUUID.getJSONCMMForgottenUUID
import de.universegame.cmm.modules.register.registerDeviceResponse
import de.universegame.cmm.modules.register.requestRegisterDeviceResponse
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes

var deviceRouting = routes(
    "/list" bind GET to { request: Request -> getJSONCMMDeviceListResponse(request) },
    "/info" bind GET to { request: Request -> getJSONCMMDeviceResponse(request) },
    "/info" bind POST to { request: Request -> updateDeviceInfoResponse(request) },
    "/uuid" bind GET to { request: Request -> getJSONCMMForgottenUUID(request) },
    "/register" bind GET to { request: Request -> requestRegisterDeviceResponse(request) },
    "/register" bind POST to { request: Request -> registerDeviceResponse(request) },
)