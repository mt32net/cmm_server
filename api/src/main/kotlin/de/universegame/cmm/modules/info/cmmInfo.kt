package de.universegame.cmm.modules.info

import de.universegame.cmm.CMMInfoJackson.auto
import de.universegame.cmm.database.cmmInfoTable
import de.universegame.cmm.database.config
import org.http4k.core.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class CMMInfo(
    val version: String,
    val moduleListURL: String,
    val cmmMinClientModuleVersions: String,
    val type: String = "kotlin",
    val lengthProperties : CMMLengthProperties = defaultCMMLengthProps
)

fun getJSONCMMInfo(): CMMInfo {
    var version = ""
    var moduleListURL = ""
    var cmmMinClientModuleVersions = ""
    transaction {
        version = cmmInfoTable.select { cmmInfoTable.key eq "version" }.single()[cmmInfoTable.value]
        cmmMinClientModuleVersions =
            cmmInfoTable.select { cmmInfoTable.key eq "cmmMinClientModuleVersions" }.single()[cmmInfoTable.value]
        moduleListURL = cmmInfoTable.select { cmmInfoTable.key eq "moduleListURL" }.single()[cmmInfoTable.value]
    }
    return CMMInfo(
        version = version,
        moduleListURL = moduleListURL,
        cmmMinClientModuleVersions = cmmMinClientModuleVersions
    )
}

fun getJSONCMMInfoResponse(request: Request): Response {
    return Response(Status.OK).with(Body.auto<CMMInfo>().toLens() of getJSONCMMInfo())
}

data class CMMLengthProperties(
    val UUIDLength: Int = config.UUIDLength,
    val clientSecretLength: Int = config.clientSecretLength,
    val clientNameMaxLength: Int = config.clientNameMaxLength,
    val userNameMaxLength : Int = config.userNameMaxLength,
    val userMailMaxLength: Int = config.userMailMaxLength,
    val macLength: Int = config.macLength,
    val maxProcessNameLength: Int = config.maxProcessNameLenght,
    val versionMaxLength: Int = config.versionMaxLength
)

val defaultCMMLengthProps: CMMLengthProperties = CMMLengthProperties()