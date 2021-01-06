package de.universegame.cmm

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * load configuration from file **filename**
 * @return true if file exists, false if file was created
 * **/
fun loadConfig(filename: String): Boolean {
    var file = File(filename)
    log("Load config")
    if(!file.exists()) {
        log("Config does not exists")
        saveConfig(filename)
        return false;
    }
    var jsonData = file.readText()
    config = Json.decodeFromString(jsonData)
    log("Loaded Config succeessfully")
    return true
}

/**
 * save configuration to file **filename**
 * @return true
 * **/
fun saveConfig(filename: String) {
    var file = File(filename)
    if(file.createNewFile()) {
        log("Created new config file")
        val config: String = Json {
            encodeDefaults = true
            prettyPrint = true
        }.encodeToString(config)
        file.writeText(config)
        log("Saved config")
    }
}