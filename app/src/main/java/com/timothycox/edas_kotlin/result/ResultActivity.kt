package com.timothycox.edas_kotlin.result

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Examinee
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(), ResultContract.View {

    private var presenter: ResultPresenter? = null
    private var navigator: ResultNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        presenter = ResultPresenter(
            this,
            intent.getSerializableExtra("selectedExaminee") as Examinee,
            intent.getSerializableExtra("assessment") as Assessment
        )
        navigator = ResultNavigator(this)
        presenter?.create()

        resultLearnMoreButton.setOnClickListener { onClickLearnMore() }
        resultTakeNewTestButton.setOnClickListener { onClickNewTest() }
    }

    override fun populatedUIWithData(examinee: Examinee, assessment: Assessment) {
        resultNameText?.text = examinee.name
        resultAgeText?.text = examinee.ageAsHumanReadable
        resultDateText?.text = assessment.timestamp
        resultScoreText?.text = assessment.result.toString()
        when (examinee.gender) {
            "Male" -> {
                resultGirlFace?.visibility = View.GONE
                resultNeutralFace?.visibility = View.GONE
            }
            "Female" -> {
                resultBoyFace?.visibility = View.GONE
                resultNeutralFace?.visibility = View.GONE
            }
            else -> {
                resultGirlFace?.visibility = View.GONE
                resultBoyFace?.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_retry_tutorial) {
            presenter?.retryTutorial()
        }
        return super.onOptionsItemSelected(item)
    }

    internal interface ResultScreenEvents {
        fun itemClicked(id: Int, bundle: Bundle?)
    }

    override fun onClickNewTest() {
        presenter?.onNewTest()
    }

    override fun navigateToNewTest(bundle: Bundle) {
        navigator?.itemClicked(ResultNavigator.ASSESSMENT_ACTIVITY, bundle)
        finish()
    }

    override fun onClickLearnMore() {
        presenter?.onLearnMore()
    }

    override fun navigateToLearnMore(bundle: Bundle) {
        navigator?.itemClicked(ResultNavigator.INFORMATION_ACTIVITY, bundle)
    }


    //todo finish this tutorial
    override fun showTutorial(retry: Boolean) {
        val introSV = ShowcaseView.Builder(this)
            .setContentTitle("Assessment results")
            .setContentText("This screen shows the results of the assessment for the examinee.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
            .build()
        val infoSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.resultScoreText, this))
            .setContentTitle("Final score")
            .setContentText("This is the score the examinee has been given for the assessment.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val assessmentsTakenSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.resultExplanationText, this))
            .setContentTitle("Score explanation")
            .setContentText("This will provide more information about the next steps to take based on the score given to your examinee.")
            .setStyle(R.style.CustomShowcaseThemeDone)
            .withHoloShowcase()
            .hideOnTouchOutside()
        if (!introSV.isShowing) introSV.show()
        introSV.overrideButtonClick { view: View ->
            introSV.hide()
            introSV.hideButton()
            val infoSV = infoSvBuilder.build()
            infoSV.show()
            infoSV.overrideButtonClick { anotherView: View ->
                infoSV.hide()
                infoSV.hideButton()
                val assessmentTakenSV = assessmentsTakenSvBuilder.build()
                assessmentTakenSV.show()
                assessmentTakenSV.overrideButtonClick { thirdView: View ->
                    assessmentTakenSV.hide()
                    assessmentTakenSV.hideButton()
                }
            }
        }
        if (!retry) presenter?.onTutorialSeen()
    }
}
