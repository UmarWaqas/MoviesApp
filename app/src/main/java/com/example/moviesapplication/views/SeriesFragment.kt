package com.example.moviesapplication.views

import android.os.Bundle
import android.os.CountDownTimer
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
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.appsologix.verepass.viewmodel.DashboardVM
import  com.example.moviesapplication.R
import com.example.moviesapplication.adapters.CategoriesAdapter
import com.example.moviesapplication.databinding.FragmentGeeksBinding
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.MoviesResponse
import com.example.moviesapplication.views.custom.DialogUtils

class SeriesFragment(val layoutId:Int) : Fragment() {
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

       // DialogUtils.ShowProgress(requireActivity(),"Loading Movies....")
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
            //    var layoutId=R.layout.item_home_viewpager
                var category = MovieCategory(1,"slider",movies.shuffled(), layoutId)
                dataList.add(category)

             //   layoutId=R.layout.item_b
                category = MovieCategory(1,"New Release",movies.shuffled(), layoutId)
                dataList.add(category)

               // layoutId=R.layout.item_a
                category = MovieCategory(1,"Trending Now",movies.shuffled(), layoutId)
                dataList.add(category)


              //  layoutId=R.layout.item_d
                category = MovieCategory(1,"Top 20 Series",movies.shuffled(), layoutId)
                dataList.add(category)
                category = MovieCategory(1,"Recommended Shows for you",movies.shuffled(), layoutId)
                dataList.add(category)
                category = MovieCategory(1,"Recommended Shows - New",movies.shuffled(), layoutId)
                dataList.add(category)

                category = MovieCategory(1,"Top Trending Shows",movies.shuffled(), layoutId)
                dataList.add(category)

                category = MovieCategory(1,"Editors Choice",movies.shuffled(), layoutId)
                dataList.add(category)

                category = MovieCategory(1,"Most Rated Indian Series",movies.shuffled(), layoutId)
                dataList.add(category)
                category = MovieCategory(1,"Popular Right Now",movies.shuffled(), layoutId)
                dataList.add(category)
                category = MovieCategory(1,"South Indian Special",movies.shuffled(), layoutId)
                dataList.add(category)

                  binding.rvCategories.layoutManager =LinearLayoutManager(requireActivity())
                val adapter = CategoriesAdapter(requireActivity(),dataList)
                  binding.rvCategories.adapter = adapter
                binding.rvCategories.setNestedScrollingEnabled(false)

                Log.d("","")
                timer = object : CountDownTimer(4000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        // homeMainAdapter.showNextScreen()
                        adapter.showNextScreen()
                        timer?.start()
                    }
                }
                timer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }//end of method initMoviesData....

    var timer: CountDownTimer? = null
}