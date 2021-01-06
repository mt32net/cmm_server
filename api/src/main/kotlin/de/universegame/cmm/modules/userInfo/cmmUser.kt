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

/**
 * Creates an unverified user
 *
 * the request must have these queries: **username**, **mail**
 * @param request The http request from the user
 * @return Return a "standalone" HTTP Response, possible response codes: CREATED
 * **/
fun createUserResponse(request: Request): Response {
    val username = request.query("username") ?: return Response(config.httpResponses.missingQuery.toStatus())
    val mail = request.query("mail") ?: return Response(config.httpResponses.missingQuery.toStatus())
    val uuid = generateUUID(config.dbConfig.UUIDLength, 5)
    val verificationSecret = generateUUID(10, 11)
    try {
        transaction {
            usersTable.insert {
                it[usersTable.username] = username
                it[usersTable.mail] = mail
                it[usersTable.pwd] = "mail"
                it[usersTable.uuid] = uuid
                it[usersTable.verified] = false
            }
            loginSecretsTable.insert {
                it[userUUID] = uuid
                it[created] = LocalDateTime.now()
                it[secret] = verificationSecret
            }
        }
        sendEMail(
            config.mailConfig.mailFrom, mail, "CMM Verification",
            "Click link to verify: ${config.serverConfig.serverPrefix}://${config.serverConfig.serverAdress}/#/login?verify=$verificationSecret \n " +
                    "If the server is not running, access the api directly: ${config.serverConfig.serverPrefix}://${config.serverConfig.serverAdress}/api/user/verify?loginSecret=$verificationSecret"
        )
        return Response(Status.CREATED).body("User created, check your emails to verify and login")
    } catch (e: Exception) {
        log(e.stackTraceToString())
        return Response(Status.BAD_REQUEST)
    }
}

/**
 * Verifying user with **loginSecret** in query
 * @param request The http request from the user
 * @return Return a "standalone" HTTP Response, possible response codes: OK, PRECONDITION_FAILED
 * **/
fun verfiyUserResponse(request: Request): Response {
    //get loginsecret query
    val loginSecret = request.query("loginSecret") ?: return Response(config.httpResponses.missingQuery.toStatus())
    var updated = false
    var username = ""
    try {
        transaction {
            var query = loginSecretsTable.select { loginSecretsTable.secret eq loginSecret }
            //verify that loginSecret exists
            if (query.count() != 0L) {
                var userUUID = query.single()[loginSecretsTable.userUUID]
                username = usersTable.select { usersTable.uuid eq userUUID }.single()[usersTable.username]
                //delete loginSecret
                loginSecretsTable.deleteWhere { loginSecretsTable.secret eq loginSecret }
                //update user profile
                usersTable.update({ usersTable.uuid eq userUUID }) {
                    it[usersTable.verified] = true
                }
                updated = true
            }
        }
        return if (updated)
            Response(OK).body("User $username was successfully verified, continue to login")
        else Response(Status.PRECONDITION_FAILED).body("loginsecret nonexistent")
    } catch (e: Exception) {
        log(e.stackTraceToString())
        return Response(Status.BAD_REQUEST)
    }
}

/**
 * Login User:
 *
 * if query **username** exists, a loginToken will be sent in an e-mail,
 *
 * if query **token** exists and is valid, the user will be signed by a cookie as a Response,
 *
 * if neither is provided, the status code defined in **config.httpResponses.missingQuery** will be returned with an empty body
 * @param request The http request by the user
 * @return Return a "standalone" HTTP Response, possible response codes: OK, **config.httpResponses.missingQuery**
 * **/
fun loginUserResponse(request: Request): Response {
    val username = request.query("username")
    val token = request.query("token")
    if (username == null && token == null)
        return Response(config.httpResponses.missingQuery.toStatus())
    var response = Response(config.httpResponses.inDatabaseNotFound.toStatus())
    var errorString = ""
    var mail = ""
    try {
        transaction {
            if (token != null) {
                var userUUID = sessionsTable.select { sessionsTable.sessionID eq token }.single()[sessionsTable.uuid]
                var mysqlUsername = usersTable.select { usersTable.uuid eq userUUID }.single()[usersTable.username]
                var sessionToken = createSessionToken()
                sessionsTable.insert {
                    it[uuid] = userUUID
                    it[sessionID] = sessionToken
                    it[created] = LocalDateTime.now()
                    it[expiration] = LocalDateTime.now().plusDays(config.cookieExpirationInDays)
                    sessionsTable.deleteWhere { sessionsTable.uuid eq userUUID and (sessionsTable.expiration less LocalDateTime.now()) }
                    response = Response(OK).cookie(
                        Cookie(
                            "cmmJWT",
                            generateJWT(userUUID, mysqlUsername, mail, sessionToken),
                            path = "/"
                        )
                    )
                        .body("User logged in, cookie was set")
                }
            } else if (username != null) {
                var userUUID = usersTable.select { usersTable.username eq username }.single()[usersTable.uuid]
                val genToken = createLoginSecret()
                sessionsTable.insert {
                    it[sessionsTable.sessionID] = genToken
                    it[sessionsTable.uuid] = userUUID
                    it[sessionsTable.created] = LocalDateTime.now()
                    it[sessionsTable.expiration] = LocalDateTime.now().plusMinutes(30)
                }
                sendEMail(
                    config.mailConfig.mailFrom,
                    mail,
                    "Mail Login Secret",
                    "Click to login: ${config.serverConfig.serverPrefix}://${config.serverConfig.serverAdress}/#/login?token=$genToken"
                )
            } else {
                //if neither username nor token is defined, do nothing
            }
        }
        return response
    } catch (e: Exception) {
        log(e.stackTraceToString())
        return Response(Status.BAD_REQUEST)
    }
}

/**
 * Send the user's uuid, if **username** is in the query
 * @param request The http request from the user
 * @return Return a "standalone" HTTP Response, possible response codes: OK, PRECONDITION_FAILED
 * **/
fun getUUIDResponse(request: Request): Response {
    var username = request.query("username") ?: return Response(config.httpResponses.missingQuery.toStatus())
    var uuidString = ""
    try {
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
    } catch (e: Exception) {
        log(e.stackTraceToString())
        return Response(Status.BAD_REQUEST)
    }
}