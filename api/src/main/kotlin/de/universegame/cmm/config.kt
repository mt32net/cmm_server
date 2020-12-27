package de.universegame.cmm

import org.http4k.core.Status
import java.time.format.DateTimeFormatter

val cmmServerVersion = "v0.1"
val cmmModuleListURL = "https://github.com/mt32net/cmm_server/modules.csv"
val cmmMinClientModuleVersions = "v0.1"

var dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

val inDatabaseNotFound = Status.PRECONDITION_FAILED
val missingQuery = Status.BAD_REQUEST

object mailConfig {
    val mailServerURL = externalMailConfig.mailServerURL
    val mailServerUsername = externalMailConfig.mailServerUsername
    val mailServerUserPwd = externalMailConfig.mailServerUserPwd
    val mailFrom = externalMailConfig.mailFrom
    val mailServerPort = 465
}

val serverPrefix = "http"
val serverAdress = "localhost"
val serverPort = 9000

val cookieExpirationInDays = 30L

val secretJWTString = "aslkfdgj304rigjsokfj0wo3p45ktjsdlfoigjhwo39nslkfikgkupoidrjg"
