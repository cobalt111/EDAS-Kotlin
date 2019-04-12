package com.timothycox.edas_kotlin.profile


import com.timothycox.edas_kotlin.model.Examinee

internal interface ExamineeProfileContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun configureRecyclerView()
        fun populateUIWithData(examinee: Examinee)
        fun setRecyclerViewAdapter(adapter: ExamineeProfileRecyclerViewAdapter)
    }

    interface Presenter {
        fun create()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
