package com.example.moviesapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapplication.R
import com.example.moviesapplication.databinding.ItemHomeViewpagerBinding
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.Movies
import java.lang.Exception
import kotlin.concurrent.fixedRateTimer

/**
 * Created by Umar on 21-Jan-25.
 */
class GamesMainAdapter(mContext:Context, dataList:List<MovieCategory>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  var mParents: List<MovieCategory>

    private var context: Context = mContext

    lateinit var adapter : ViewsSliderAdapter
    private lateinit var viewHolderItemSlider : ItemHomeSliderView
   init {
       mParents=dataList
   }
    inner class ItemHomeSliderView(itemView: ItemHomeViewpagerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding = itemView
    }
    private fun multiViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        return when (viewType) {
            0 ->{
                val binding: ItemHomeViewpagerBinding = ItemHomeViewpagerBinding.inflate(
                    LayoutInflater.from(parent.context),
                   parent, false
                )
                ItemHomeSliderView(binding)
            }
            else -> {
                val contactView: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_categories, parent, false)
                 ViewHolder(contactView)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return /*if (position==0) 0 else*/ 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //LayoutInflater inflater = LayoutInflater.from(getContext());

        //return multiViewHolder( parent,  currentOnboarding);
        return multiViewHolder(parent, viewType)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            bindMultiViews(holder, position, getItemViewType(position))
        } catch (e: Exception) {
            e.printStackTrace()
           // Utilities.showMessage(context, e.localizedMessage)
        }
    }


    override fun getItemCount(): Int {
        return mParents.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    fun showNextScreen(){
        if (mParents.isNotEmpty()){
            if (::viewHolderItemSlider.isInitialized){
                var currentPosition = viewHolderItemSlider.binding.viewPager2.currentItem + 1
                if (currentPosition>adapter.itemCount-1){
                    currentPosition=0
                }
                viewHolderItemSlider.binding.viewPager2.setCurrentItem(currentPosition, true)
            }
        }
    }

    private fun bindMultiViews(holder: RecyclerView.ViewHolder?, position: Int, type: Int) {

                if (mParents.size == 0) {
                    return
                }
                try {
                    if (holder is ItemHomeSliderView) {
                        val category: MovieCategory = mParents.get(position)
                        viewHolderItemSlider = holder
                        adapter = ViewsSliderAdapter(category.movieList.subList(0,6))


                        holder.binding.viewPager2.adapter = adapter
                        holder.binding.viewPager2.isUserInputEnabled = true
                        holder.binding.viewPager2.offscreenPageLimit = (3)
                        holder.binding.viewPager2.isNestedScrollingEnabled = false
                        holder.binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                        //holder.binding.viewPager2.setPageTransformer(MarginPageTransformer(1500));
                        /*TabLayoutMediator(
                            holder.binding.tabLayout,
                            holder.binding.viewPager2
                        ) { tab, _ -> tab.view.isClickable = false }.attach()*/
                        holder.binding.customIndicator.setViewPager(holder.binding.viewPager2)


                    }else{
                        val viewHolder = holder as ViewHolder
                        val linearLayoutManager = LinearLayoutManager(
                            context, LinearLayoutManager.HORIZONTAL, false
                        )
                        val grid= GridLayoutManager(context,2,GridLayoutManager.HORIZONTAL,false)
                        val category: MovieCategory = mParents.get(position)
                        var id = category.laoutId
                        holder.tvCategoryTitle.text = category.name
                        val mAdapter =
                            MoviesAdapter(
                                context,
                                category.movieList.subList(0,10),id,category.hideDetails
                            )
                        viewHolder!!.rvMovies.adapter = mAdapter
                        viewHolder.rvMovies.setHasFixedSize(true)
                        if (id==R.layout.item_f)
                            viewHolder.rvMovies.layoutManager = linearLayoutManager//grid
                        else
                            viewHolder.rvMovies.layoutManager = linearLayoutManager
                        viewHolder.rvMovies.isNestedScrollingEnabled = false
                    }



                } catch (e: Exception) {
                    e.printStackTrace()
                   // Utilities.showMessage(context, e.localizedMessage)
                }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var rvMovies:RecyclerView
        var tvCategoryTitle:TextView

        init {
            rvMovies = itemView.findViewById(R.id.rvMovies)
            tvCategoryTitle = itemView.findViewById(R.id.tvCategoryTitle)
        }
    }

}
