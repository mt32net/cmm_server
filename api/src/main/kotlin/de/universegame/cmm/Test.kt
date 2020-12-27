package de.universegame.cmm

import com.fasterxml.jackson.annotation.JsonIgnore

class Test {
    @JsonIgnore
    var hallo = "blub"
    var testi = "gug"

    fun test(){
        println("test")
    }
}