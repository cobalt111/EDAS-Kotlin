package com.timothycox.edas_kotlin.creator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.User
import kotlinx.android.synthetic.main.activity_examinee_creator.*

class ExamineeCreatorActivity : AppCompatActivity(), ExamineeCreatorContract.View {

    private var presenter: ExamineeCreatorPresenter? = null
    private var navigator: ExamineeCreatorNavigator? = null

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examinee_creator)
        presenter = ExamineeCreatorPresenter(view = this, user = intent.getBundleExtra("examineeListBundle").getSerializable("user") as User)
        navigator = ExamineeCreatorNavigator(context = this)
        examineeCreatorAddButton.setOnClickListener { onClickAddExaminee() }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Click Events"
    override fun onClickAddExaminee() {
        presenter?.onAddExaminee()
    }
    //</editor-fold>

    override fun saveEnteredExamineeData(): Bundle {
        val bundle = Bundle()
        bundle.putString("name", examineeCreatorNameTextfield.text.toString())
        bundle.putInt("ageInMonths", Integer.parseInt(examineeCreatorAgeTextfield.text.toString()))
        bundle.putString("gender", examineeCreatorGenderSpinner.selectedItem.toString())
        return bundle
    }

    //<editor-fold defaultstate="collapsed" desc="Navigation Methods"
    override fun navigateToAssessments(bundle: Bundle) {
        navigator?.navigateTo(ExamineeCreatorNavigator.ASSESSMENTS_ACTIVITY, bundle)
        finish()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Options Menu"
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_retry_tutorial) {
            presenter?.retryTutorial()
        }
        return super.onOptionsItemSelected(item)
    }
    //</editor-fold>

    //todo change strings
    override fun showTutorial(retry: Boolean) {
        val introSV = ShowcaseView.Builder(this)
            .setContentTitle("Examinee Creator")
            .setContentText("This is the examinee creator screen. Here you will create the profile for the examinee to be assessed.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
            .build()
        val nameSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.examineeCreatorNameTextfield, this))
            .setContentTitle("Name")
            .setContentText("Enter the examinee's name.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val ageSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.examineeCreatorAgeTextfield, this))
            .setContentTitle("Age")
            .setContentText("Enter the examinee's ageInMonths in months.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val genderSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.examineeCreatorGenderSpinner, this))
            .setContentTitle("Gender")
            .setContentText("If desired, enter the examinee's gender or select \"Prefer not to say.\"")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val submitSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.examineeCreatorAddButton, this))
            .setContentTitle("Submit")
            .setContentText("Click this button to submit your new examinee.")
            .setStyle(R.style.CustomShowcaseThemeDone)
            .withHoloShowcase()
            .hideOnTouchOutside()
        if (!introSV.isShowing) introSV.show()
        introSV.overrideButtonClick {
            introSV.hide()
            introSV.hideButton()
            val nameSV = nameSvBuilder.build()
            nameSV.show()
            nameSV.overrideButtonClick {
                nameSV.hide()
                nameSV.hideButton()
                val ageSV = ageSvBuilder.build()
                ageSV.show()
                ageSV.overrideButtonClick {
                    ageSV.hide()
                    ageSV.hideButton()
                    val genderSV = genderSvBuilder.build()
                    genderSV.show()
                    genderSV.overrideButtonClick {
                        genderSV.hide()
                        genderSV.hideButton()
                        val submitSV = submitSvBuilder.build()
                        submitSV.show()
                        submitSV.overrideButtonClick {
                            submitSV.hide()
                            submitSV.hideButton()
                        }
                    }
                }
            }
        }
        if (!retry) presenter?.onTutorialSeen()
    }

    internal interface CreatorScreenEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
