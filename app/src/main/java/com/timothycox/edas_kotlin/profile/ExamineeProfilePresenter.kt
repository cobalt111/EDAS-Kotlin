package com.timothycox.edas_kotlin.profile

import android.os.Bundle
import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Answer
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.model.Response
import com.timothycox.edas_kotlin.util.Firebase

internal class ExamineeProfilePresenter(
    private val view: ExamineeProfileContract.View,
    private val examinee: Examinee
) : ExamineeProfileContract.Presenter {

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
                val responses = mutableListOf<Response>()
                dataSnapshot.children.forEach { responseSnapshot ->
                    val response = Response(
                        responseSnapshot.child("category").value as String,
                        responseSnapshot.child("timestamp").value as String,
                        examinee.name
                    )
                    response.isCompleted = responseSnapshot.child("isCompleted").value as Boolean

                    val categoryAnswers = mutableListOf<Answer>()
                    val commonAnswers = mutableListOf<Answer>()
                    var counter = 1
                    responseSnapshot.child("response").child(examinee.category!!).children.forEach {
                        categoryAnswers.add(Answer(counter, it.value as Int))
                        counter++
                    }
                    counter = 1
                    responseSnapshot.child("response").child("common").children.forEach {
                        commonAnswers.add(Answer(counter, (it.value as Long).toInt()))
                        counter++
                    }

                    response.answers = mutableListOf()
                    response.answers!!.add(mutableListOf())
                    response.answers!!.add(mutableListOf())
                    for (answer in categoryAnswers)
                        response.answers!![0].add(answer)
                    for (answer in commonAnswers)
                        response.answers!![1].add(answer)
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

    //<editor-fold defaultstate="collapsed" desc="Events">
    override fun onTakeTestSelected() {
        val bundle = Bundle()
        bundle.putSerializable("selectedExaminee", examinee)
        view.navigateToAssessment(bundle)
    }

    override fun onPreviousAssessmentSelected(response: Response?) {
        val bundle = Bundle()
        bundle.putSerializable("selectedExaminee", examinee)
        bundle.putSerializable("response", response)
        view.navigateToResult(bundle)
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
                if (!(dataSnapshot.value as Boolean) )
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
