package de.universegame.cmm.database

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.transactions.transaction

fun initModuleTables() {
    transaction {
        SchemaUtils.create(notificationsTable, monitorTable)
    }
}

object notificationsTable : Table() {
    val recieverUUID = varchar("revieverUUID", clientUUIDLength)
    val sender = varchar("sender", 200)
    val iconType = varchar("iconType", 20)
    val iconData = varchar("iconData", 1000)
    val message = varchar("message", 300)
    val sendTime = datetime("sendTime")

    override val primaryKey = PrimaryKey(recieverUUID, sender, sendTime, message)
}

object monitorTable : Table() {
    val uuid = varchar("uuid", clientUUIDLength)
    val cpuUsagePercent = double("cpuUsage")
    val ramUsage = double("ramUsage")
    val installedRam = integer("installedRam")
    val networkUploadKbs = double("networkUploadKbs")
    val networkDownloadKbs = double("networkDownloadKbs")
}