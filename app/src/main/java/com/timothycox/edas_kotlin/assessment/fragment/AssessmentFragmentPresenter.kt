package com.timothycox.edas_kotlin.assessment.fragment

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.*
import com.timothycox.edas_kotlin.util.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AssessmentFragmentPresenter(
    private var view: AssessmentFragmentContract.View,
    private var examinee: Examinee
) : AssessmentFragmentContract.Presenter {

    private val TAG = "AFCP"
    private val firebase: Firebase = Firebase.instance

    private var response = Response(
        examinee.category,
        getTimestamp(),
        examinee.name
    )

    private val assessment: Assessment? = loadAssessmentFromDB()
    private var questionNumber: Int = assessment?.questions?.size!!

    //<editor-fold defaultstate="collapsed" desc="Fragment Lifecycle">
    override fun create() {
        view.populateUIWithData(assessment!!)
    }
    //</editor-fold>

    override fun loadAssessmentFromDB(): Assessment {
        val categoryQuestions = mutableListOf<Question>()
        val commonQuestions = mutableListOf<Question>()
        val questions = mutableListOf<Question>()

        val databaseReference = firebase
            .databaseReference
            .child("server")
            .child("users")
            .child("assessments")

        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                val categorySnapshot = dataSnapshot.child(examinee.category!!)
                val commonSnapshot = dataSnapshot.child("common")

                categorySnapshot.children.forEach {
                    val questionId = it.child("id").getValue(Int::class.java)
                    categoryQuestions[questionId!!.toInt()] = it.getValue(Question::class.java)!!
                }

                commonSnapshot.children.forEach {
                    val questionId = it.child("id").getValue(Int::class.java)
                    commonQuestions[questionId!!.toInt()] = it.getValue(Question::class.java)!!
                }

                for (question in categoryQuestions)
                    questions.add(question)
                for (question in commonQuestions)
                    questions.add(question)
            }

            override fun onFailure(databaseError: DatabaseError) {
                Log.d(TAG, "Failed to load assessment from DB")
            }
        })

        return Assessment(
            questions.toList(),
            examinee.category,
            examinee.name,
            response.timestamp
        )
    }

    override fun onAnswer(answer: Int) {
        response.answers!![questionNumber] = Answer(questionNumber, answer)
        if (!isAssessmentFinished())
            showNextQuestion()
        else
            onAssessmentFinished()
    }

    override fun showNextQuestion() {
        val question = getQuestion(questionNumber++)
    }

    override fun getQuestion(questionId: Int): Question {
        return assessment?.questions!![questionId]
    }

    override fun isAssessmentFinished(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAssessmentFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveResponseState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getTimestamp(): String? {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat.getDateInstance()
        simpleDateFormat.timeZone = calendar.timeZone
        return simpleDateFormat.format(Date(System.currentTimeMillis() * 1000))
    }
}