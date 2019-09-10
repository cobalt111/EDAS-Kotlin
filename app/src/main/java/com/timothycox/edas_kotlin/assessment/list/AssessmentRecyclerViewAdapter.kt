package com.timothycox.edas_kotlin.assessment.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Assessment

class AssessmentRecyclerViewAdapter(private var assessmentList: List<List<Assessment>>) : RecyclerView.Adapter<AssessmentRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var nameText: TextView = view.findViewById(R.id.listingsNameTextView)
        internal var dateText: TextView = view.findViewById(R.id.listingsSubTextView)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentRecyclerViewAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_assessment_listing_row, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val assessment = assessmentList[0][position]

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return assessmentList.size
    }
}
