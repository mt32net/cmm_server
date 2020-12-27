package de.universegame.cmm.modules.register

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.config
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.loginSecretsTable
import de.universegame.cmm.generateClientSecret
import de.universegame.cmm.generateUUID
import de.universegame.cmm.inDatabaseNotFound
import org.http4k.core.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class CMMDeviceRegistered(val uuid: String, val clientSecret: String)

fun registerDevice(name: String, mac: String, loginSecret: String): CMMDeviceRegistered {
    var cancel = false
    transaction {
        if (loginSecretsTable.select { loginSecretsTable.secret eq loginSecret }.count() != 1L)
            cancel = true
    }
    if (cancel)
        return CMMDeviceRegistered("", "")
    var uuid = generateUUID(config.UUIDLength, 10)
    var clientsecret = generateClientSecret()

    transaction {
        while (devicesTable.select { devicesTable.uuid eq uuid }.count() != 0L)
            uuid = generateUUID(config.UUIDLength, 10)
        var userUUID =
            loginSecretsTable.select { loginSecretsTable.secret eq loginSecret }.single()[loginSecretsTable.userUUID]
        devicesTable.insert {
            it[devicesTable.uuid] = uuid
            it[clientSecret] = clientsecret
            it[devicesTable.name] = name
            it[online] = true
            it[devicesTable.verified] = false
            it[devicesTable.mac] = mac
            it[devicesTable.user] = userUUID
        }
    }
    return CMMDeviceRegistered(uuid = uuid, clientSecret = clientsecret)
}

fun registerDevice(request: Request): Response {
    var name = request.query("name") ?: return Response(inDatabaseNotFound)
    var mac = request.query("mac") ?: return Response(inDatabaseNotFound)
    var loginSecret = request.query("loginSecret") ?: return Response(inDatabaseNotFound)
    return Response(Status.CREATED).with(
        Body.auto<CMMDeviceRegistered>().toLens() of registerDevice(
            name = name,
            mac = mac,
            loginSecret = loginSecret
        )
    )
}