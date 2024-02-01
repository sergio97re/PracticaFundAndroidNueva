package com.example.practicafundandroidnueva

data class Heroe(
    val photo: String,
    val id: String,
    val favorite: Boolean,
    val description: String,
    val name: String,
    var totalHitPoints : Int = 100,
    var currentHitPoints : Int = 100,
    var isDead : Boolean = false
)