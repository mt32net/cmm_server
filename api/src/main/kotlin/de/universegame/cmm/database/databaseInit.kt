package de.universegame.cmm.database

import de.universegame.cmm.cmmMinClientModuleVersions
import de.universegame.cmm.cmmModuleListURL
import de.universegame.cmm.cmmServerVersion
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


var database: Database? = null

fun initialize() {
    database = Database.connect(config.mysqlUrl, driver = "com.mysql.jdbc.Driver", user = config.mysqlUser, password = config.mysqlPwd)

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
        it[value] = cmmServerVersion
    }

    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "moduleListURL"
        it[value] = cmmModuleListURL
    }

    cmmInfoTable.upsert(cmmInfoTable.key) {
        it[key] = "cmmMinClientModuleVersions"
        it[value] = cmmMinClientModuleVersions
    }
}