package com.example.greenbook.dataObjekter

// TODO: 10/27/2021 legg til beskrivelse
data class Arrangement(
    val arrangør: String,
    val tittel: String,
    val beskrivelse: String,
    val sted: String,
    val dato: String? = null,
    val tid: String,
    val plasser: Int,
    val bildeUrl: String? = null
)
