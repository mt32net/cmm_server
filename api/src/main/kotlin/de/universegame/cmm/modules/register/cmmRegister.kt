package de.universegame.cmm.modules.register

import de.universegame.cmm.*
import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.config
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.database.loginSecretsTable
import de.universegame.cmm.database.usersTable
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
    var uuid = createLoginSecret()
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

fun registerDeviceResponse(request: Request): Response {
    var name = request.query("name") ?: return Response(missingQuery)
    var mac = request.query("mac") ?: return Response(missingQuery)
    var loginSecret = request.query("loginSecret") ?: return Response(missingQuery)
    var device = registerDevice(
        name = name,
        mac = mac,
        loginSecret = loginSecret
    )
    return if (!device.uuid.isEmpty()) Response(Status.CREATED).with(
        Body.auto<CMMDeviceRegistered>().toLens() of device
    ) else Response(
        inDatabaseNotFound
    )
}

fun requestRegisterDeviceResponse(request: Request): Response {
    var uuid = request.query("uuid")?:return Response(missingQuery)
    var loginSecret = createLoginSecret()
    var mail = ""
    transaction {
        mail = usersTable.select{usersTable.uuid eq uuid}.single()[usersTable.mail]
    }
    if(mail!= "") {
        sendEMail(mailConfig.mailFrom, mail, "CMM new device login secret", "here ist your login secret: $loginSecret")
        return Response(Status.OK)
    }
    else return Response(inDatabaseNotFound)
}