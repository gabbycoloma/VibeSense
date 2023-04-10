package com.s12.mobdeve.coloma.caballero.vibesense

import java.util.Date

data class Mood(
    var emoji : String? = null,
    var name : String? = null,
    var desc : String? = null,
    var date : String? = null, //temporary data type
    var rate: Int? = null,
    var userID: String ? = null
)