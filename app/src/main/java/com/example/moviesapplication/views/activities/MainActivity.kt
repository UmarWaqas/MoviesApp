package com.example.moviesapplication.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.appsologix.verepass.viewmodel.DashboardVM
import com.example.moviesapplication.R
import com.example.moviesapplication.adapters.CategoriesAdapter
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.Movies
import com.example.moviesapplication.models.MoviesResponse
import com.example.moviesapplication.views.custom.DialogUtils
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardVM: DashboardVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dashboardVM = ViewModelProvider(this).get(DashboardVM::class.java)
        val horizontalLayoutManagaer=
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.layoutManager = horizontalLayoutManagaer

       fetchOfflineData()
    }

    private fun initMoviesData(moviesData: MoviesResponse) {
        if (moviesData != null) {
            //var array:JsonArray  = JsonArray(json)
            try {
               // val data = Gson().fromJson(json, MoviesResponse::class.java)
                Log.d("","")

                val dataList:ArrayList<MovieCategory> =ArrayList()

                var layoutId=R.layout.item_a
                var category = MovieCategory(1,"Trending",moviesData.movies, layoutId)
                dataList.add(category)

                layoutId=R.layout.item_b
                category = MovieCategory(1,"Previews",moviesData.movies, layoutId)
                dataList.add(category)

                layoutId=R.layout.item_c
                category = MovieCategory(1,"Today Trending",moviesData.movies, layoutId)
                dataList.add(category)

                layoutId=R.layout.item_d
                category = MovieCategory(1,"Podcast",moviesData.movies, layoutId)
                dataList.add(category)

                layoutId=R.layout.item_e
                category = MovieCategory(1,"For You",moviesData.movies, layoutId)
                dataList.add(category)

                layoutId=R.layout.item_f
                category = MovieCategory(1,"Listen Again",moviesData.movies, layoutId)
                dataList.add(category)

                layoutId=R.layout.item_c
                category = MovieCategory(1,"Action Movies",moviesData.movies, layoutId)
                dataList.add(category)

                binding.rvCategories.layoutManager =LinearLayoutManager(this)
                binding.rvCategories.adapter = CategoriesAdapter(this,dataList)
                binding.rvCategories.setNestedScrollingEnabled(false);
                Log.d("","")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }//end of method initMoviesData....

    private fun fetchOfflineData() {

        val observer: Observer<MoviesResponse> = Observer { response ->
            response?.let { data->
                initMoviesData(data)
            }
            DialogUtils.HideDialog()
        }

        DialogUtils.ShowProgress(this,"Loading Movies....")
        Handler(Looper.getMainLooper())
            .postDelayed({
                // DialogUtils.HideDialog()
                dashboardVM.offlineMoviesResponse.observe(this@MainActivity, observer)
                dashboardVM.getOfflineMovies(OfflineMoviesRequest("offline",this@MainActivity))
            }, 2000)

    }//end of method fetchOfflineData....


}