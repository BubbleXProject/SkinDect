package com.dicoding.capstone.skindect.data.model

data class HistoryResponse(
    var id: String = "",
    val image_url: String = "",
    val diagnosis: String = "",
    val recommendation: String = "",
    val timestamp: Long = 0L
)
