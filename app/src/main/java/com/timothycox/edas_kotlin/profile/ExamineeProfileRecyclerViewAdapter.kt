package com.timothycox.edas_kotlin.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.profile.ExamineeProfileRecyclerViewAdapter.ViewHolder

class ExamineeProfileRecyclerViewAdapter(var assessmentList: List<Assessment>) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var assessmentTimestampText: TextView = view.findViewById(R.id.listingsNameTextView)
        internal var completionStatusText: TextView = view.findViewById(R.id.listingsSubTextView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_assessment_listing_row, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val assessment = assessmentList[position]
        holder.assessmentTimestampText.text = assessment.timestamp
        if (assessment.isCompleted != null && assessment.isCompleted!!) {
            holder.completionStatusText.text = "Completed"
        } else
            holder.completionStatusText.text = "Incomplete"

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return assessmentList.size
    }
}
