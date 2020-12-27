package de.universegame.cmm

import java.time.LocalDateTime

data class NotificationsList(val notifications: List<Notification>)

data class Notification(
    val recieverUUID: String,
    val sender: String,
    val iconType: IconType,
    val iconData: String,
    val message: String,
    val sendTime: String
)

enum class IconType {
    base64Encoded,
    url
}

data class MachineState(
    val uuid: String,
    val cpuUsagePercent: Number,
    val ramUsage: Number,
    val installedRam: Number,
    val networkUploadKb: Number,
    val networkDownloadKb: Number
)