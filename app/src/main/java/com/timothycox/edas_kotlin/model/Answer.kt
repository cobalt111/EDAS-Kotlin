package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Answer(
    var id: Int? = 0,
    var answer: Int? = null
) : Serializable {

}