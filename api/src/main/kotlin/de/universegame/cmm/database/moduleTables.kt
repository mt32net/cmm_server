package de.universegame.cmm.database

import de.universegame.cmm.config
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
    val recieverUUID = varchar("revieverUUID", config.dbConfig.UUIDLength)
    val sender = varchar("sender", 200)
    val iconType = varchar("iconType", 20)
    val iconData = varchar("iconData", 1000)
    val message = varchar("message", 300)
    val sendDateTime = datetime("sendDateTime")

    override val primaryKey = PrimaryKey(recieverUUID, sender, sendDateTime, message)
}

object monitorTable : Table() {
    val uuid = varchar("uuid", config.dbConfig.UUIDLength)
    val recDateTime = datetime("sendDateTime")
    val cpuUsagePercent = double("cpuUsage")
    val ramUsage = double("ramUsage")
    val installedRam = integer("installedRam")

    val processNameMostCPUUsage = varchar("processNameMostCPUUsage", config.dbConfig.maxProcessNameLenght)
    val processMostCPUUsage = double("processMostCPUUsage")
    val processNameMostRAMUsage = varchar("processNameMostRAMUsage", config.dbConfig.maxProcessNameLenght)
    val processMostRAMUsage = double("processMostRAMUsage")

    val networkUploadKbs = double("networkUploadKbs")
    val networkDownloadKbs = double("networkDownloadKbs")

    override val primaryKey = PrimaryKey(uuid, recDateTime)
}