package com.timothycox.edas_kotlin.examinees

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
import com.timothycox.edas_kotlin.model.User
import kotlinx.android.synthetic.main.activity_examinees.*

class ExamineesListActivity : AppCompatActivity(), ExamineesListContract.View {

    private var presenter: ExamineesListPresenter? = null
    private var navigator: ExamineesListNavigator? = null
    private var adapter: ExamineesListRecyclerViewAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examinees)
        presenter = ExamineesListPresenter(
            this,
            intent.getBundleExtra("mainBundle").getSerializable("user") as User
        )
        navigator = ExamineesListNavigator(this)
        presenter?.create()
        examineesAddButton.setOnClickListener { onClickAddExaminee() }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RecyclerView"
    override fun configureRecyclerView() {
        examineesRecyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        examineesRecyclerView?.layoutManager = layoutManager
        examineesRecyclerView?.itemAnimator = DefaultItemAnimator()
        examineesRecyclerView?.addOnItemTouchListener(
            RecyclerTouchListener(applicationContext,
                examineesRecyclerView!!,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val bundle = Bundle()
                        bundle.putSerializable("selectedExaminee", (examineesRecyclerView.adapter!! as ExamineesListRecyclerViewAdapter).examineeList[position])
                        presenter?.onExamineeSelected(bundle)
                    }

                    override fun onLongClick(view: View?, position: Int) {

                    }
                })
        )
    }

    override fun setRecyclerViewAdapter(adapter: ExamineesListRecyclerViewAdapter) {
        this.adapter = adapter
        examineesRecyclerView?.adapter = adapter
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Click Events"
    override fun onClickAddExaminee() {
        presenter?.onAddExaminee()
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tutorial"
    //todo change strings
    override fun showTutorial(retry: Boolean) {
        val introSV = ShowcaseView.Builder(this)
            .setContentTitle("Examinees")
            .setContentText("This screens shows the list of examinees registered to your account.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
            .build()
        val listItemSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.examineesRecyclerView, this))
            .setContentTitle("Examinee list")
            .setContentText("Each examinee will appear in a list here.")
            .setStyle(R.style.CustomShowcaseThemeNext)
            .withHoloShowcase()
            .hideOnTouchOutside()
        val addExamineeSvBuilder = ShowcaseView.Builder(this)
            .setTarget(ViewTarget(R.id.examineesAddButton, this))
            .setContentTitle("Add new examinee")
            .setContentText("To add a new examinee, click this button.")
            .setStyle(R.style.CustomShowcaseThemeDone)
            .withHoloShowcase()
            .hideOnTouchOutside()
        if (!introSV.isShowing) introSV.show()
        introSV.overrideButtonClick {
            introSV.hide()
            introSV.hideButton()
            val listSV = listItemSvBuilder.build()
            listSV.show()
            listSV.overrideButtonClick {
                listSV.hide()
                listSV.hideButton()
                val addExamineeSV = addExamineeSvBuilder.build()
                addExamineeSV.show()
                addExamineeSV.overrideButtonClick {
                    addExamineeSV.hide()
                    addExamineeSV.hideButton()
                }
            }
        }
        if (!retry) presenter?.onTutorialSeen()
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

        if (id == R.id.action_retry_tutorial) {
            presenter?.retryTutorial()
        }
        return super.onOptionsItemSelected(item)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Navigation"
    override fun navigateToExamineeCreator(bundle: Bundle?) {
        navigator?.navigateTo(ExamineesListNavigator.EXAMINEE_CREATOR, bundle)
    }

    override fun navigateToExamineeProfile(bundle: Bundle?) {
        navigator?.navigateTo(ExamineesListNavigator.EXAMINEE_PROFILE, bundle)
    }
    //</editor-fold>

    internal interface ExamineesScreenEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
