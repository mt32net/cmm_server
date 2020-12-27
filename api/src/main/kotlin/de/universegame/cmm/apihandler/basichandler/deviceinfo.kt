package de.universegame.cmm.apihandler.basichandler

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.modules.deviceInfo.CMMDevice
import de.universegame.cmm.modules.deviceInfo.getJSONCMMDevice
import de.universegame.cmm.modules.deviceInfo.updateDeviceInfo

import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes

var deviceInfoResponse = routes(
    "/" bind Method.GET to { request: Request ->
        Response(Status.OK).with(
            Body.auto<CMMDevice>().toLens() of getJSONCMMDevice(request.query("uuid") ?: "")
        )
    },
    "/" bind Method.POST to { request: Request ->
        Response(
            updateDeviceInfo(
                uuid = request.query("uuid"),
                name = request.query("name"),
                online = request.query("online").toBoolean()
            )
        )
    }
)
