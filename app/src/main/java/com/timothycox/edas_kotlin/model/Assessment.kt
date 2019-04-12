package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Assessment(
    var questions: List<Question>? = null,
    var category: String? = null,
    var examineeName: String? = null,
    var timestamp: String? = null
) : Serializable {
    var isCompleted: Boolean = false
    var result: Int = 0


}
