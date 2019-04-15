package com.timothycox.edas_kotlin.result.overview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Response

class ResponseOverviewActivity : AppCompatActivity(), ResponseOverviewContract.View {

    //<editor-fold defaultstate="collapsed" desc="Activity Lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="RecyclerView">
    override fun configureRecyclerView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRecyclerViewAdapter(adapter: ResponseOverviewRecyclerViewAdapter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    //</editor-fold>

    override fun populateUIWithData(response: Response) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTutorial(retry: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal interface ResponseOverviewClickEvents {
        fun navigateTo(id: Int, bundle: Bundle?)
    }
}
