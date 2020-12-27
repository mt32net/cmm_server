package de.universegame.cmm.apirouter.modules

import de.universegame.cmm.apirouter.modules.modulerouter.monitorResponse
import de.universegame.cmm.apirouter.modules.modulerouter.notificationsResponse
import org.http4k.routing.bind
import org.http4k.routing.routes

var moduleRoutes = routes(
    "/monitor/" bind monitorResponse,
    "/notifications/" bind notificationsResponse
)