package com.timothycox.edas_kotlin.util

import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId

class Firebase private constructor() {
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference
    private val firebaseInstanceToken: String?

    init {
        firebaseDatabase.setPersistenceEnabled(true)
        databaseReference = firebaseDatabase.reference
        firebaseInstanceToken = FirebaseInstanceId.getInstance().token
    }

    fun access(continuousUpdating: Boolean, locationOfQuery: Query, listener: OnGetDataListener) {

        locationOfQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!continuousUpdating)
                    locationOfQuery.removeEventListener(this)
                listener.onSuccess(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                if (!continuousUpdating)
                    locationOfQuery.removeEventListener(this)
                listener.onFailure(databaseError)
            }
        })
        locationOfQuery.keepSynced(true)
    }

    interface OnGetDataListener {
        fun onSuccess(dataSnapshot: DataSnapshot)

        fun onFailure(databaseError: DatabaseError)
    }

    companion object {
        val instance = Firebase()
    }
}
