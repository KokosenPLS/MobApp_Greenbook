package com.example.greenbook.dataObjekter

data class Arrangement(
    val arrangementId: String ?= null,
    val arrangør: String ?= null,
    val tittel: String ?= null,
    val beskrivelse: String ?= null,
    val sted: String ?= null,
    val dato: String? = null,
    val tid: String ?= null,
    val plasser: Int ?= null,
    val bildeUrl: String? = null
)
