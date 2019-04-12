package com.timothycox.edas_kotlin.assessment.fragment

import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Question

interface AssessmentFragmentContract {
    interface View {
        fun onClickAnswerOne()
        fun onClickAnswerTwo()
        fun onClickAnswerThree()
        fun populateUIWithData(assessment: Assessment)
    }
    interface Presenter {
        fun create()
        fun loadAssessmentFromDB(): Assessment
        fun onAnswer(answer: Int)
        fun showNextQuestion()
        fun getQuestion(questionId: Int): Question
        fun isAssessmentFinished(): Boolean
        fun onAssessmentFinished()
        fun saveResponseState()
    }
}