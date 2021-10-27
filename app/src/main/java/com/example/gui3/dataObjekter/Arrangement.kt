package com.example.gui3.dataObjekter

import java.sql.Timestamp

data class Arrangement(
    val tittel: String,
    val sted: String,
    val timestamp: String, // TODO endre denne til et timestamp av noe slag
    val plasser: Int,
    val bildeUrl: String? = null
)
