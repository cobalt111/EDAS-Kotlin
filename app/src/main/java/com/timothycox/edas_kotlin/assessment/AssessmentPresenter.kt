package com.timothycox.edas_kotlin.assessment

import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.*
import com.timothycox.edas_kotlin.util.Firebase
import java.text.SimpleDateFormat
import java.util.*

internal class AssessmentPresenter(
    private val view: AssessmentContract.View,
    private val examinee: Examinee?
) : AssessmentContract.Presenter {

    private val TAG = "AP"
    private val firebase: Firebase = Firebase.instance
    private var assessment: Assessment? = null
    private var categoryQuestionNumber: Int = 0
    private var commonQuestionNumber: Int = 0
    private var response = Response(
        examinee?.category,
        getTimestamp(),
        examinee?.name
    )

    override fun create() {
        loadAssessmentFromDB()
        view.setupUI()
    }

    //<editor-fold defaultstate="collapsed" desc="Assessment">
    override fun loadAssessmentFromDB() {
        val categoryQuestions = mutableListOf<Question>()
        val commonQuestions = mutableListOf<Question>()
        val questions = mutableListOf<List<Question>>()

        val databaseReference = firebase
            .databaseReference
            .child("server")
            .child("assessments")

        firebase.access(false, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                val categorySnapshot = dataSnapshot.child(examinee?.category!!)
                val commonSnapshot = dataSnapshot.child("common")
                categorySnapshot.children.forEach { question ->
                    categoryQuestions.add(Question(
                        (question.child("id").value as Long).toInt(),
                        question.child("importance").value as String,
                        question.child("questionText").value as String,
                        examinee.category
                    ))
                }
                commonSnapshot.children.forEach { question ->
                    commonQuestions.add(Question(
                        (question.child("id").value as Long).toInt(),
                        question.child("importance").value as String,
                        question.child("questionText").value as String,
                        "common"
                    ))
                }
                questions.add(0, categoryQuestions.toList())
                questions.add(1, commonQuestions.toList())
                assessment = Assessment(questions.toList(), examinee.category)
                beginAssessment()
            }

            override fun onFailure(databaseError: DatabaseError) {
                Log.d(TAG, "Failed to load assessment from DB")
            }
        })
    }

    override fun beginAssessment() {
        response.answers = mutableListOf()
        val categoryAnswers = mutableListOf<Answer>()
        val commonAnswers = mutableListOf<Answer>()
        response.answers?.add(0, categoryAnswers)
        response.answers?.add(1, commonAnswers)
        view.showQuestion(assessment?.questions!![0].first())
    }

    override fun onAnswer(answerNumber: Int?) {
        when {
            !isCategoryAssessmentFinished() -> {
                response.answers!![0].add(Answer(categoryQuestionNumber + 1, answerNumber))
            }
            !isCommonAssessmentFinished() -> {
                response.answers!![1].add(Answer(categoryQuestionNumber + 1, answerNumber))
            }
            else -> {
                // todo handle wrong question numbering
            }
        }
        showNextQuestion()
    }

    override fun showNextQuestion() {
        if (!isCategoryAssessmentFinished()) {
            view.showQuestion(getCategoryQuestion(categoryQuestionNumber + 1)!!)
            categoryQuestionNumber++
        }
        else if (!isCommonAssessmentFinished()) {
            view.showQuestion(getCommonQuestion(commonQuestionNumber + 1)!!)
            commonQuestionNumber++
        }
        else
            onAssessmentFinished()
    }

    override fun getCategoryQuestion(questionId: Int?): Question? {
        return assessment?.questions!![0][questionId!! - 1]
    }

    override fun getCommonQuestion(questionId: Int?): Question? {
        return assessment?.questions!![1][questionId!! - 1]
    }


    override fun isCategoryAssessmentFinished(): Boolean {
        return assessment?.questions!![0].size == categoryQuestionNumber
    }

    override fun isCommonAssessmentFinished(): Boolean {
        return assessment?.questions!![1].size == commonQuestionNumber
    }

    override fun onAssessmentFinished() {
        firebase.databaseReference
            .child("server")
            .child("users")
            .child(examinee?.creatorUid!!)
            .child("examinees")
            .child(examinee.name!!)
            .child("assessments")
            .child(response.timestamp!!)
            .setValue(response)

        view.showAssessmentFinishedView()
    }

    override fun onAcceptedFinishedAssessment() {
        val bundle = Bundle()
        bundle.putSerializable("response", response)
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

