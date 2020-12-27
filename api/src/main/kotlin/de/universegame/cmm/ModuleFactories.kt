package de.universegame.cmm

import de.universegame.cmm.database.notificationsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

fun getJSONCMMNotificationList(recieverUUID: String): NotificationsList {
    var notifications = mutableListOf<Notification>()
    transaction {
        notificationsTable.select { notificationsTable.recieverUUID eq recieverUUID }.toList().forEach {
            notifications.add(getJSONCMMNotification(it))
        }
    }
    return NotificationsList(notifications)
}

private fun getJSONCMMNotification(it: ResultRow): Notification {
    return Notification(
        recieverUUID = it[notificationsTable.recieverUUID],
        sender = it[notificationsTable.sender],
        iconType = IconType.valueOf(it[notificationsTable.iconType]),
        iconData = it[notificationsTable.iconData],
        message = it[notificationsTable.message],
        sendTime = it[notificationsTable.sendTime].format(dateTimeFormatter)
    )
}