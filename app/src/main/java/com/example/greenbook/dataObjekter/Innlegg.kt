package com.example.greenbook.dataObjekter

data class Innlegg(
    val innleggId: String ?= null,
    val innlegg_skriver: String ?= null,
    val innlegg_beskrivelse: String ?= null,
    val bildeURL: String ?= null
)
