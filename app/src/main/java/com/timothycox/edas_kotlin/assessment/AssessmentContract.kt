package com.timothycox.edas_kotlin.assessment

import android.os.Bundle
import com.timothycox.edas_kotlin.model.Question

internal interface AssessmentContract {
    interface View {
        fun onClickAnswerOne()
        fun onClickAnswerTwo()
        fun onClickAnswerThree()
        fun setupUI()
        fun showQuestion(question: Question)
        fun showAssessmentFinishedView()
        fun navigateToResult(bundle: Bundle?)
    }
    interface Presenter {
        fun create()
        fun loadAssessmentFromDB()
        fun beginAssessment()
        fun onAnswer(answerNumber: Int?)
        fun showNextQuestion()
        fun getCategoryQuestion(questionId: Int?): Question?
        fun getCommonQuestion(questionId: Int?): Question?
        fun isCategoryAssessmentFinished(): Boolean
        fun isCommonAssessmentFinished(): Boolean
        fun onAssessmentFinished()
        fun onAcceptedFinishedAssessment()
    }
}
