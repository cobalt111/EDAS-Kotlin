package com.timothycox.edas_kotlin.profile


import android.os.Bundle
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Examinee

internal interface ExamineeProfileContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun configureRecyclerView()
        fun populateUIWithData(examinee: Examinee)
        fun setRecyclerViewAdapter(adapter: ExamineeProfileRecyclerViewAdapter)
        fun onClickTakeNewTest()
        fun navigateToResponse(bundle: Bundle?)
        fun navigateToAssessment(bundle: Bundle?)
    }

    interface Presenter {
        fun create()
        fun onPreviousAssessmentSelected(assessment: Assessment)
        fun onTakeNewTestSelected()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
