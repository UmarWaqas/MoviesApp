package com.example.moviesapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.moviesapplication.databinding.ItemHomeSliderBinding
import com.example.moviesapplication.R
import com.example.moviesapplication.models.Movies

class ViewsSliderAdapter(private var homeSliderList: List<Any>?,private var type:Int=0) :
    RecyclerView.Adapter<ViewsSliderAdapter.SliderItem>() {

    lateinit var context:Context

    inner class SliderItem(itemView: ItemHomeSliderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderItem {
        val binding: ItemHomeSliderBinding =
            ItemHomeSliderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        context = parent.context
        return SliderItem(binding)
    }

    override fun onBindViewHolder(holder: SliderItem, position: Int) {
        val data = homeSliderList?.get(position) as Movies
        Glide.with(context).load(data.Poster).diskCacheStrategy(DiskCacheStrategy.ALL)
       //     .transform(RoundedCorners(15))
            .placeholder(R.drawable.placeholder).into(holder.binding.appCompatImageView2)
    }

    override fun getItemCount(): Int {
        return homeSliderList?.size ?: 0
    }



}