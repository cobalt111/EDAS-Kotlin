package com.timothycox.edas_kotlin.information

import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.util.Firebase

internal class InformationPresenter(private val view: InformationContract.View, private val examinee: Examinee) :
    InformationContract.Presenter {

    //todo remove tag
    private val TAG = "InformationPresenter"
    private val firebase: Firebase

    init {
        firebase = Firebase.instance
    }

    override fun create() {

    }

    override fun getTutorialState() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee.creatorUid!!)
            .child("tutorials")
            .child("seenInformation")
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
        //todo finish creating tutorial
    }

    override fun retryTutorial() {
        // todo finish creating tutorial
    }
}
