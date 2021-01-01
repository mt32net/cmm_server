package de.universegame.cmm.modules.userInfo

import de.universegame.cmm.*
import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.loginSecretsTable
import de.universegame.cmm.database.sessionsTable
import de.universegame.cmm.database.usersTable
import de.universegame.cmm.modules.forgottenUUID.CMMForgottenUUID
import org.http4k.core.*
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
            val it = usersTable.select { usersTable.uuid eq uuid }.single()
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
    val username = request.query("username") ?: return Response(config.httpResponses.missingQuery.toStatus())
    val mail = request.query("mail") ?: return Response(config.httpResponses.missingQuery.toStatus())
    val pwd = request.query("pwd") ?: return Response(config.httpResponses.missingQuery.toStatus())
    val uuid = generateUUID(config.dbConfig.UUIDLength, 5)
    val loginSecret = generateUUID(10, 11)
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
        config.mailConfig.mailFrom, mail, "CMM Verification",
        "Click link to verify: ${config.serverConfig.serverPrefix}://${config.serverConfig.serverAdress}/#/login?verify=$loginSecret \n " +
                "If the server is not running, access the api directly: ${config.serverConfig.serverPrefix}://${config.serverConfig.serverAdress}/api/user/verify?loginSecret=$loginSecret"
    )
    return Response(Status.CREATED).body("User created, check your emails to verify and login")
}

fun verfiyUserResponse(request: Request): Response {
    val loginSecret = request.query("loginSecret") ?: return Response(config.httpResponses.missingQuery.toStatus())
    var updated = false
    var username = ""
    transaction {
        var query = loginSecretsTable.select { loginSecretsTable.secret eq loginSecret }
        if (query.count() != 0L) {
            var userUUID = query.single()[loginSecretsTable.userUUID]
            username = usersTable.select { usersTable.uuid eq userUUID }.single()[usersTable.username]
            loginSecretsTable.deleteWhere { loginSecretsTable.secret eq loginSecret }
            usersTable.update({ usersTable.uuid eq userUUID }) {
                it[usersTable.verified] = true
            }
            updated = true
        }
    }
    return if(updated)
        Response(OK).body("User $username was successfully verified, continue to login")
    else Response(Status.PRECONDITION_FAILED).body("loginsecret nonexistent")
}

fun loginUserResponse(request: Request): Response {
    val username = request.query("username") ?: return Response(config.httpResponses.missingQuery.toStatus())
    val pwd = request.query("pwd") ?: return Response(config.httpResponses.missingQuery.toStatus())
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
                    it[expiration] = LocalDateTime.now().plusDays(config.cookieExpirationInDays)
                }
            } else {
                code = Status.UNAUTHORIZED
                errorString = "username or password wrong"
            }
            sessionsTable.deleteWhere { sessionsTable.uuid eq userUUID and (sessionsTable.expiration less LocalDateTime.now()) }
        } else {
            code = config.httpResponses.inDatabaseNotFound.toStatus()
            errorString = "nonexistent user"
        }
    }
    return if (code != OK)
        Response(code).body(errorString)
    else
        Response(code).cookie(Cookie("cmmJWT", generateJWT(userUUID, username, mail, sessionToken), path = "/"))
            .body("User logged in, cookie was set")
}

fun getUUIDResponse(request: Request): Response {
    var username = request.query("username") ?: return Response(config.httpResponses.missingQuery.toStatus())
    var uuidString = ""
    transaction {
        uuidString = usersTable.select { usersTable.username eq username }.single()[usersTable.uuid]
    }
    var uuid = CMMForgottenUUID(uuidString)
    if (uuidString.isEmpty())
        return Response(config.httpResponses.inDatabaseNotFound.toStatus()).body("username not found")
    else
        return Response(Status.OK).with(
            Body.auto<CMMForgottenUUID>()
                .toLens() of uuid
        )
}