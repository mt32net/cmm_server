package de.universegame.cmm.apirouter

import de.universegame.cmm.apirouter.modules.moduleRoutes
import de.universegame.cmm.modules.deviceInfo.getJSONCMMDeviceResponse
import de.universegame.cmm.modules.deviceInfo.updateDeviceInfoResponse
import de.universegame.cmm.modules.deviceList.getJSONCMMDeviceListResponse
import de.universegame.cmm.modules.forgottenUUID.getJSONCMMForgottenUUID
import de.universegame.cmm.modules.info.getJSONCMMInfoResponse
import de.universegame.cmm.modules.register.registerDevice
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.routing.bind
import org.http4k.routing.routes

var apiRoutes =
    routes(
        "/info" bind GET to { request: Request -> getJSONCMMInfoResponse(request) },
        "/devicelist" bind GET to { request: Request -> getJSONCMMDeviceListResponse(request) },
        "/deviceinfo/" bind GET to { request: Request -> getJSONCMMDeviceResponse(request) },
        "/deviceinfo/" bind POST to { request: Request -> updateDeviceInfoResponse(request) },
        "/uuid/" bind GET to { request: Request -> getJSONCMMForgottenUUID(request) },
        "/register/" bind POST to { request: Request -> registerDevice(request) },
        "/modules" bind moduleRoutes,
        "/user" bind userRouting
    )