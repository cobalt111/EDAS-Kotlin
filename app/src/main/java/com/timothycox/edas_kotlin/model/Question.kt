package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Question(
    var id: Long = 0,
    var importance: String? = null,
    var questionText: String? = null
) : Serializable {

    fun setId(id: Int) {
        this.id = id.toLong()
    }
}
