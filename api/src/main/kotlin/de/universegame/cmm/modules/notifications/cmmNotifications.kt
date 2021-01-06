package de.universegame.cmm.modules.notifications

import de.universegame.cmm.database.notificationsTable
import de.universegame.cmm.dateTimeFormatter
import de.universegame.cmm.log
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class NotificationsList(val notifications: List<Notification>)

data class Notification(
    val recieverUUID: String,
    val sender: String,
    val iconType: IconType,
    val iconData: String,
    val message: String,
    val sendDateTime: String
)

enum class IconType {
    base64Encoded,
    url
}

fun getJSONCMMNotificationList(recieverUUID: String): NotificationsList {
    var notifications = mutableListOf<Notification>()
    try{
    transaction {
        notificationsTable.select { notificationsTable.recieverUUID eq recieverUUID }.toList().forEach {
            notifications.add(getJSONCMMNotification(it))
        }
    }
    return NotificationsList(notifications)
    }catch(e: Exception){
        log(e.stackTraceToString())
        return NotificationsList(listOf())
    }
}

private fun getJSONCMMNotification(it: ResultRow): Notification {
    return Notification(
        recieverUUID = it[notificationsTable.recieverUUID],
        sender = it[notificationsTable.sender],
        iconType = IconType.valueOf(it[notificationsTable.iconType]),
        iconData = it[notificationsTable.iconData],
        message = it[notificationsTable.message],
        sendDateTime = it[notificationsTable.sendDateTime].format(dateTimeFormatter)
    )
}