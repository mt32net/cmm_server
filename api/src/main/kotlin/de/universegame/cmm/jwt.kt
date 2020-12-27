package de.universegame.cmm

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.sql.Date
import java.time.LocalDate


var key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretJWTString))

fun generateJWT(uuid: String, username: String, mail: String, sessionToken: String): String{
    var builder = Jwts.builder()
    builder.setSubject("CMM")
    builder.setExpiration(Date.valueOf(LocalDate.now().plusDays(cookieExpirationInDays)))
        .setId(sessionToken)
        .claim("uuid", uuid)
        .claim("username", username)
        .claim("mail", mail)
    builder.signWith(key)
    return builder.compact()
}