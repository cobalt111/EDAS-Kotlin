package com.timothycox.edas_kotlin.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.google.firebase.FirebaseApp
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.NetworkStateReceiver
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener, MainContract.View {

    private var presenter: MainPresenter? = null
    private var navigator: MainNavigator? = null

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        presenter = MainPresenter(this, intent.getBundleExtra("loginBundle").getSerializable("user") as User)
        navigator = MainNavigator(this)
        setSupportActionBar(toolbar)
        presenter?.create()
        mainTestButton.setOnClickListener { onClickTests() }
        mainPreviousAssessmentsButton.setOnClickListener { onClickPreviousAssessments() }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Network Status"
    override fun networkAvailable() {

    }


    override fun networkUnavailable() {

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Options Menu"
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

        if (id == R.id.action_retry_tutorial)
            presenter?.retryTutorial()
        return super.onOptionsItemSelected(item)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Click Events"
    override fun onClickTests() {
        presenter?.onTests()
    }

    override fun onClickPreviousAssessments() {
        presenter?.onPreviousAssessments()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Navigation"
    override fun navigateToTests(bundle: Bundle) {
        navigator?.navigateTo(MainNavigator.EXAMINEES_ACTIVITY, bundle)
    }

    override fun navigateToPreviousAssessments(bundle: Bundle?) {
        navigator?.navigateTo(MainNavigator.ASSESSMENT_LIST_ACTIVITY, bundle)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tutorial">
    override fun showTutorial(retry: Boolean) {
        val introSV = ShowcaseView.Builder(this)
            .setContentTitle("Hello!")
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
        if (!introSV.isShowing)
            introSV.show()
        introSV.overrideButtonClick{
            introSV.hide()
            introSV.hideButton()
            val testSV = testSvBuilder.build()
            testSV.show()
            testSV.overrideButtonClick{
                testSV.hide()
                testSV.hideButton()
                val assessmentSV = assessmentSvBuilder.build()
                assessmentSV.show()
                assessmentSV.overrideButtonClick{
                    assessmentSV.hide()
                    assessmentSV.hideButton()
                    val retrySV = retryTutorialSvBuilder.build()
                    retrySV.showcaseX = 1
                    retrySV.show()
                    retrySV.overrideButtonClick{
                        retrySV.hide()
                        retrySV.hideButton()
                    }
                }
            }
        }
        if (!retry) presenter?.onTutorialSeen()
    }
    //</editor-fold>

    internal interface MainScreenEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
