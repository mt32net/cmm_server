package de.universegame.cmm.database

import de.universegame.cmm.config
import de.universegame.cmm.dateTimeFormatter
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.ZoneOffset


var database: Database? = null

fun initializeDB() {
    database = Database.connect(config.dbConfig.mysqlUrl, driver = "com.mysql.jdbc.Driver", user = config.dbConfig.mysqlUser, password = config.dbConfig.mysqlPwd)

    transaction {
        //addLogger(StdOutSqlLogger)
        SchemaUtils.create(cmmInfoTable, usersTable, devicesTable, installedModulesTable, loginSecretsTable, sessionsTable)

        updateCMMInfoTable()
        initModuleTables()
    }
}

fun updateCMMInfoTable() {
    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "version"
        it[value] = config.cmmServerVersion
    }

    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "moduleListURL"
        it[value] = config.cmmModuleListURL
    }

    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "cmmMinClientModuleVersions"
        it[value] = config.cmmMinClientModuleVersions
    }

    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "onlineSince"
        it[value] = LocalDateTime.now().format(dateTimeFormatter)
    }

    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "onlineSinceUnix"
        it[value] = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0)).toString()
    }
}