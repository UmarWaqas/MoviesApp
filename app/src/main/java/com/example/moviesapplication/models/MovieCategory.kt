package com.example.moviesapplication.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
//import kotlinx.parcelize.Parcelize
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieCategory (
    var id : Int,
    val name : String,
    var movieList :List<Movies>,
    var laoutId:Int

    ): Parcelable