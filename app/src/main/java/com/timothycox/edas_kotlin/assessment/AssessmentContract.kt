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
        fun loadNewAssessmentFromDB()
        fun loadSavedAssessmentFromDB()
        fun beginAssessment()
        fun saveAssessmentState()
        fun onAnswer(answer: String?)
        fun showNextUnansweredQuestion()
        fun getUnansweredCategoryQuestion(): Question?
        fun getUnansweredCommonQuestion(): Question?
        fun isCategoryAssessmentFinished(): Boolean
        fun isCommonAssessmentFinished(): Boolean
        fun onAssessmentFinished()
        fun onAcceptedFinishedAssessment()
    }
}
