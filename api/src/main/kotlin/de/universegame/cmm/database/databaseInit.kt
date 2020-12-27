package de.universegame.cmm.database

import de.universegame.cmm.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction


var database: Database? = null

fun initialize() {
    database = Database.connect(mysqlUrl, driver = "com.mysql.jdbc.Driver", user = mysqlUser, password = mysqlPwd)

    transaction {
        //addLogger(StdOutSqlLogger)
        SchemaUtils.create(cmmInfoTable, commandListTable, devicesTable, installedModulesTable)

        updateCMMInfoTable()
        initModuleTables()

        //update new commands
        commands.values().forEach {
            val enum: commands = it
            if (commandListTable.select { commandListTable.command eq it.name }.count() == 0L)
                commandListTable.insert {
                    it[command] = enum.name
                    it[method] = enum.method
                    it[enabled] = true
                }
        }
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