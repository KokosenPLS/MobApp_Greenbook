package com.example.gui3.dataObjekter

data class Profil(
    val brukerID: String? = null,
    val fornavn: String,
    val etternavn: String,
    val email: String,
    val fdato: String,
    val imgUrl: String? = null
)
