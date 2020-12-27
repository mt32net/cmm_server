package de.universegame.cmm.modules.userInfo

import de.universegame.cmm.database.usersTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class CMMUser(
    val username: String,
    val mail: String,
    val uuid: String,
    val verified: Boolean
)

fun getJSONCMMUser(uuid: String): CMMUser {
    var username = ""
    var mail = ""
    var verified = false
    transaction {
        var it = usersTable.select { usersTable.uuid eq uuid }.single()
        username = it[usersTable.username]
        mail = it[usersTable.mail]
        verified = it[usersTable.verified]
    }
    return CMMUser(username = username, mail = mail, uuid = uuid, verified = verified)
}