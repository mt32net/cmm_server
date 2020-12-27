package de.universegame.cmm.apihandler.modules.modulehandler

import de.universegame.cmm.modules.monitor.updateMonitor
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.routing.bind
import org.http4k.routing.routes

var monitorResponse = routes(
    "/" bind Method.POST to { request: Request ->
        Response(
            updateMonitor(
                uuid = request.query("uuid"),
                cpuUsage = request.query("cpuUsage"),
                ramUsage = request.query("ramUsage"),
                installedRam = request.query("installedRam"),
                networkUploadKbs = request.query("upload"),
                networkDownloadKbs = request.query("download")
            )
        )
    }
)