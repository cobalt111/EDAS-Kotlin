package com.timothycox.edas_kotlin.examinees

import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.Firebase
import java.util.*

internal class ExamineesListPresenter(private val view: ExamineesListContract.View, private val user: User) :
    ExamineesListContract.Presenter {

    // todo remove tag
    private val TAG = "ExamineesListPresenter"
    private val firebase: Firebase = Firebase.instance

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle"
    override fun create() {
        view.configureRecyclerView()
        getTutorialState()

        val examineesReference = firebase
            .databaseReference
            .child("server")
            .child("users")
            .child(user.uid!!)
            .child("examinees")

        firebase.access(true, examineesReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                val examineeList = ArrayList<Examinee>()
                var examinee: Examinee
                dataSnapshot.children.forEach {
                    examinee = Examinee(
                        it.child("name").value as String,
                        (it.child("age").value as Long).toInt(),
                        it.child("gender").value as String,
                        it.child("creatorUid").value as String
                    )
                    examinee.creatorUid = user.uid
                    examineeList.add(examinee)
                }
                view.setRecyclerViewAdapter(ExamineesListRecyclerViewAdapter(examineeList))
            }

            override fun onFailure(databaseError: DatabaseError) {
                //todo handle failure
                Log.d(TAG, databaseError.message)
            }
        })
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Events"
    override fun onAddExaminee() {
        val bundle = Bundle()
        bundle.putSerializable("user", user)
        view.navigateToExamineeCreator(bundle)
    }

    override fun onExamineeSelected(bundle: Bundle?) {
        bundle?.putSerializable("user", user)
        view.navigateToExamineeProfile(bundle)
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tutorial"
    override fun getTutorialState() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(user.uid!!)
            .child("tutorials")
            .child("seenExaminees")
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
            .child(user.uid!!)
            .child("tutorials")
            .child("seenExaminees")
        databaseReference.setValue(true)
    }

    override fun retryTutorial() {
        view.showTutorial(true)
    }
    //</editor-fold>
}
