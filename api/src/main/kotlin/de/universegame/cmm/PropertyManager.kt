package de.universegame.cmm

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * load configuration from file **filename**
 * @return true
 * **/
fun loadConfig(filename: String): Boolean {
    var file = File(filename)
    if(!file.exists())
        saveConfig(filename)
    var jsonData = file.readText()
    config = Json.decodeFromString(jsonData)
    return true
}

/**
 * save configuration to file **filename**
 * @return true
 * **/
fun saveConfig(filename: String) {
    var file = File(filename)
    if(file.createNewFile()) {
        val config: String = Json {
            encodeDefaults = true
            prettyPrint = true
        }.encodeToString(config)
        file.writeText(config)
    }
}