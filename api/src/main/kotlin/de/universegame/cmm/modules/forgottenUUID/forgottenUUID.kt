package de.universegame.cmm.modules.forgottenUUID

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.config
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.log
import de.universegame.cmm.toStatus
import org.http4k.core.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class CMMForgottenUUID(val uuid: String)

fun getJSONCMMForgottenUUID(clientSecret: String): CMMForgottenUUID {
    var uuid = ""
    try {
        transaction {
            uuid = devicesTable.select { devicesTable.clientSecret eq clientSecret }.single()[devicesTable.uuid]
        }
        return CMMForgottenUUID(uuid)
    } catch (e: Exception) {
        log(e.stackTraceToString())
        return CMMForgottenUUID("")
    }
}

fun getJSONCMMForgottenUUID(request: Request): Response {
    var uuid = getJSONCMMForgottenUUID(clientSecret = request.query("clientsecret") ?: "")
    if (uuid.uuid.isEmpty()) return Response(config.httpResponses.inDatabaseNotFound.toStatus())
    return Response(Status.OK).with(
        Body.auto<CMMForgottenUUID>()
            .toLens() of uuid
    )
}