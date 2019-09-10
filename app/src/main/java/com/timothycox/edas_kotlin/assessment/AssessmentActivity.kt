package com.timothycox.edas_kotlin.assessment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Examinee
import com.timothycox.edas_kotlin.model.Question
import kotlinx.android.synthetic.main.activity_assessment.*

class AssessmentActivity : AppCompatActivity(), AssessmentContract.View {

    private var presenter: AssessmentPresenter? = null
    private var navigator: AssessmentNavigator? = null
    private var questionNumberForUI = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
        val bundle = when {
            intent.getBundleExtra("resultBundle") != null -> intent.getBundleExtra("resultBundle")
            intent.getBundleExtra("assessmentListBundle") != null -> intent.getBundleExtra("assessmentListBundle")
            intent.getBundleExtra("examineeProfileBundle") != null -> intent.getBundleExtra("examineeProfileBundle")
            intent.getBundleExtra("examineeCreatorBundle") != null -> intent.getBundleExtra("examineeCreatorBundle")
            else -> null
        }
        presenter = AssessmentPresenter(view = this, examinee = bundle?.getSerializable("selectedExaminee") as Examinee)
        navigator = AssessmentNavigator(context = this)
        presenter?.create()
    }

    override fun setupUI() {
        title = getString(R.string.assessmentActivityTitle)
        assessmentAnswerOneButton.setOnClickListener { onClickAnswerOne() }
        assessmentAnswerTwoButton.setOnClickListener { onClickAnswerTwo() }
        assessmentAnswerThreeButton.setOnClickListener { onClickAnswerThree() }
    }

    override fun onClickAnswerOne() {
        presenter?.onAnswer(answer = "Yes")
        questionNumberForUI++
    }

    override fun onClickAnswerTwo() {
        presenter?.onAnswer(answer = "Sometimes")
        questionNumberForUI++
    }

    override fun onClickAnswerThree() {
        presenter?.onAnswer(answer = "No")
        questionNumberForUI++
    }

    override fun showQuestion(question: Question) {
        assessmentQuestionNumberText.text = questionNumberForUI.toString()
        assessmentQuestionText.text = question.questionText
    }

    override fun showAssessmentFinishedView() {
        //todo show that assessment is done with button to see results
    }

    override fun navigateToResult(bundle: Bundle?) {
        navigator?.navigateTo(AssessmentNavigator.RESULTS_ACTIVITY, bundle)
    }

    internal interface AssessmentScreenEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
