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
    var questionNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment)
        val bundle =
            when {
                intent.getBundleExtra("resultBundle") != null -> intent.getBundleExtra("resultBundle")
                intent.getBundleExtra("assessmentListBundle") != null -> intent.getBundleExtra("assessmentListBundle")
                intent.getBundleExtra("examineeProfileBundle") != null -> intent.getBundleExtra("examineeProfileBundle")
                else -> null
            }
        presenter = AssessmentPresenter(
            this,
            bundle?.getSerializable("selectedExaminee") as Examinee
        )
        navigator = AssessmentNavigator(this)
        presenter?.create()
    }

    override fun setupUI() {
        title = getString(R.string.assessmentActivityTitle)
        assessmentAnswerOneButton.setOnClickListener { onClickAnswerOne() }
        assessmentAnswerTwoButton.setOnClickListener { onClickAnswerTwo() }
        assessmentAnswerThreeButton.setOnClickListener { onClickAnswerThree() }
    }

    override fun onClickAnswerOne() {
        presenter?.onAnswer(1)
        questionNumber++
    }

    override fun onClickAnswerTwo() {
        presenter?.onAnswer(2)
        questionNumber++
    }

    override fun onClickAnswerThree() {
        presenter?.onAnswer(3)
        questionNumber++
    }

    override fun showQuestion(question: Question) {
        assessmentQuestionNumberText.text = questionNumber.toString()
        assessmentQuestionText.text = question.questionText
    }

    override fun showAssessmentFinishedView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToResult(bundle: Bundle?) {
        navigator?.navigateTo(AssessmentNavigator.RESULTS_ACTIVITY, bundle)
    }

    internal interface AssessmentScreenEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
