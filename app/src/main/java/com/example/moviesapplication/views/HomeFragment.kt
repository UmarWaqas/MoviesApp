package com.example.moviesapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviesapplication.databinding.FragmentCodeBinding
import com.example.moviesapplication.views.activities.MainActivity.ViewPagerAdapter

class HomeFragment : Fragment() {
    // inflate the layout
    private lateinit var binding: FragmentCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeBinding.inflate(inflater,container,false)

        val adapter = ViewPagerAdapter(childFragmentManager)

        // add fragment to the list
        adapter.addFragment(GeeksFragment(), "GeeksForGeeks")
        adapter.addFragment(TempFragment(), "Code Chef")
        adapter.addFragment(GeeksFragment(), "GeeksForGeeks")
        adapter.addFragment(TempFragment(), "Cheat Code")

        // Adding the Adapter to the ViewPager
        binding.viewPager.adapter = adapter

        // bind the viewPager with the TabLayout.
        binding.tabs.setupWithViewPager( binding.viewPager)
        return binding.root
    }
}