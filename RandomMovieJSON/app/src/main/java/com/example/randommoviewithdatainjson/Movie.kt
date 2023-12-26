package com.example.randommoviewithdatainjson

data class Movie(val name: String, val year: Int, val rating: Float, val critics_rating: Float, val language: List<String>, val viewing_area: List<String>)