package de.universegame.cmm.modules.info

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.config
import de.universegame.cmm.database.cmmInfoTable
import de.universegame.cmm.database.devicesTable
import de.universegame.cmm.dateTimeFormatter
import de.universegame.cmm.log
import org.http4k.core.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.ZoneOffset

data class CMMInfo(
    val version: String,
    val moduleListURL: String,
    val cmmMinClientModuleVersions: String,
    val backendType: String = "kotlin",
    val connectedDevices: Number = 0L,
    val systemRunningSince: String = LocalDateTime.now().format(dateTimeFormatter),
    val systemRunningSinceUnix: Number = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
    val lengthProperties: CMMLengthProperties = defaultCMMLengthProps
)

fun getJSONCMMInfo(): CMMInfo {
    var version = ""
    var moduleListURL = ""
    var cmmMinClientModuleVersions = ""
    var connectedDevices = 0L
    var onlineSince = ""
    var onlineSinceUnix = 0L
    try {
        transaction {
            version = cmmInfoTable.select { cmmInfoTable.key eq "version" }.single()[cmmInfoTable.value]
            cmmMinClientModuleVersions =
                cmmInfoTable.select { cmmInfoTable.key eq "cmmMinClientModuleVersions" }.single()[cmmInfoTable.value]
            moduleListURL = cmmInfoTable.select { cmmInfoTable.key eq "moduleListURL" }.single()[cmmInfoTable.value]
            connectedDevices = devicesTable.selectAll().count()
            onlineSince = cmmInfoTable.select { cmmInfoTable.key eq "onlineSince" }.single()[cmmInfoTable.value]
            onlineSinceUnix =
                cmmInfoTable.select { cmmInfoTable.key eq "onlineSinceUnix" }.single()[cmmInfoTable.value].toLong()
        }
    } catch (e: Exception) {
        log(e.stackTraceToString())
    }
    return CMMInfo(
        version = version,
        moduleListURL = moduleListURL,
        cmmMinClientModuleVersions = cmmMinClientModuleVersions,
        connectedDevices = connectedDevices,
        systemRunningSince = onlineSince,
        systemRunningSinceUnix = onlineSinceUnix
    )
}

fun getJSONCMMInfoResponse(request: Request): Response {
    return Response(Status.OK).with(Body.auto<CMMInfo>().toLens() of getJSONCMMInfo())
}

data class CMMLengthProperties(
    val UUIDLength: Int = config.dbConfig.UUIDLength,
    val clientSecretLength: Int = config.dbConfig.clientSecretLength,
    val clientNameMaxLength: Int = config.dbConfig.clientNameMaxLength,
    val userNameMaxLength: Int = config.dbConfig.userNameMaxLength,
    val userMailMaxLength: Int = config.dbConfig.userMailMaxLength,
    val macLength: Int = config.dbConfig.macLength,
    val maxProcessNameLength: Int = config.dbConfig.maxProcessNameLenght,
    val versionMaxLength: Int = config.dbConfig.versionMaxLength
)

val defaultCMMLengthProps: CMMLengthProperties = CMMLengthProperties()