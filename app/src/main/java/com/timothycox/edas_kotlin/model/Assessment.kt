package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Assessment(
    var questions: List<List<Question>>? = null,
    var category: String? = null
) : Serializable
