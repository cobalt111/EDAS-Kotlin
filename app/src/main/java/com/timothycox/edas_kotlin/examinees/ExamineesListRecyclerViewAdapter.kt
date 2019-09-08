package com.timothycox.edas_kotlin.examinees

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Examinee

class ExamineesListRecyclerViewAdapter// Provide a suitable constructor (depends on the kind of dataset)
    (internal var examineeList: List<Examinee>) : RecyclerView.Adapter<ExamineesListRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var nameText: TextView = view.findViewById(R.id.listingsNameTextView)
        internal var ageText: TextView = view.findViewById(R.id.listingsSubTextView)
        internal var ageLabel: TextView = view.findViewById(R.id.examineeRowBoyAgeLabel)
        internal var boyFace: ImageView = view.findViewById(R.id.examineeRowBoyface)
        internal var girlFace: ImageView = view.findViewById(R.id.examineeRowGirlface)
        internal var neutralFace: ImageView = view.findViewById(R.id.examineeRowNeutralFace)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamineesListRecyclerViewAdapter.ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_examinees_listing_row, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val examinee = examineeList[position]
        holder.nameText.text = examinee.name
        holder.ageText.text = examinee.ageAsHumanReadable

        when {
            examinee.gender == "Male" -> {
                holder.girlFace.visibility = View.GONE
                holder.neutralFace.visibility = View.GONE
            }
            examinee.gender == "Female" -> {
                holder.boyFace.visibility = View.GONE
                holder.neutralFace.visibility = View.GONE
            }
            else -> {
                holder.boyFace.visibility = View.GONE
                holder.girlFace.visibility = View.GONE
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return examineeList.size
    }
}
