package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Question(
    var id: Int? = null,
    var importance: String? = null,
    var questionText: String? = null,
    var category: String?
) : Serializable {

}
