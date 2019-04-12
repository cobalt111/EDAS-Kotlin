package com.timothycox.edas_kotlin.examinees

internal interface ExamineesContract {
    interface View {
        fun showTutorial(retry: Boolean)
        // todo add network connectivity check for presenter to use
        fun onClickAddExaminee()
        fun configureRecyclerView()
        fun navigateToExamineeCreator()
        fun setRecyclerViewAdapter(adapter: ExamineesRecyclerViewAdapter)
    }

    interface Presenter {
        fun create()
        fun onAddExaminee()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
