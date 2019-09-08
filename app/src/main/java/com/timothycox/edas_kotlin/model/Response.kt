package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Response(
    var category: String? = null,
    var examineeName: String? = null,
    var timestamp: String?

) : Serializable {

    var result: Double? = 0.0
    var answers: MutableList<MutableList<Answer>>? = null
    var isCompleted: Boolean? = false
}