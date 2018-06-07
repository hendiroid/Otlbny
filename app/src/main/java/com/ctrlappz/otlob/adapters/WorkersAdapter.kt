package com.ctrlappz.otlob.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.activities.WorkerActivity
import com.ctrlappz.otlob.models.WorkerModel
import com.squareup.picasso.Picasso

class WorkersAdapter(private val workers: ArrayList<WorkerModel>, private val context: Context)
    : RecyclerView.Adapter<WorkersAdapter.WorkersHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WorkersHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_worker, parent, false)
        return WorkersHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkersHolder?, position: Int) {
        val workerModel = workers[position]
        holder!!.workerNameTV.text = workerModel.name
        holder.workerAddressTV.text = workerModel.address
        Picasso.get().load(workerModel.image).into(holder.workerIV)
        holder.rate.rating = workerModel.rate!!
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WorkerActivity::class.java)
            intent.putExtra("id", workerModel.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return workers.size
    }

    inner class WorkersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workerIV = itemView.findViewById<ImageView>(R.id.workerIV)!!
        val workerNameTV = itemView.findViewById<TextView>(R.id.workerNameTV)!!
        val workerAddressTV = itemView.findViewById<TextView>(R.id.locationTV)!!
        val rate = itemView.findViewById<RatingBar>(R.id.rate)!!
    }
}