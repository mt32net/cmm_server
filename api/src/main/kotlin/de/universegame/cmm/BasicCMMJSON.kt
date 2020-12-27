package de.universegame.cmm

data class CMMInfo(
    val version: String,
    val moduleListURL: String,
    val cmmMinClientModuleVersions: String,
    val cmds: List<String>,
    val type: String = "kotlin"
)

data class CMMDeviceList(val deviceList: List<CMMDevice>)

data class CMMDevice(
    val name: String,
    val uuid: String,
    val online: Boolean,
    val lastUpdated: String,
    val modules: List<CMMModule>
)

data class CMMModule(val name: String, val version: String)

data class CMMForgottenUUID(val uuid: String)

data class CMMDeviceRegistered(val uuid: String, val clientSecret: String)