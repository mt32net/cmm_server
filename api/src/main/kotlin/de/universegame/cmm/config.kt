package de.universegame.cmm

import kotlinx.serialization.Serializable
import org.http4k.core.Status
import java.time.format.DateTimeFormatter

var config = Config()

var dateTimeFormatter = DateTimeFormatter.ofPattern(config.datetimePattern)

@Serializable
data class Config(
    val cmmServerVersion: String = "v0.1",
    val cmmModuleListURL: String = "https://github.com/mt32net/cmm_server/modules.csv",
    val cmmMinClientModuleVersions: String = "v0.1",

    val datetimePattern: String = "yyyy-MM-dd HH:mm:ss.SSS",

    val cookieExpirationInDays: Long = 30L,

    val secretJWTString: String = "aslkfdgj304rigjsokfj0wo3p45ktjsdlfoigjhwo39nslkfikgkupoidrjg",

    val serverConfig: ServerConfig = ServerConfig(),
    val httpResponses: HTTPResponsesConfig = HTTPResponsesConfig(),
    val mailConfig: MailConfig = MailConfig(),
    val dbConfig: DBConfig = DBConfig()
)

@Serializable
data class ServerConfig(
    val serverPrefix: String = "http",
    val serverAdress: String = "localhost",
    val serverPort: Int = 9000
)

@Serializable
data class HTTPResponsesConfig(
    @Serializable
    val inDatabaseNotFound: Int = Status.PRECONDITION_FAILED.code,
    val missingQuery: Int = Status.BAD_REQUEST.code
)

fun Int.toStatus(): Status {
    return Status(this, "cast")
}

@Serializable
data class MailConfig(
    val mailServerURL: String = "your mail server URL",
    val mailServerUsername: String ="your account on that mailserver",
    val mailServerUserPwd: String = "corresponding password to username",
    val mailFrom: String = "the mail-address, all mails should be sent from (reciever server (after at-symbol) has to be the same as your server URL",
    val mailServerPort: Int = 456
)

@Serializable
data class DBConfig(
    val mysqlUser: String = "cmm",
    val mysqlPwd: String = "123456789",
    val mysqlUrl: String = "jdbc:mysql://localhost:3306/cmm?autoReconnect=true&useSSL=false",

    val UUIDLength: Int = 200,
    val clientSecretLength: Int = 500,
    val clientNameMaxLength: Int = 200,

    val userNameMaxLength: Int = 50,
    val userMailMaxLength: Int = 100,

    val macLength: Int = 20,

    val maxProcessNameLenght: Int = 100,

    val versionMaxLength: Int = 50,
)
