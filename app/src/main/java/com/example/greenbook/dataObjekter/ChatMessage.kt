package com.example.greenbook.dataObjekter

data class ChatMessage(
    val message: String ?= null,
    val sender: String ?= null,
    val reciever: String ?= null,
    val timestamp: String ?= null
    )
