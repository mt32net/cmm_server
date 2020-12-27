package de.universegame.cmm.database

import org.jetbrains.exposed.sql.Table

object cmmInfoTable : Table() {
    val key = varchar("key", 50)
    val value = varchar("value", 200)

    override val primaryKey = PrimaryKey(key)
}

object usersTable : Table(){
    val username = varchar("username", config.userNameMaxLength)
    val mail = varchar("mail", config.userMailMaxLength)
    val uuid = varchar("uuid", config.UUIDLength)
    val verified = bool("verified")
    
    override val primaryKey = PrimaryKey(username)
}

object devicesTable : Table() {
    val uuid = varchar("uuid", config.UUIDLength)
    val mac = varchar("mac", config.macLength)
    val name = varchar("name", config.clientNameMaxLength)
    val user = varchar("user", config.UUIDLength)
    val clientSecret = varchar("clientSecret", config.clientSecretLength)
    val verified = bool("verified")
    val online = bool("online")

    override val primaryKey = PrimaryKey(uuid)
}

object installedModulesTable : Table() {
    val uuid = varchar("uuid", config.UUIDLength)
    val module = varchar("module", 100)
    val version = varchar("installedVersion", config.versionMaxLength)

    override val primaryKey = PrimaryKey(uuid, module)
}