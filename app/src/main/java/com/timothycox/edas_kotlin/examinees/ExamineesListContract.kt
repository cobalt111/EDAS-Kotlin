package com.timothycox.edas_kotlin.examinees

import android.os.Bundle

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
        fun onExamineeSelected(bundle: Bundle?)
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
