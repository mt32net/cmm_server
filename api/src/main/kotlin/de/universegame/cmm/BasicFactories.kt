package de.universegame.cmm

import de.universegame.cmm.database.cmmInfoTable
import de.universegame.cmm.database.commandListTable
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.installedModulesTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun getJSONCMMInfo(): CMMInfo {
    var version = ""
    var cmmMinClientModuleVersions = ""
    var moduleListURL = ""
    var cmds: MutableList<String> = mutableListOf<String>()
    transaction {
        version = cmmInfoTable.select { cmmInfoTable.key eq "version" }.single()[cmmInfoTable.value]
        cmmMinClientModuleVersions =
            cmmInfoTable.select { cmmInfoTable.key eq "cmmMinClientModuleVersions" }.single()[cmmInfoTable.value]
        moduleListURL = cmmInfoTable.select { cmmInfoTable.key eq "moduleListURL" }.single()[cmmInfoTable.value]
        commandListTable.select { commandListTable.enabled eq true }.toList().forEach {
            cmds.add("${it[commandListTable.method]}_${it[commandListTable.command]}")
        }
    }
    return CMMInfo(
        version = version,
        moduleListURL = moduleListURL,
        cmmMinClientModuleVersions = cmmMinClientModuleVersions,
        cmds = cmds
    )
}

fun getJSONCMMDeviceList(): CMMDeviceList {
    var deviceList: MutableList<CMMDevice> = mutableListOf<CMMDevice>()
    transaction {
        devicesTable.selectAll().toList().forEach {
            deviceList.add(getJSONCMMDevice(it))
        }
    }
    return CMMDeviceList(deviceList)
}

private fun getJSONCMMDevice(it: ResultRow): CMMDevice {
    var name = ""
    var uuid = ""
    var modules = mutableListOf<CMMModule>()
    var online = false
    var lastUpdated = "1-1-1970 00:00"
    transaction {
        name = it[devicesTable.name]
        uuid = it[devicesTable.uuid]
        var moduleRows = installedModulesTable.select { installedModulesTable.uuid eq uuid }.toList()
        moduleRows.forEach {
            modules.add(CMMModule(name = it[installedModulesTable.module], version = it[installedModulesTable.version]))
        }
        online = it[devicesTable.online]
        lastUpdated = it[devicesTable.lastUpdated].format(dateTimeFormatter)
    }
    return CMMDevice(
        name = name,
        uuid = uuid,
        modules = modules,
        online = online,
        lastUpdated = lastUpdated
    )
}

fun getJSONCMMDevice(uuid: String): CMMDevice? {
    var device: CMMDevice? = null
    transaction {
        device = getJSONCMMDevice(devicesTable.select { devicesTable.uuid eq uuid }.single())
    }
    return device
}

fun getJSONCMMForgottenUUID(clientSecret: String):CMMForgottenUUID{
    var uuid = ""
    transaction{
        uuid = devicesTable.select{devicesTable.clientSecret eq clientSecret}.single()[devicesTable.uuid]
    }
    return CMMForgottenUUID(uuid)
}