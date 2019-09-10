package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Assessment(
    var category: String? = null,
    var examineeName: String? = null,
    var timestamp: String? = null

) : Serializable {

    var questions = HashMap<String, MutableList<Question?>>()
    var isCompleted: Boolean? = false
    var result: Double? = -1.0
}
