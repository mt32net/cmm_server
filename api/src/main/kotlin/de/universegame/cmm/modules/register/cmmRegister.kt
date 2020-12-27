package de.universegame.cmm.modules.register

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.config
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.generateClientSecret
import de.universegame.cmm.generateUUID
import org.http4k.core.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class CMMDeviceRegistered(val uuid: String, val clientSecret: String)

fun registerDevice(name: String, mac: String): CMMDeviceRegistered {
    var uuid = generateUUID(config.UUIDLength, 10)
    var clientsecret = generateClientSecret()
    transaction {
        while (devicesTable.select { devicesTable.uuid eq uuid }.count() != 0L)
            uuid = generateUUID(config.UUIDLength, 10)
        devicesTable.insert {
            it[devicesTable.uuid] = uuid
            it[clientSecret] = clientsecret
            it[devicesTable.name] = name
            it[online] = true
        }
    }
    return CMMDeviceRegistered(uuid = uuid, clientSecret = clientsecret)
}

fun registerDevice(request: Request): Response {
    return Response(Status.OK).with(
        Body.auto<CMMDeviceRegistered>().toLens() of registerDevice(
            name = request.query("name") ?: "",
            mac = request.query("mac") ?: ""
        )
    )
}