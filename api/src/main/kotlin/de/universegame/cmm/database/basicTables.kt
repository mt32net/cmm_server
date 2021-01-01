package de.universegame.cmm.database

import de.universegame.cmm.config
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object cmmInfoTable : Table() {
    val key = varchar("key", 50)
    val value = varchar("value", 200)

    override val primaryKey = PrimaryKey(key)
}

object usersTable : Table(){
    val username = varchar("username", config.dbConfig.userNameMaxLength)
    val mail = varchar("mail", config.dbConfig.userMailMaxLength)
    val uuid = varchar("uuid", config.dbConfig.UUIDLength)
    val verified = bool("verified")
    val pwd = varchar("pwd", 200)
    
    override val primaryKey = PrimaryKey(uuid)
}

object devicesTable : Table() {
    val uuid = varchar("uuid", config.dbConfig.UUIDLength)
    val mac = varchar("mac", config.dbConfig.macLength)
    val name = varchar("name", config.dbConfig.clientNameMaxLength)
    val user = varchar("user", config.dbConfig.UUIDLength)
    val clientSecret = varchar("clientSecret", config.dbConfig.clientSecretLength)
    val verified = bool("verified")
    val online = bool("online")

    override val primaryKey = PrimaryKey(uuid)
}

object installedModulesTable : Table() {
    val uuid = varchar("uuid", config.dbConfig.UUIDLength)
    val module = varchar("module", 100)
    val version = varchar("installedVersion", config.dbConfig.versionMaxLength)

    override val primaryKey = PrimaryKey(uuid, module)
}

object loginSecretsTable: Table(){
    val userUUID = varchar("user", config.dbConfig.UUIDLength)
    val secret = varchar("secret", config.dbConfig.UUIDLength)
    val created = datetime("created")
}

object sessionsTable: Table(){
    val uuid = varchar("uuid", config.dbConfig.UUIDLength)
    val sessionID = varchar("sessionID", config.dbConfig.UUIDLength)
    val created = datetime("created")
    val expiration = datetime("expiration")
}