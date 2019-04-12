package com.timothycox.edas_kotlin.assessment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.timothycox.edas_kotlin.R

class AssessmentActivity : AppCompatActivity(), AssessmentContract.View {

    private var presenter: AssessmentPresenter? = null
    private var navigator: AssessmentNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
        presenter = AssessmentPresenter(this)
        navigator = AssessmentNavigator(this)
    }

    internal interface AssessmentScreenEvents {
        fun itemClicked(id: Int)
    }
}
