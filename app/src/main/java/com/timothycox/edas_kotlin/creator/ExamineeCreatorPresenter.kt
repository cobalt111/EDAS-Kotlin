package com.timothycox.edas_kotlin.creator

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.Firebase

internal class ExamineeCreatorPresenter(private val view: ExamineeCreatorContract.View, private val user: User) :
    ExamineeCreatorContract.Presenter {

    //todo remove tag
    private val TAG = "ECP"
    private val firebase: Firebase = Firebase.instance

    override fun create() {
        getTutorialState()
    }

    override fun getTutorialState() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(user.uid!!)
            .child("tutorials")
            .child("seenCreator")
        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                try {
                    if (!(dataSnapshot.getValue(Boolean::class.java))!!) view.showTutorial(false)
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }

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
            .child(user.uid!!)
            .child("tutorials")
            .child("seenCreator")
        databaseReference.setValue(true)
    }

    override fun retryTutorial() {
        view.showTutorial(true)
    }

    override fun onAddExaminee() {
        val bundle = view.saveEnteredExamineeData()

        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(user.uid!!)
            .child("examinees")
            .child(bundle.get("name")!!.toString())

        databaseReference.child("ageInMonths").setValue(bundle.get("ageInMonths"))
        databaseReference.child("name").setValue(bundle.get("name")!!.toString())
        databaseReference.child("gender").setValue(bundle.get("gender")!!.toString())

        view.navigateToAssessments(bundle)
    }
}
