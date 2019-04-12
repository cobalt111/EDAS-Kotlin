package com.timothycox.edas_kotlin.util

import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.timothycox.edas_kotlin.model.Question

import java.util.ArrayList

import android.support.constraint.Constraints.TAG

object DatabaseProvider {

    private val firebase = Firebase.instance
    private val currentDatabase: DataSnapshot? = null
    private var questionList: List<Question>? = null

    fun submit() {

    }

    fun getAssessment(category: String): List<Question>? {
        val query = firebase.databaseReference
            .child("server")
            .child("assessments")
            .child(category)
        firebase.access(false, query, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                questionList = parseDataSnapshotAsQuestionList(dataSnapshot)
            }

            override fun onFailure(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.details)
            }
        })
        return questionList
    }

    private fun parseDataSnapshotAsQuestionList(dataSnapshot: DataSnapshot): List<Question> {
        val list = ArrayList<Question>()
        val snapshotIterable = dataSnapshot.children
        for (questionEntry in snapshotIterable) {
            list.add(questionEntry.getValue(Question::class.java)!!)
        }
        return list
    }
}
