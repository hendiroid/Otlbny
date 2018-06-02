package com.ctrlappz.otlob.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.models.CategoryModel

class JobsAdapter(private val category: ArrayList<CategoryModel>) : RecyclerView.Adapter<JobsAdapter.JobsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): JobsHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_job, parent, false)
        return JobsHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobsHolder?, position: Int) {
        val job = category[position]
        holder!!.radioButton.text = job.name

    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class JobsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioButton = itemView.findViewById<RadioButton>(R.id.jobRB)
    }
}