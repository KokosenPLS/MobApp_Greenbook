package com.example.greenbook.dataObjekter

data class Profil(
    var brukerId: String ?=null,
    val email: String ?= null,
    val fornavn: String ?= null,
    val etternavn: String ?= null,
    val fdato: String? = null,
    val imgUrl: String? = null,
    val bio:String? = null
)
