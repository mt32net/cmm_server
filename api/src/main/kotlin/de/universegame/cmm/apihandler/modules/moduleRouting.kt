package de.universegame.cmm.apihandler.modules

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.apihandler.modules.modulehandler.monitorResponse
import de.universegame.cmm.apihandler.modules.modulehandler.notificationsResponse
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes

var moduleRoutes = routes(
    "/monitor/" bind monitorResponse,
    "/notifications/" bind notificationsResponse
)