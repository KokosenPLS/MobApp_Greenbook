package com.example.greenbook.dataObjekter

data class Profil(
    val email: String ?= null,
    val fornavn: String ?= null,
    val etternavn: String ?= null,
    val fdato: String? = null,
    val imgUrl: String? = null
)
