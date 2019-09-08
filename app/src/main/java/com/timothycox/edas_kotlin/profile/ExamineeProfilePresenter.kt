package com.timothycox.edas_kotlin.profile

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.util.Firebase

internal class ExamineeProfilePresenter(
    private val view: ExamineeProfileContract.View,
    private val examinee: Examinee) : ExamineeProfileContract.Presenter {

    //todo remove tag
    private val TAG = "ExamineePP"
    private val firebase: Firebase = Firebase.instance

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle">
    override fun create() {

        getTutorialState()
        view.configureRecyclerView()
        view.populateUIWithData(examinee)

        val databaseReference = firebase
            .databaseReference
            .child("server")
            .child("users")
            .child(examinee.creatorUid!!)
            .child("examinees")
            .child(examinee.name!!)
            .child("assessments")

        firebase.access(true, databaseReference, object : Firebase.OnGetDataListener {

            override fun onSuccess(dataSnapshot: DataSnapshot) {
                //todo fix
//                val assessments = ArrayList<Assessment>()
//                dataSnapshot.children.forEach {
//                    val assessment = Assessment(
//                        it.child("category").getValue(String::class.java),
//                        examinee.name,
//                        it.child("timestamp").getValue(String::class.java),
//                        it.child("isCompleted").getValue(Boolean::class.java)!!,
//                        it.child("result").getValue(Int::class.java)!!
//                    )
//                    assessments.add(assessment)
//                }
//                view.setRecyclerViewAdapter(ExamineeProfileRecyclerViewAdapter(assessments))
            }

            override fun onFailure(databaseError: DatabaseError) {
                //todo handle failure
                Log.d(TAG, databaseError.message)
            }
        })
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tutorial">
    // todo test tutorial, retrieval of creatoruid from db
    override fun getTutorialState() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee.creatorUid!!)
            .child("tutorials")
            .child("seenProfile")
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
            .child("seenProfile")
        databaseReference.setValue(true)
    }

    override fun retryTutorial() {
        view.showTutorial(true)
    }
    //</editor-fold>
}
