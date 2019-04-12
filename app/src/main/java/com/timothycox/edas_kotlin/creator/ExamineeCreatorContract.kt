package com.timothycox.edas_kotlin.creator

import android.os.Bundle

internal interface ExamineeCreatorContract {
    interface View {
        fun onClickAddExaminee()
        fun saveEnteredExamineeData(): Bundle
        fun showTutorial(retry: Boolean)
        fun navigateToAssessments(bundle: Bundle)
    }

    interface Presenter {
        fun create()
        fun onAddExaminee()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
