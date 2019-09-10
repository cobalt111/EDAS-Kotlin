package com.timothycox.edas_kotlin.result

import android.os.Bundle
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Examinee


internal interface ResultContract {
    interface View {
        fun showTutorial(retry: Boolean)
        fun populatedUIWithData(examinee: Examinee, assessment: Assessment)
        fun onClickNewTest()
        fun navigateToNewTest(bundle: Bundle)
        fun onClickLearnMore()
        fun navigateToLearnMore(bundle: Bundle)
    }

    interface Presenter {
        fun create()
        fun onNewTest()
        fun onLearnMore()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
