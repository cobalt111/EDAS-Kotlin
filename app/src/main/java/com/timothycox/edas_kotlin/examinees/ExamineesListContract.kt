package com.timothycox.edas_kotlin.examinees

import android.os.Bundle
import com.timothycox.edas_kotlin.model.Examinee

internal interface ExamineesListContract {
    interface View {
        fun showTutorial(retry: Boolean)
        // todo add network connectivity check for presenter to use
        fun onClickAddExaminee()
        fun configureRecyclerView()
        fun navigateToExamineeProfile(bundle: Bundle?)
        fun navigateToExamineeCreator(bundle: Bundle?)
        fun setRecyclerViewAdapter(adapter: ExamineesListRecyclerViewAdapter)
    }

    interface Presenter {
        fun create()
        fun onAddExaminee()
        fun onExamineeSelected(examinee: Examinee?)
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
