package com.timothycox.edas_kotlin.result.overview

import com.timothycox.edas_kotlin.model.Assessment

interface ResponseOverviewContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun configureRecyclerView()
        fun populateUIWithData(assessment: Assessment)
        fun setRecyclerViewAdapter(adapter: ResponseOverviewRecyclerViewAdapter)
    }
    interface Presenter {
        fun create()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}