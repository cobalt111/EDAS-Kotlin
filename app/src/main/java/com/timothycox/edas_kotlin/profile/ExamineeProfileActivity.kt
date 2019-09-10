package com.timothycox.edas_kotlin.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Examinee
import kotlinx.android.synthetic.main.activity_profile.*

class ExamineeProfileActivity : AppCompatActivity(), ExamineeProfileContract.View {

    private var presenter: ExamineeProfilePresenter? = null
    private var navigator: ExamineeProfileNavigator? = null
    private var adapter: ExamineeProfileRecyclerViewAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        presenter = ExamineeProfilePresenter(this, intent.getBundleExtra("examineeListBundle").getSerializable("selectedExaminee") as Examinee)
        navigator = ExamineeProfileNavigator(this)
        presenter?.create()

        examineeProfileTakeNewTestButton.setOnClickListener { onClickTakeNewTest() }

        //        //todo fix this
        //        List<Assessment> assessments = new ArrayList<>();
        //        Assessment sampleAssessment = new Assessment();
        //        sampleAssessment.setTimestamp("11-10-18");
        //        sampleAssessment.setResult(34);
        //        sampleAssessment.setCompleted(true);
        //        assessments.add(sampleAssessment);
        //
        //        adapter = new ExamineeProfileRecyclerViewAdapter(assessments);
        //        profileRecyclerView.setAdapter(adapter);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RecyclerView">
    override fun configureRecyclerView() {
        profileRecyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        profileRecyclerView?.layoutManager = layoutManager
        profileRecyclerView?.itemAnimator = DefaultItemAnimator()
        profileRecyclerView?.addOnItemTouchListener(RecyclerTouchListener(applicationContext, profileRecyclerView!!,
            object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        presenter?.onPreviousAssessmentSelected(adapter!!.assessmentList[position])
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )
    }

    override fun setRecyclerViewAdapter(adapter: ExamineeProfileRecyclerViewAdapter) {
        this.adapter = adapter
        profileRecyclerView?.adapter = adapter
    }
    //</editor-fold>

    override fun populateUIWithData(examinee: Examinee) {

        profileNameText?.text = examinee.name
        profileAgeText?.text = examinee.ageAsHumanReadable

        when {
            examinee.gender == "Male" -> {
                profileGirlImage?.visibility = View.GONE
                profileNeutralImage?.visibility = View.GONE
            }
            examinee.gender == "Female" -> {
                profileBoyImage?.visibility = View.GONE
                profileNeutralImage?.visibility = View.GONE
            }
            else -> {
                profileGirlImage?.visibility = View.GONE
                profileBoyImage?.visibility = View.GONE
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Options Menu">
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Click Events">
    override fun onClickTakeNewTest() {
        presenter?.onTakeNewTestSelected()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Navigation">
    override fun navigateToResponse(bundle: Bundle?) {
        navigator?.navigateTo(ExamineeProfileNavigator.RESULT_ACTIVITY, bundle)
    }

    override fun navigateToAssessment(bundle: Bundle?) {
        navigator?.navigateTo(ExamineeProfileNavigator.ASSESSMENT_ACTIVITY, bundle)
    }
    //</editor-fold>

    // todo finish this tutorial
    //<editor-fold defaultstate="collapsed" desc="Tutorial">
    override fun showTutorial(retry: Boolean) {
        val introSV = ShowcaseView.Builder(this)
            .setContentTitle("Examinee Profile")
            .setContentText("This screen shows information about the examinee selected.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
            .build()
        val infoSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.profileAgeLabel, this))
            .setContentTitle("Profile information")
            .setContentText("This is the examinee's name and age.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val assessmentsTakenSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.profileRecyclerView, this))
            .setContentTitle("Previous assessments")
            .setContentText("If available, chose one of these to view previous assessment results for this examinee.")
            .setStyle(R.style.CustomShowcaseThemeDone)
            .withHoloShowcase()
            .hideOnTouchOutside()
        if (!introSV.isShowing) introSV.show()
        introSV.overrideButtonClick {
            introSV.hide()
            introSV.hideButton()
            val infoSV = infoSvBuilder.build()
            infoSV.show()
            infoSV.overrideButtonClick {
                infoSV.hide()
                infoSV.hideButton()
                val assessmentTakenSV = assessmentsTakenSvBuilder.build()
                assessmentTakenSV.show()
                assessmentTakenSV.overrideButtonClick {
                    assessmentTakenSV.hide()
                    assessmentTakenSV.hideButton()
                }
            }
        }
        if (!retry) presenter?.onTutorialSeen()
    }
    //</editor-fold>

    internal interface ProfileClickEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
