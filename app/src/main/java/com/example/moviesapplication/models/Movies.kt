package com.example.moviesapplication.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
//import kotlinx.parcelize.Parcelize
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movies (
    val Title : String,
    val Year : String,
    val Rated : String,
    val Released : String,
    val Runtime : String,
    val Genre : String,
    val Director : String,
    val Writer : String,
    val Actors : String,
    val Plot : String,
    val Language : String,
    val Country : String,
    val Awards : String,
    val Poster : String,
    val Metascore : String,
    val ImdbRating : String,
    val ImdbVotes : String,
    val ImdbID : String,
    val Type : String,
    val Response : Boolean,
    val Images : List<String>
    ): Parcelable