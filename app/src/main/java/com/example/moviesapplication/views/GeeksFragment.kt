package com.example.moviesapplication.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.appsologix.verepass.viewmodel.DashboardVM
import  com.example.moviesapplication.R
import com.example.moviesapplication.adapters.CategoriesAdapter
import com.example.moviesapplication.databinding.FragmentGeeksBinding
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.MoviesResponse
import com.example.moviesapplication.views.activities.MainActivity
import com.example.moviesapplication.views.custom.DialogUtils
import com.google.android.material.tabs.TabLayout

class GeeksFragment : Fragment() {
    // inflate the layout

    private lateinit var binding: FragmentGeeksBinding

    private lateinit var dashboardVM: DashboardVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentGeeksBinding.inflate(inflater,container,false)
        dashboardVM = ViewModelProvider(this).get(DashboardVM::class.java)
        val horizontalLayoutManagaer=
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        //   binding.rvCategories.layoutManager = horizontalLayoutManagaer

        fetchOfflineData()

        return binding.root
    }
    private fun fetchOfflineData() {

        val observer: Observer<MoviesResponse> = Observer { response ->
            response?.let { data->
                   initMoviesData(data)
            }
            DialogUtils.HideDialog()
        }

        DialogUtils.ShowProgress(requireActivity(),"Loading Movies....")
        Handler(Looper.getMainLooper())
            .postDelayed({
                // DialogUtils.HideDialog()
                dashboardVM.offlineMoviesResponse.observe(this, observer)
                dashboardVM.getOfflineMovies(OfflineMoviesRequest("offline",requireContext()))
            }, 200)

    }//end of method fetchOfflineData....

    private fun initMoviesData(moviesData: MoviesResponse) {
        if (moviesData != null) {
            //var array:JsonArray  = JsonArray(json)
            try {
                // val data = Gson().fromJson(json, MoviesResponse::class.java)
                Log.d("","")

                val dataList:ArrayList<MovieCategory> =ArrayList()
                val movies = moviesData.movies.filter { it.Year.toInt() >=2005 }
                var layoutId=R.layout.item_a
                var category = MovieCategory(1,"Trending",movies.shuffled(), layoutId)
                dataList.add(category)

                layoutId=R.layout.item_b
                category = MovieCategory(1,"Previews",movies.shuffled(), layoutId)
                dataList.add(category)

                layoutId=R.layout.item_c
                category = MovieCategory(1,"Today Trending",movies.shuffled(), layoutId)
                dataList.add(category)

                layoutId=R.layout.item_d
                category = MovieCategory(1,"Podcast",movies.shuffled(), layoutId)
                dataList.add(category)

                layoutId=R.layout.item_e
                category = MovieCategory(1,"For You",moviesData.movies.shuffled(), layoutId)
                dataList.add(category)

                layoutId=R.layout.item_f
                category = MovieCategory(1,"Listen Again",moviesData.movies.shuffled(), layoutId)
                dataList.add(category)

                layoutId=R.layout.item_c
                category = MovieCategory(1,"Action Movies",moviesData.movies, layoutId)
                dataList.add(category)
                  binding.rvCategories.layoutManager =LinearLayoutManager(requireActivity())
                  binding.rvCategories.adapter = CategoriesAdapter(requireActivity(),dataList)
                binding.rvCategories.setNestedScrollingEnabled(false)
                Log.d("","")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }//end of method initMoviesData....
}