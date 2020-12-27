package de.universegame.cmm

import de.universegame.cmm.database.clientUUIDLength
import de.universegame.cmm.database.devicesTable
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

fun registerDevice(name: String): CMMDeviceRegistered {
    var uuid = generateUUID(clientUUIDLength, 10)
    var clientsecret = generateClientSecret()
    transaction {
        while (devicesTable.select { devicesTable.uuid eq uuid }.count() != 0L)
            uuid = generateUUID(clientUUIDLength, 10)
        devicesTable.insert {
            it[devicesTable.uuid] = uuid
            it[devicesTable.clientSecret] = clientsecret
            it[devicesTable.name] = name
            it[devicesTable.online] = true
            it[devicesTable.lastUpdated] = LocalDateTime.now()
        }
    }
    return CMMDeviceRegistered(uuid = uuid, clientSecret = clientsecret)
}

fun updateDeviceInfo(uuid: String?, name: String?, online: Boolean): Status {
    var status: Status = OK
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