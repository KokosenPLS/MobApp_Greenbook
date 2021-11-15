package com.example.greenbook.dataObjekter

data class Kommentar(
    val kommentarId: String ? = null,
    val innleggId: String ? = null,
    val brukerId: String ? = null,
    val kommentarTekst: String ? = null,
    val timestamp: String ? = null
)
