package com.s12.mobdeve.coloma.caballero.vibesense

data class Mood(
    var id: String? = null,
    var emoji: String? = null,
    var name: String? = null,
    var desc: String? = null,
    var date: String? = null, //temporary data type
    var rate: Int? = null,
    var userID: String? = null,
)