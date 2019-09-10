package com.timothycox.edas_kotlin.assessment

import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.model.Question
import com.timothycox.edas_kotlin.util.Firebase
import java.text.SimpleDateFormat
import java.util.*

internal class AssessmentPresenter(private val view: AssessmentContract.View, private val examinee: Examinee?) : AssessmentContract.Presenter {

    private val TAG = "AP"
    private val firebase: Firebase = Firebase.instance
    private var categoryQuestionNumber: Int = 0
    private var commonQuestionNumber: Int = 0
    private var categoryQuestionsFinished = false
    private var assessment : Assessment? = null

    override fun create() {
        loadSavedAssessmentFromDB()
        if (assessment == null) {
            assessment = Assessment(examinee?.category, examinee?.name, getTimestamp())
            loadNewAssessmentFromDB()
        }
        view.setupUI()
    }

    //<editor-fold defaultstate="collapsed" desc="Assessment">
    override fun loadNewAssessmentFromDB() {

        val databaseReference = firebase
            .databaseReference
            .child("server")
            .child("assessments")

        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { category ->
                    if (category.exists() and listOf(assessment!!.category, "common").contains(category.key.toString())) {
                        assessment!!.questions[category.key.toString()] = mutableListOf()
                        category.children.forEach { question ->
                            if (question.exists()) {
                                assessment!!.questions[category.key.toString()]!!.add(question.getValue(Question::class.java))
                            }
                        }
                    }
                }
                beginAssessment()
            }

            override fun onFailure(databaseError: DatabaseError) {
                Log.d(TAG, "Failed to load assessment from DB")
            }
        })
    }

    override fun loadSavedAssessmentFromDB() {

        val databaseReference = firebase
            .databaseReference
            .child("server")
            .child("users")
            .child(examinee?.creatorUid!!)
            .child("examinees")
            .child(examinee.name!!)
            .child("assessments")

        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {

            override fun onSuccess(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    assessment = dataSnapshot.children.first {
                        it.exists() and it.child("completed").exists() and !it.child("completed").getValue(Boolean::class.java)!!
                    }.getValue(Assessment::class.java)!!

                    if (assessment != null)
                        beginAssessment()
                }
            }

            override fun onFailure(databaseError: DatabaseError) {
                Log.d(TAG, "Failed to load saved assessment from DB")
            }
        })
    }

    override fun beginAssessment() {
        view.showQuestion(assessment!!.questions[assessment!!.category]!!.first()!!)
    }

    override fun onAnswer(answer: String?) {
        when {
            !isCategoryAssessmentFinished() -> { assessment!!.questions[assessment!!.category]!![categoryQuestionNumber]!!.answer = answer }
            !isCommonAssessmentFinished() -> { assessment!!.questions["common"]!![commonQuestionNumber]!!.answer = answer }
            else -> { /* todo handle wrong question numbering */ }
        }
        saveAssessmentState()
        showNextUnansweredQuestion()
    }

    override fun saveAssessmentState() {
        firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee?.creatorUid!!)
            .child("examinees")
            .child(examinee.name!!)
            .child("assessments")
            .child(assessment!!.timestamp!!)
            .setValue(assessment)
    }

    override fun showNextUnansweredQuestion() {

        if (!isCategoryAssessmentFinished()) {
            val question = getUnansweredCategoryQuestion()
            if (question != null)
                view.showQuestion(question)
        }
        else if (!categoryQuestionsFinished and !isCommonAssessmentFinished()) {
            categoryQuestionsFinished = true
            val question = getUnansweredCommonQuestion()
            if (question != null)
                view.showQuestion(question)
        }
        else if (!isCommonAssessmentFinished()) {
            val question = getUnansweredCommonQuestion()
            if (question != null)
                view.showQuestion(question)
        }

        onAssessmentFinished()
    }

    override fun getUnansweredCategoryQuestion(): Question? {
        val question = assessment!!.questions[assessment!!.category]!!.firstOrNull { it!!.answer == null }
        categoryQuestionNumber = question?.id ?: assessment!!.questions[assessment!!.category]!!.size
        return question
    }

    override fun getUnansweredCommonQuestion(): Question? {
        val question = assessment!!.questions["common"]!!.firstOrNull { it!!.answer == null }
        commonQuestionNumber = question?.id ?: assessment!!.questions["common"]!!.size
        return question
    }


    override fun isCategoryAssessmentFinished(): Boolean {
        return assessment!!.questions[assessment!!.category]!!.size == assessment!!.questions[assessment!!.category]!!.mapNotNull { it!!.answer }.size
    }

    override fun isCommonAssessmentFinished(): Boolean {
        return assessment!!.questions["common"]!!.size == assessment!!.questions["common"]!!.mapNotNull { it!!.answer }.size
    }

    override fun onAssessmentFinished() {

        assessment!!.isCompleted = true

        firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee?.creatorUid!!)
            .child("examinees")
            .child(examinee.name!!)
            .child("assessments")
            .child(assessment!!.timestamp!!)
            .setValue(assessment)

        view.showAssessmentFinishedView()
    }

    override fun onAcceptedFinishedAssessment() {
        val bundle = Bundle()
        bundle.putSerializable("assessment", assessment)
        bundle.putSerializable("selectedExaminee", examinee)
        view.navigateToResult(bundle)
    }
    //</editor-fold>

    private fun getTimestamp(): String? {
        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat.getDateInstance()
        simpleDateFormat.timeZone = calendar.timeZone
        return simpleDateFormat.format(Date(System.currentTimeMillis() * 1000))
    }
}

