package com.timothycox.edas_kotlin.main

import android.os.Bundle
import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.Firebase

internal class MainPresenter(private val view: MainContract.View, private val user: User?) : MainContract.Presenter {

    //todo remove tag
    private val TAG = "MainPresenter"
    private val firebase: Firebase = Firebase.instance

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle"
    override fun create() {
        getTutorialState()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tutorial"
    override fun getTutorialState() {
        val databaseReference = firebase
            .databaseReference
            .child("server")
            .child("users")
            .child(user?.uid!!)
            .child("tutorials")
            .child("seenMain")

        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                if (!(dataSnapshot.value as Boolean))
                    view.showTutorial(false)
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
            .child(user?.uid!!)
            .child("tutorials")
            .child("seenMain")
        databaseReference.setValue(true)
    }

    override fun retryTutorial() {
        view.showTutorial(true)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="UI Events"
    override fun onTests() {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        view.navigateToTests(bundle)
    }

    override fun onPreviousAssessments() {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        view.navigateToPreviousAssessments(bundle)
    }
    //</editor-fold>
}
