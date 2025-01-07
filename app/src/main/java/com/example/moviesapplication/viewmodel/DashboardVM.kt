package com.appsologix.verepass.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.appsologix.verepass.repositories.MoviesRepository
import com.example.moviesapplication.models.MoviesResponse

class DashboardVM : ViewModel() {
    private val TAG: String = "DashboardVM"

    val dataSource: MutableLiveData<String> = MutableLiveData()
    val offlineRequest: MutableLiveData<OfflineMoviesRequest> = MutableLiveData()

    val offlineMoviesResponse:
            LiveData<MoviesResponse> = offlineRequest.switchMap()   //Transformations.switchMap(offlineRequest)
    {
        offlineRequest.value?.let { request ->
            MoviesRepository.getOfflineMovies(offlineRequest.value!!)

        }
    }

    fun getMovies(source: String) {

        source?.let { source ->
            dataSource.value = source
        }
    }//end of method getMovies....

    fun getOfflineMovies(request:OfflineMoviesRequest) {

        request?.let { source ->
            offlineRequest.value = source
        }
    }//end of method getMovies....
}