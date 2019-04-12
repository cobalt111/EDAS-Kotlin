package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Answer(
    var id: Long = 0,
    var answer: Int? = null
) : Serializable {
    fun setId(id: Int) {
        this.id = id.toLong()
    }
}