package de.universegame.cmm.modules.forgottenUUID

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.devicesTable
import org.http4k.core.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class CMMForgottenUUID(val uuid: String)

fun getJSONCMMForgottenUUID(clientSecret: String): CMMForgottenUUID {
    var uuid = ""
    transaction {
        uuid = devicesTable.select { devicesTable.clientSecret eq clientSecret }.single()[devicesTable.uuid]
    }
    return CMMForgottenUUID(uuid)
}

fun getJSONCMMForgottenUUID(request: Request): Response {
    return Response(Status.OK).with(
        Body.auto<CMMForgottenUUID>()
            .toLens() of getJSONCMMForgottenUUID(clientSecret = request.query("clientsecret") ?: "")
    )
}