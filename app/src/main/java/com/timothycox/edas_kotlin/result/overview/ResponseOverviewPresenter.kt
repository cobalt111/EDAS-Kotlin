package com.timothycox.edas_kotlin.result.overview

import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.util.Firebase

class ResponseOverviewPresenter(
    private val view: ResponseOverviewContract.View,
    private val examinee: Examinee
) : ResponseOverviewContract.Presenter {

    private val TAG = "ROP"
    private val firebase = Firebase.instance

    // todo rewrite for this presenter
    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle">
    override fun create() {
//        getTutorialState()
//        view.configureRecyclerView()
//        view.setupUI(examinee)
//
//        val databaseReference = firebase
//            .databaseReference
//            .child("server")
//            .child("users")
//            .child(examinee.creatorUid!!)
//            .child("examinees")
//            .child(examinee.name!!)
//            .child("responses")
//
//        firebase.access(true, databaseReference, object : Firebase.OnGetDataListener {
//            override fun onSuccess(dataSnapshot: DataSnapshot) {
//                val responses = mutableListOf<Response>()
//                dataSnapshot.children.forEach { responseSnapshot ->
//                    val response = Response(
//                        responseSnapshot.child("category").getValue(String::class.java),
//                        responseSnapshot.child("timestamp").getValue(String::class.java),
//                        examinee.name
//                    )
//                    response.isCompleted = responseSnapshot.child("isCompleted").getValue(Boolean::class.java)!!
//
//                    val categoryAnswers = mutableListOf<Answer>()
//                    val commonAnswers = mutableListOf<Answer>()
//                    var counter = 1
//                    responseSnapshot.child("response").child(examinee.category!!).children.forEach {
//                        categoryAnswers.add(Answer(counter, it.getValue(Int::class.java)!!))
//                        counter++
//                    }
//                    counter = 1
//                    responseSnapshot.child("response").child("common").children.forEach {
//                        commonAnswers.add(Answer(counter, it.getValue(String::class.java)!!.toInt()))
//                        counter++
//                    }
//
//                    response.answers = mutableListOf()
//                    for (answer in categoryAnswers)
//                        response.answers?.add(answer)
//                    for (answer in commonAnswers)
//                        response.answers?.add(answer)
//                    responses.add(response)
//                }
//                view.setRecyclerViewAdapter(ResponseOverviewRecyclerViewAdapter(responses))
//            }
//
//            override fun onFailure(databaseError: DatabaseError) {
//                //todo handle failure
//                Log.d(TAG, databaseError.message)
//            }
//        })
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tutorial">
    override fun getTutorialState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTutorialSeen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun retryTutorial() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //</editor-fold>
}