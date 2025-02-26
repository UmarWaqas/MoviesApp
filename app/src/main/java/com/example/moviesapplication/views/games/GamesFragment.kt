package com.example.moviesapplication.views.games

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.appsologix.verepass.viewmodel.DashboardVM
import  com.example.moviesapplication.R
import com.example.moviesapplication.adapters.CategoriesAdapter
import com.example.moviesapplication.adapters.GamesMainAdapter
import com.example.moviesapplication.databinding.FragmentGamesBinding
import com.example.moviesapplication.databinding.FragmentGeeksBinding
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.MoviesResponse
import com.example.moviesapplication.views.custom.DialogUtils

class GamesFragment(val layoutId:Int=R.layout.item_a) : Fragment() {
    // inflate the layout

    private lateinit var binding: FragmentGamesBinding

    private lateinit var dashboardVM: DashboardVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentGamesBinding.inflate(inflater,container,false)
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
                val movies = moviesData.movies.filter { it.Year.toInt() >=1990 && it.Genre.contains("Animation") }
            //    var layoutId=R.layout.item_home_viewpager
                var category = MovieCategory(1,"Brows Categories ",movies.shuffled(), R.layout.item_games_category)
                dataList.add(category)

                category = MovieCategory(1,"Puzzle Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"Sports Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"Editor Choice Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"More Exciting Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"Casual Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"Most Played Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"Action Games",movies.shuffled(), layoutId,true)
                dataList.add(category)


                category = MovieCategory(1,"Arcade  Games",movies.shuffled(), layoutId,true)
                dataList.add(category)



                  binding.rvGames.layoutManager =LinearLayoutManager(requireActivity())
                val adapter = GamesMainAdapter(requireActivity(),dataList)
                  binding.rvGames.adapter = adapter
                binding.rvGames.setNestedScrollingEnabled(false)

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