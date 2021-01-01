package de.universegame.cmm

import kotlin.random.Random
import kotlin.random.nextInt

val avialableChars = "abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOPQRSTUV0123456789".toCharArray()

fun generateUUID(length: Int, separateAfter: Int): String {
    var uuid: StringBuilder = StringBuilder()
    var numDashes = length / separateAfter
    for (i in 0..(length - numDashes)) {
        uuid.append(avialableChars[Random.nextInt(IntRange(0, avialableChars.size - 1))])
        if (i % separateAfter == 0 && i != 0 && i != length && uuid.get(i - 1) != '-') uuid.append("-")
    }
    return uuid.toString()
}

fun generateClientSecret(): String {
    return generateUUID(config.dbConfig.clientSecretLength, config.dbConfig.clientSecretLength)
}

fun createSessionToken(): String {
    return generateUUID(config.dbConfig.UUIDLength, config.dbConfig.UUIDLength)
}

fun createLoginSecret(): String {
    return generateUUID(config.dbConfig.UUIDLength, 10)
}