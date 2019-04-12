package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Response(
    var category: String? = null,
    var isCompleted: Boolean = false,
    var answers: List<Answer>? = null,
    var result: Double,
    var timestamp: Double,
    var examineeName: String? = null
) : Serializable {
}