package com.example.gui3.dataObjekter

data class Profil(
    val email: String,
    val fornavn: String,
    val etternavn: String,
    val fdato: String? = null,
    val imgUrl: String? = null
)
