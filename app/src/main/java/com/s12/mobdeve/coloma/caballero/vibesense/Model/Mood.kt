package com.s12.mobdeve.coloma.caballero.vibesense.Model

data class Mood(
    var id: String? = null,
    var emoji: String? = null,
    var name: String? = null,
    var description: String? = null,
    var date: String? = null, //temporary data type
    var rate: Int? = null,
    var userID: String? = null,
)