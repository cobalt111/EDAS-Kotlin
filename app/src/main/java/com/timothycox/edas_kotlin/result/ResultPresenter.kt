package com.timothycox.edas_kotlin.result

import android.os.Bundle
import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.util.Firebase

internal class ResultPresenter(
    private val view: ResultContract.View,
    private val examinee: Examinee,
    private val assessment: Assessment
) : ResultContract.Presenter {

    //todo remove tag
    private val TAG = "ResultsPresenter"
    private val firebase: Firebase = Firebase.instance

    override fun create() {
        getTutorialState()
        view.populatedUIWithData(examinee, assessment)
    }

    override fun getTutorialState() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee.creatorUid!!)
            .child("tutorials")
            .child("seenResult")
        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                if (!(dataSnapshot.getValue(Boolean::class.java))!!) view.showTutorial(false)
            }

            override fun onFailure(databaseError: DatabaseError) {
                //todo handle failure
                Log.d(TAG, databaseError.message)
            }
        })
    }

    override fun onTutorialSeen() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee.creatorUid!!)
            .child("tutorials")
            .child("seenResult")
        databaseReference.setValue(true)
    }

    override fun retryTutorial() {
        view.showTutorial(true)
    }

    override fun onNewTest() {
        val bundle = Bundle()
        bundle.putSerializable("selectedExaminee", examinee)
        bundle.putSerializable("assessment", assessment)
        view.navigateToNewTest(bundle)
    }

    override fun onLearnMore() {
        val bundle = Bundle()
        bundle.putSerializable("selectedExaminee", examinee)
        bundle.putSerializable("assessment", assessment)
        view.navigateToLearnMore(bundle)
    }
}
