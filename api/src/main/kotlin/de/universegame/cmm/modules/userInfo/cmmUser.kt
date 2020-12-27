package de.universegame.cmm.modules.userInfo

import de.universegame.cmm.*
import de.universegame.cmm.database.config
import de.universegame.cmm.database.loginSecretsTable
import de.universegame.cmm.database.sessionsTable
import de.universegame.cmm.database.usersTable
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.Status.Companion.OK
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class CMMUser {
    var username: String = ""
    var mail: String = ""
    var uuid: String = ""
    var verified: Boolean = false

    constructor(username: String, mail: String, uuid: String, verified: Boolean) {
        this.username = username
        this.mail = mail
        this.uuid = uuid
        this.verified = verified
    }

    constructor(uuid: String) {
        transaction {
            var it = usersTable.select { usersTable.uuid eq uuid }.single()
            username = it[usersTable.username]
            mail = it[usersTable.mail]
            verified = it[usersTable.verified]
        }
    }

    fun isEmpty(): Boolean {
        return uuid.isEmpty()
    }
}

fun getJSONCMMUser(uuid: String): CMMUser {
    return CMMUser(uuid)
}

fun createUserResponse(request: Request): Response {
    var username = request.query("username") ?: return Response(missingQuery)
    var mail = request.query("mail") ?: return Response(missingQuery)
    var pwd = request.query("pwd") ?: return Response(missingQuery)
    var uuid = generateUUID(config.UUIDLength, 5)
    var loginSecret = generateUUID(10, 11)
    transaction {
        usersTable.insert {
            it[usersTable.username] = username
            it[usersTable.mail] = mail
            it[usersTable.pwd] = pwd
            it[usersTable.uuid] = uuid
            it[usersTable.verified] = false
        }
        loginSecretsTable.insert {
            it[userUUID] = uuid
            it[created] = LocalDateTime.now()
            it[secret] = loginSecret
        }
    }
    sendEMail(
        mailConfig.mailFrom, mail, "CMM Verification",
        "Click link to verify: $serverPrefix://$serverAdress:$serverPort/api/user/verify?loginSecret=$loginSecret"
    )
    return Response(Status.CREATED).body("User created, view your emails to verify and login")
}

fun verfiyUserResponse(request: Request): Response {
    var loginSecret = request.query("loginSecret") ?: return Response(missingQuery)
    var updated = false
    var username = ""
    transaction {
        var query = loginSecretsTable.select { loginSecretsTable.secret eq loginSecret }
        if (query.count() != 0L) {
            var userUUID = query.single()[loginSecretsTable.userUUID]
            username = usersTable.select({ usersTable.uuid eq userUUID }).single()[usersTable.username]
            loginSecretsTable.deleteWhere { loginSecretsTable.secret eq loginSecret }
            usersTable.update({ usersTable.uuid eq userUUID }) {
                it[usersTable.verified] = true
            }
            updated = true
        }
    }
    return Response(if (updated) OK else Status.PRECONDITION_FAILED).body("User $username was successfully verified, continue to login")
}

fun loginUserResponse(request: Request): Response {
    var username = request.query("username") ?: return Response(missingQuery)
    var pwd = request.query("pwd") ?: return Response(missingQuery)
    var code = OK
    var errorString = ""
    var sessionToken = ""
    var userUUID = ""
    var mail = ""
    transaction {
        var usersQuery = usersTable.select { usersTable.username eq username }
        if (usersQuery.count() == 1L) {
            userUUID = usersQuery.single()[usersTable.uuid]
            mail = usersQuery.single()[usersTable.mail]
            if (usersQuery.single()[usersTable.pwd] == pwd) {
                sessionToken = createSessionToken()
                sessionsTable.insert {
                    it[uuid] = userUUID
                    it[sessionID] = sessionToken
                    it[created] = LocalDateTime.now()
                    it[expiration] = LocalDateTime.now().plusDays(cookieExpirationInDays)
                }
            } else code = Status.UNAUTHORIZED
            sessionsTable.deleteWhere { sessionsTable.uuid eq userUUID and (sessionsTable.expiration less LocalDateTime.now()) }
        } else code = inDatabaseNotFound
    }
    if (code != OK)
        return Response(code).body(errorString)
    else
        return Response(code).cookie(Cookie("cmmJWT", generateJWT(userUUID, username, mail, sessionToken)))
}