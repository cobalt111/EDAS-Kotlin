package com.timothycox.edas_kotlin.assessment.list

internal interface AssessmentListContract {
    interface View {
        fun setRecyclerViewAdapter(adapter: AssessmentRecyclerViewAdapter)
    }

    interface Presenter {
        fun create()
    }
}
