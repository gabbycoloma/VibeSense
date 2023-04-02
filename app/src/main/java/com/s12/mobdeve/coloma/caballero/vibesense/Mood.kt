package com.s12.mobdeve.coloma.caballero.vibesense

import java.util.Date

data class Mood(
    var emoji : Int,
    var name : String,
    var desc : String,
    var date : String, //temporary data type
    var rate: Int
)