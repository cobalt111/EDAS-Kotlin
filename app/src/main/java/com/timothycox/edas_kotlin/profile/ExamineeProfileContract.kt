package com.timothycox.edas_kotlin.profile


import android.os.Bundle
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.model.Response

internal interface ExamineeProfileContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun configureRecyclerView()
        fun populateUIWithData(examinee: Examinee)
        fun setRecyclerViewAdapter(adapter: ExamineeProfileRecyclerViewAdapter)
        fun onClickTakeNewTest()
        fun navigateToAssessment(bundle: Bundle?)
        fun navigateToResult(bundle: Bundle?)
    }

    interface Presenter {
        fun create()
        fun onPreviousAssessmentSelected(response: Response?)
        fun onTakeTestSelected()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
