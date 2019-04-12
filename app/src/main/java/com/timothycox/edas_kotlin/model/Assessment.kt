package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Assessment(
    var category: String? = null,
    var examineeName: String? = null,
    var timestamp: String? = null,
    var isCompleted: Boolean = false,
    var result: Int = 0
) : Serializable {
    var questions: List<Question>? = null
}
