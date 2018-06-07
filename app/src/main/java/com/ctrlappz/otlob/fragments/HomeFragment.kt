package com.ctrlappz.otlob.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import com.ctrlappz.otlob.R
import com.ctrlappz.otlob.adapters.CategoriesAdapter
import com.ctrlappz.otlob.interfaces.ServicesApi
import com.ctrlappz.otlob.models.CategoryModel
import com.ctrlappz.otlob.utils.Helper
import com.ctrlappz.otlob.utils.Links
import kotlinx.android.synthetic.main.activity_workers.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var servicesApi: ServicesApi
    private lateinit var categoriesList: ArrayList<CategoryModel>
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        rootView.searchSV.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchList = categoriesList.filter { categoryModel ->
                    categoryModel.name.contains(v.text.toString())
                }

                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = CategoriesAdapter(ArrayList(searchList), context)
                true
            } else {
                false
            }
        }

        recyclerView = rootView.findViewById(R.id.categoriesRV)
        val layoutManager = GridLayoutManager(context, 3)

        recyclerView.layoutManager = layoutManager

        val retrofit = Retrofit.Builder()
                .baseUrl(Links.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        servicesApi = retrofit.create<ServicesApi>(ServicesApi::class.java)

        getCategories()

        return rootView

    }

    private fun getCategories() {
        val connection = servicesApi.getServices(Links.API + Links.SERVICES)
        connection.enqueue(object : Callback<ArrayList<CategoryModel>> {
            override fun onResponse(call: Call<ArrayList<CategoryModel>>?, response: Response<ArrayList<CategoryModel>>?) {
                if (response!!.isSuccessful) {
                    categoriesList = response.body()!!

                    categoriesAdapter = CategoriesAdapter(categoriesList, context)
                    recyclerView.adapter = categoriesAdapter
                } else {
                    Helper.getErrorMessage(context, response)
                }
            }

            override fun onFailure(call: Call<ArrayList<CategoryModel>>?, t: Throwable?) {

            }


        })
    }

}
