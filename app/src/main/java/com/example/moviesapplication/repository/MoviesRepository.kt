package com.appsologix.verepass.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.appsologix.verepass.models.request.OfflineMoviesRequest
import com.example.moviesapplication.models.Movies
import com.example.moviesapplication.models.MoviesResponse
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream

object MoviesRepository {

    var job: CompletableJob? = null


    fun getOnlineMovies(token: String): LiveData<MoviesResponse> {

        return object : LiveData<MoviesResponse>() {
            override fun onActive() {
                super.onActive()
            }
        }

    }//end of method getOnlineMovies....

    fun getOfflineMovies(request: OfflineMoviesRequest): LiveData<MoviesResponse> {
        job = Job()
        return object : LiveData<MoviesResponse>() {
            override fun onActive() {
                super.onActive()
                job?.let { offlineJob ->
                    CoroutineScope(IO + offlineJob).launch {
                        val jsonData = loadJSONFromFile(request.context)
                        if (jsonData != null) {
                            CoroutineScope(Main).launch {
                                value = Gson().fromJson(jsonData, MoviesResponse::class.java)
                                Log.d("", "")
                            }

                        }
                    }
                }
            }
        }
    }//end of method getOfflineMovies....

    fun loadJSONFromFile(context: Context): String? {
        var json: String? = null
        try {
            val stream: InputStream = context.getAssets().open("movies.json")
            val size: Int = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            json = String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    fun cancleJobs() {
        job?.cancel()
    }//end of method cancleJobs....

}//end of class AuthRepository....