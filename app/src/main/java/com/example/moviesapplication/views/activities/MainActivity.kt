package com.example.moviesapplication.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer;
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.appsologix.verepass.viewmodel.DashboardVM
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ActivityMainBinding
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.MoviesResponse
import com.example.moviesapplication.views.custom.DialogUtils
import com.google.android.material.tabs.TabLayout
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dashboardVM: DashboardVM

    private lateinit var navController: NavController
    private lateinit var pager: ViewPager // creating object of ViewPager
    private lateinit var tab: TabLayout  // creating object of TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
      //  navController.graph.setStartDestination(R.id.homeFragment)
      //  dashboardVM = ViewModelProvider(this).get(DashboardVM::class.java)
        val horizontalLayoutManagaer=
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
     //   binding.rvCategories.layoutManager = horizontalLayoutManagaer

      // fetchOfflineData()

       /* val adapter = ViewPagerAdapter(supportFragmentManager)

        // add fragment to the list
        adapter.addFragment(GeeksFragment(), "GeeksForGeeks")
        adapter.addFragment(CodeFragment(), "Code Chef")
        adapter.addFragment(CodeFragment(), "Leet Code")
        adapter.addFragment(GeeksFragment(), "GeeksForGeeks")
        adapter.addFragment(CodeFragment(), "Cheat Code")

        // Adding the Adapter to the ViewPager
        binding.viewPager.adapter = adapter

        // bind the viewPager with the TabLayout.
        binding.tabs.setupWithViewPager( binding.viewPager)*/
    }

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

              //  binding.rvCategories.layoutManager =LinearLayoutManager(this)
              //  binding.rvCategories.adapter = CategoriesAdapter(this,dataList)
             //   binding.rvCategories.setNestedScrollingEnabled(false);
                Log.d("","")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }//end of method initMoviesData....

    private fun fetchOfflineData() {

        val observer: Observer<MoviesResponse> = Observer { response ->
            response?.let { data->
             //   initMoviesData(data)
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

    class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(supportFragmentManager) {

        // declare arrayList to contain fragments and its title
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            // return a particular fragment page
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            // return the number of tabs
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence{
            // return title of the tab
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            // add each fragment and its title to the array list
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }
}