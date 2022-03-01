package com.example.moviesapplication.adapters
/*import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;*/
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapplication.R
import com.example.moviesapplication.models.MovieCategory
import com.example.moviesapplication.models.Movies
import java.lang.Exception
import kotlin.concurrent.fixedRateTimer

/**
 * Created by Umar on 21-Feb-22.
 */
class CategoriesAdapter(mContext:Context, dataList:List<MovieCategory>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  var mParents: List<MovieCategory>

    private var context: Context


   init {
       context=mContext
       mParents=dataList
   }

    private fun multiViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        val contactView: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_categories, parent, false)
        return ViewHolder(contactView)


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


    private fun bindMultiViews(holder: RecyclerView.ViewHolder?, position: Int, type: Int) {

                if (mParents.size == 0) {
                    return
                }
                try {

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
                            category.movieList,id
                        )
                    viewHolder!!.rvMovies.adapter = mAdapter
                    viewHolder.rvMovies.setHasFixedSize(true)
                    if (id==R.layout.item_f)
                    viewHolder.rvMovies.layoutManager = grid
                    else
                        viewHolder.rvMovies.layoutManager = linearLayoutManager
                    viewHolder.rvMovies.isNestedScrollingEnabled = false

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
