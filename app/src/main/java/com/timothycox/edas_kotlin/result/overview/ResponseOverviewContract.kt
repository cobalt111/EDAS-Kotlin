package com.timothycox.edas_kotlin.result.overview

import com.timothycox.edas_kotlin.model.Response

interface ResponseOverviewContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun configureRecyclerView()
        fun populateUIWithData(response: Response)
        fun setRecyclerViewAdapter(adapter: ResponseOverviewRecyclerViewAdapter)
    }
    interface Presenter {
        fun create()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}