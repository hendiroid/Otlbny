package com.ctrlappz.otlob.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.models.CategoryModel
import com.squareup.picasso.Picasso

class CategoriesAdapter(private val category: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoriesHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.item_category, parent, false)
        return CategoriesHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesHolder?, position: Int) {
        val category = category[position]
        holder!!.nameTV.text = category.name
        Picasso.get().load(category.image).into(holder.catIV)

        holder.itemView.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class CategoriesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTV = itemView.findViewById<TextView>(R.id.cetTV)!!
        val catIV = itemView.findViewById<ImageView>(R.id.catIV)!!
    }

}