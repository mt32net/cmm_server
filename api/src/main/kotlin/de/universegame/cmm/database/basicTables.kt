package de.universegame.cmm.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object cmmInfoTable : Table() {
    val key = varchar("key", 50)
    val value = varchar("value", 200)

    override val primaryKey = PrimaryKey(key)
}

object commandListTable : Table() {
    val command = varchar("cmd", 50)
    val method = varchar("httpMethod", 10)
    val enabled = bool("enabled")

    override val primaryKey = PrimaryKey(command, method)
}

object devicesTable : Table() {
    val uuid = varchar("uuid", clientUUIDLength)
    val name = varchar("name", clientNameLength)
    val online = bool("online")
    val lastUpdated = datetime("lastUpdated")
    val clientSecret = varchar("clientSecret", clientSecretLength)

    override val primaryKey = PrimaryKey(uuid)
}

object installedModulesTable : Table() {
    val uuid = varchar("uuid", clientUUIDLength)
    val module = varchar("module", 100)
    val version = varchar("installedVersion", versionLength)

    override val primaryKey = PrimaryKey(uuid, module)
}