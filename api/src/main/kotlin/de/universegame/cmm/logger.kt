package de.universegame.cmm

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun log(line:String){
    logger.log(line)
}

object logger {
    private var initialized: Boolean = false
    private lateinit var file: File
    private var fileDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm")
    private var logDateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/mm/yy")

    fun init(){
        val dateString = LocalDateTime.now().format(fileDateTimeFormatter)
        init("logs/$dateString.log")
    }

    fun init(filename: String){
        file = File(filename)
        if(!file.exists()) {
            file.createNewFile()
        }
        initialized = true
    }

    private fun getDTString(): String{
        return LocalDateTime.now().format(logDateTimeFormatter)
    }

    fun log(line: String){
        val text = "${getDTString()}: $line\n"
        if(initialized){
            file.appendText(text)
        }
        println(text)
    }


}