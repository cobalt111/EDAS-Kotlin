package com.timothycox.edas_kotlin.model

import java.io.Serializable

// todo implement Parcelable?
data class User(var name: String?, var email: String?, var uid: String?) : Serializable
