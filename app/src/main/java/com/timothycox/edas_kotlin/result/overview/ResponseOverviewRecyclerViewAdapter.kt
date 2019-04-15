package com.timothycox.edas_kotlin.result.overview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Response
import com.timothycox.edas_kotlin.result.overview.ResponseOverviewRecyclerViewAdapter.ViewHolder


class ResponseOverviewRecyclerViewAdapter(
    val assessmentTaken: Assessment,
    val responseList: List<Response>
) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var questionNumberText: TextView = view.findViewById(R.id.questionNumberTextView)
        internal var questionText: TextView = view.findViewById(R.id.questionTextView)
        internal var answerText: TextView = view.findViewById(R.id.answerTextView)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_response_overview_listing_row, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val response = responseList[position]
        holder.questionNumberText.text = response.timestamp
        if (response.isCompleted!!) {
            holder.questionText.text = "Completed"
        } else
            holder.questionText.text = "Incomplete"

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return responseList.size
    }
}
