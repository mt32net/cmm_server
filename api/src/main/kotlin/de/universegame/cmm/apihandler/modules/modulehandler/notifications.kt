package de.universegame.cmm.apihandler.modules.modulehandler

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.modules.notifications.NotificationsList
import de.universegame.cmm.modules.notifications.getJSONCMMNotificationList
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes

var notificationsResponse = routes(
    "/" bind Method.GET to { request: Request ->
        Response(Status.OK).with(
            Body.auto<NotificationsList>().toLens() of getJSONCMMNotificationList(
                request.query("recieveruuid") ?: ""
            )
        )
    }
)