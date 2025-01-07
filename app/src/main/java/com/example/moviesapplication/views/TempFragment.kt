package com.example.moviesapplication.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appsologix.verepass.viewmodel.DashboardVM
import  com.example.moviesapplication.R
import com.example.moviesapplication.databinding.FragmentCodeBinding
import com.example.moviesapplication.databinding.FragmentGeeksBinding
import com.example.moviesapplication.databinding.FragmentTempBinding

class TempFragment : Fragment() {
    // inflate the layout
    private lateinit var binding: FragmentTempBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTempBinding.inflate(inflater,container,false)


        return binding.root
    }
}