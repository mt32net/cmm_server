package de.universegame.cmm.database

object config {
    val mysqlUser: String = "cmm"
    val mysqlPwd: String = "123456789"
    val mysqlUrl: String = "jdbc:mysql://localhost:3306/cmm?autoReconnect=true&useSSL=false"

    val UUIDLength = 200
    val clientSecretLength = 500
    val clientNameMaxLength = 200

    val userNameMaxLength = 50
    val userMailMaxLength = 100

    val macLength = 20

    val maxProcessNameLenght = 100

    val versionMaxLength = 50
}