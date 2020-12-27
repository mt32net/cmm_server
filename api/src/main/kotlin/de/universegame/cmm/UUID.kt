package de.universegame.cmm

import de.universegame.cmm.database.clientSecretLength
import kotlin.random.Random
import kotlin.random.nextInt

val avialableChars = "abcdefghijklmnopqrstuvwABCDEFGHIJKLMNOPQRSTUV0123456789".toCharArray()

fun generateUUID(length: Int, separateAfter: Int): String {
    var uuid: StringBuilder = StringBuilder()
    var numDashes = length / separateAfter
    for (i in 0..(length - numDashes)) {
        uuid.append(avialableChars[Random.nextInt(IntRange(0, avialableChars.size - 1))])
    }
    var dashes = 0
    for (i in 0..length) {
        if ((i - dashes) % separateAfter == 0 && i != 0 && i != length && uuid.get(i - 1) != '-') {
            uuid.insert(i, "-")
            dashes++
        }
    }
    return uuid.toString()
}

fun generateClientSecret(): String {
    return generateUUID(clientSecretLength, clientSecretLength)
}