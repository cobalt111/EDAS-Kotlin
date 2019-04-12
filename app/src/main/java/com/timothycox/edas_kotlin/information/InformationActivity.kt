package com.timothycox.edas_kotlin.information

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Examinee
import kotlinx.android.synthetic.main.activity_information.*


class InformationActivity : AppCompatActivity(), InformationContract.View {

    private var presenter: InformationPresenter? = null
    private var navigator: InformationNavigator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        firstInformationSiteButton.setOnClickListener { onClickFirstSite() }
        secondInformationSiteButton.setOnClickListener { onClickSecondSite() }

        presenter = InformationPresenter(this, intent.getSerializableExtra("selectedExaminee") as Examinee)
        navigator = InformationNavigator(this)
    }

    private fun onClickFirstSite() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("http://www.autismspeaks.org")
        startActivity(intent)
    }

    private fun onClickSecondSite() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("https://www.nimh.nih.gov/health/topics/autism-spectrum-disorders-asd/index.shtml")
        startActivity(intent)
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

    // todo finish tutorial
    override fun showTutorial(retry: Boolean) {
        val introSV = ShowcaseView.Builder(this)
            .setContentTitle("More Information")
            .setContentText("This application is intended for people who want to take assessments for those who may have Autism Spectrum Disorders. This is the home screen.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
            .build()
        val testSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.mainTestButton, this))
            .setContentTitle("Take a new test")
            .setContentText("This button will take you to the examinee select screen. You can choose an examinee to take an assessment for and/or create a new examinee.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val assessmentSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.mainPreviousAssessmentsButton, this))
            .setContentTitle("Previous assessments")
            .setContentText("This button will show you previous assessments that you have taken.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val retryTutorialSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.main_sv_menu_anchor_textview, this))
            .setContentTitle("Start tutorials again")
            .setContentText("These three dots will show an options menu if selected. You may choose to view the tutorial for the current screen again at any time by selecting, \"Start Tutorial.\"")
            .setStyle(R.style.CustomShowcaseThemeDone)
            .withHoloShowcase()
            .hideOnTouchOutside()
        if (!introSV.isShowing) introSV.show()
        introSV.overrideButtonClick {
            introSV.hide()
            introSV.hideButton()
            val testSV = testSvBuilder.build()
            testSV.show()
            testSV.overrideButtonClick {
                testSV.hide()
                testSV.hideButton()
                val assessmentSV = assessmentSvBuilder.build()
                assessmentSV.show()
                assessmentSV.overrideButtonClick {
                    assessmentSV.hide()
                    assessmentSV.hideButton()
                    val retrySV = retryTutorialSvBuilder.build()
                    retrySV.showcaseX = 1
                    retrySV.show()
                    retrySV.overrideButtonClick {
                        retrySV.hide()
                        retrySV.hideButton()
                    }
                }
            }
        }
        if (!retry) presenter?.onTutorialSeen()
    }

    internal interface InformationScreenEvents {
        fun itemClicked(id: Int, bundle: Bundle?)
    }
}
