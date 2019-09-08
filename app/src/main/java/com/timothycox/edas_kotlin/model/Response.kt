package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Response(
    var category: String? = null,
    var timestamp: String?,
    var examineeName: String? = null
) : Serializable {
    var result: Double? = 0.0
    var answers: MutableList<MutableList<Answer>>? = null
    var isCompleted: Boolean? = false
}