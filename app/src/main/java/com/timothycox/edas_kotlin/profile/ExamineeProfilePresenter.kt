package com.timothycox.edas_kotlin.profile

import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.model.Response
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
                                .child("responses")

        firebase.access(true, databaseReference, object : Firebase.OnGetDataListener {

            override fun onSuccess(dataSnapshot: DataSnapshot) {

                val responses = ArrayList<Response>()

                dataSnapshot.children.forEach {
                    val response = Response(
                        it.child("category").value as String,
                        examinee.name,
                        it.child("timestamp").value as String
                    )
                    response.isCompleted = it.child("isCompleted").value as Boolean
                    response.result = it.child("result").value as Double
                    responses.add(response)
                }

                view.setRecyclerViewAdapter(ExamineeProfileRecyclerViewAdapter(responses))
            }

            override fun onFailure(databaseError: DatabaseError) {
                //todo handle failure
                Log.d(TAG, databaseError.message)
            }
        })
    }
    //</editor-fold>

    override fun onPreviousResponseSelected(response: Response?) {
        val bundle = Bundle()
        view.navigateToResponse(bundle)
    }

    override fun onTakeNewTestSelected() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
