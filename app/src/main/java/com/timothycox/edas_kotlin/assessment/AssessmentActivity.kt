package com.timothycox.edas_kotlin.assessment

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.assessment.fragment.AssessmentFragment

class AssessmentActivity : AppCompatActivity(), AssessmentContract.View, AssessmentFragment.OnFragmentInteractionListener {

    private var presenter: AssessmentPresenter? = null
    private var navigator: AssessmentNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
        presenter = AssessmentPresenter(this)
        navigator = AssessmentNavigator(this)
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal interface AssessmentScreenEvents {
        fun itemClicked(id: Int)
    }
}
