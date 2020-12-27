package de.universegame.cmm.modules.deviceInfo

import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.installedModulesTable
import de.universegame.cmm.modules.CMMModule
import org.http4k.core.Status
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

data class CMMDeviceList(val deviceList: List<CMMDevice>)

data class CMMDevice(
    val uuid: String,
    val mac: String,
    val name: String,
    val user: String,
    val verified: Boolean,
    val online: Boolean,
    val modules: List<CMMModule>
)

fun getJSONCMMDevice(it: ResultRow): CMMDevice {
    var uuid = ""
    var mac = ""
    var name = ""
    var user = ""
    var verified = false
    var modules = mutableListOf<CMMModule>()
    var online = false
    transaction {
        name = it[devicesTable.name]
        uuid = it[devicesTable.uuid]
        var moduleRows = installedModulesTable.select { installedModulesTable.uuid eq uuid }.toList()
        moduleRows.forEach {
            modules.add(CMMModule(name = it[installedModulesTable.module], version = it[installedModulesTable.version]))
        }
        mac = it[devicesTable.mac]
        user = it[devicesTable.user]
        verified = it[devicesTable.verified]
        online = it[devicesTable.online]
    }
    return CMMDevice(
        uuid = uuid,
        mac = mac,
        name = name,
        user = user,
        verified = verified,
        online = online,
        modules = modules
    )
}

fun getJSONCMMDevice(uuid: String): CMMDevice {
    var device: CMMDevice = CMMDevice("", "", "", "", false, false, listOf<CMMModule>())
    transaction {
        device = getJSONCMMDevice(devicesTable.select { devicesTable.uuid eq uuid }.single())
    }
    return device
}

fun updateDeviceInfo(uuid: String?, name: String?, online: Boolean): Status {
    var status: Status = Status.OK
    if (uuid == null) return Status.PRECONDITION_FAILED
    transaction {
        if (devicesTable.select { devicesTable.uuid eq uuid }.count() == 0L) {
            status = Status.NOT_FOUND
        } else {
            devicesTable.update({ devicesTable.uuid eq uuid }) {
                if (name != null) it[devicesTable.name] = name
                it[devicesTable.online] = online
            }
        }
    }
    return status
}