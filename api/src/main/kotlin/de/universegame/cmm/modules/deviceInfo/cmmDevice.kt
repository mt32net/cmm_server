package de.universegame.cmm.modules.deviceInfo

import com.fasterxml.jackson.annotation.JsonIgnore
import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.installedModulesTable
import de.universegame.cmm.generateClientSecret
import de.universegame.cmm.inDatabaseNotFound
import de.universegame.cmm.modules.CMMModule
import org.http4k.core.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

data class CMMDeviceList(val deviceList: List<CMMDevice>)

class CMMDevice {
    var uuid: String = ""
        private set
    var mac: String = ""
        private set
    var name: String = ""
    var user: String = ""
        private set
    var verified: Boolean = false
    var online: Boolean = false
    var modules: MutableList<CMMModule> = mutableListOf()

    @JsonIgnore
    var clientSecret: String = ""
        private set

    constructor(uuid: String) : this(devicesTable.select { devicesTable.uuid eq uuid }.single())

    constructor(it: ResultRow) {
        transaction {
            name = it[devicesTable.name]
            uuid = it[devicesTable.uuid]
            var moduleRows = installedModulesTable.select { installedModulesTable.uuid eq uuid }.toList()
            moduleRows.forEach {
                modules.add(
                    CMMModule(
                        name = it[installedModulesTable.module],
                        version = it[installedModulesTable.version]
                    )
                )
            }
            mac = it[devicesTable.mac]
            user = it[devicesTable.user]
            verified = it[devicesTable.verified]
            online = it[devicesTable.online]
            clientSecret = it[devicesTable.clientSecret]
        }
    }

    constructor(
        uuid: String,
        mac: String,
        user: String,
        verified: Boolean,
        online: Boolean,
        modules: MutableList<CMMModule>
    ) {
        this.uuid = uuid
        this.mac = mac
        this.user = user
        this.verified = verified
        this.online = online
        this.modules = modules
        this.clientSecret = generateClientSecret()
    }

    fun saveToDatabase() {
        transaction {
            devicesTable.update({ devicesTable.uuid eq uuid }) {
                it[devicesTable.name] = name
                it[devicesTable.online] = online
                it[devicesTable.verified] = verified
            }

        }
    }

    fun isEmpty():Boolean{
        return uuid.isEmpty()
    }
}

fun getJSONCMMDevice(it: ResultRow): CMMDevice {
    return CMMDevice(it)
}

fun getJSONCMMDevice(uuid: String): CMMDevice {
    return CMMDevice(uuid)
}

fun getJSONCMMDeviceResponse(request: Request):Response{
    var uuid: String = request.query("uuid") ?: return Response(inDatabaseNotFound)
    return Response(Status.OK).with(
        Body.auto<CMMDevice>().toLens() of getJSONCMMDevice(uuid)
    )
}

fun updateDeviceInfo(uuid: String?, name: String?, online: Boolean): Status {
    var status: Status = Status.OK
    if (uuid == null) return inDatabaseNotFound
    transaction {
        if (devicesTable.select { devicesTable.uuid eq uuid }.count() == 0L) {
            status = inDatabaseNotFound
        } else {
            devicesTable.update({ devicesTable.uuid eq uuid }) {
                if (name != null) it[devicesTable.name] = name
                it[devicesTable.online] = online
            }
        }
    }
    return status
}

fun updateDeviceInfoResponse(request: Request):Response{
    return Response(
        updateDeviceInfo(
            uuid = request.query("uuid"),
            name = request.query("name"),
            online = request.query("online").toBoolean()
        )
    )
}