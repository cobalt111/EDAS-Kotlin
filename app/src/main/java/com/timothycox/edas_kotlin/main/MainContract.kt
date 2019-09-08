package com.timothycox.edas_kotlin.main

import android.os.Bundle

internal interface MainContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun onClickTests()
        fun navigateToTests(bundle: Bundle)
        fun onClickPreviousAssessments()
        fun navigateToPreviousAssessments(bundle: Bundle?)
    }

    interface Presenter {
        fun create()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
        fun onTests()
        fun onPreviousAssessments()
    }
}
