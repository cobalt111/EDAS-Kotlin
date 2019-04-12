package com.timothycox.edas_kotlin.information

internal interface InformationContract {
    interface View {
        fun showTutorial(retry: Boolean)
    }

    interface Presenter {
        fun create()
        fun getTutorialState()
        fun onTutorialSeen()
        fun retryTutorial()
    }
}
