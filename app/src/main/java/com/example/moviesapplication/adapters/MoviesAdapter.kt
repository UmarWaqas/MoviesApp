package com.example.moviesapplication.adapters

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.moviesapplication.R
import com.example.moviesapplication.models.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Umar on 21-Feb-22.
 */
class MoviesAdapter(mContext: Context, mParents: List<Movies>,layout:Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private var mParents: List<Movies>

    private val context: Context
    private val layoutId: Int

    private fun multiViewHolder(parent: ViewGroup, viewType: Int=0): RecyclerView.ViewHolder? {
         when (viewType) {
            R.layout.item_aa -> {
                val contactView: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_aa, parent, false)
                return ViewHolder(contactView)
            }
            R.layout.item_a -> {
                val contactView: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_a, parent, false)
                return ViewHolder(contactView)
            }
            R.layout.item_m -> {
                val contactView: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_m, parent, false)
                return ViewHolder(contactView)
            }
            R.layout.item_b -> {
                val view: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_b, parent, false)
                return ViewHolder(view)
            }
            R.layout.item_c -> {
                val contactView: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_c, parent, false)
                return ViewHolder(contactView)
            }
            R.layout.item_d -> {
                val view: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_d, parent, false)
                return ViewHolder(view)
            }
            R.layout.item_e -> {
                val view: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_e, parent, false)
                return ViewHolder(view)
            }
            R.layout.item_f -> {
                val view: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_f, parent, false)
                return ViewHolder(view)
            }
            else ->{
                val contactView: View = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_categories, parent, false)
                return ViewHolder(contactView)

            }

        }
        return null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //LayoutInflater inflater = LayoutInflater.from(getContext());
        return multiViewHolder(parent,viewType)!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            bindMultiViews(holder, position)
        } catch (e: Exception) {
            e.printStackTrace()
           // Utilities.showMessage(context, e.localizedMessage)
        }
    }



    override fun getItemCount(): Int {
        if (layoutId==R.layout.item_f)
            return 11
        else
        return mParents.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    private fun bindMultiViews(holder: RecyclerView.ViewHolder?, position: Int, type: Int =0) {
     //   Collections.shuffle(this.mParents)
        val model: Movies = mParents.get(position)
        val viewHolder = holder as ViewHolder?
        var url:String? =null
        viewHolder?.setIsRecyclable(false)

        if (viewHolder?.ivImage != null && layoutId!=R.layout.item_d) {


            if(model.Poster.contains("http://")){
                url = model.Poster.replace("http://","https://")
               // url = "https://ia.media-imdb.com/images/M/MV5BMTYwOTEwNjAzMl5BMl5BanBnXkFtZTcwODc5MTUwMw@@._V1_SX300.jpg"//model.Poster.replace("http://","https://")

                Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(15))
                    .placeholder(R.drawable.placeholder).into(viewHolder.ivImage);

            }else{
                Glide.with(context).load(model.Poster).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(15))
                    .placeholder(R.drawable.placeholder).into(viewHolder.ivImage)
            }

            //val url = model.Poster.replace(/^http:\/\//i, 'https://');

        }

        if (viewHolder?.tvTitle != null) {
            viewHolder.tvTitle.text = model.Title

        }

        when (layoutId) {
            R.layout.item_d -> {

                viewHolder?.tvTitle?.isVisible = true
                viewHolder?.tvTitle?.text = "Hindi"
            }
            R.layout.item_m -> {

             //   viewHolder?.tvTitle?.isVisible = false
            }
           /* R.layout.item_a -> {

            }*/
            R.layout.item_b -> {

                viewHolder?.tvTitle?.text = model.Title
            }
            R.layout.item_e -> {
                viewHolder?.tvTitle?.isVisible = false
                viewHolder?.tvGenre?.isVisible = false
            }
           /* R.layout.item_c -> {

            }
            R.layout.item_d -> {

            }
            R.layout.item_e -> {

            }
            R.layout.item_f -> {

            }*/
            else ->{
                viewHolder?.tvTitle?.isVisible = true
                viewHolder?.tvGenre?.isVisible = true
                viewHolder?.tvTitle?.text = model.Title
                viewHolder?.tvGenre?.text = model.Genre +" "+
                        context.getString(R.string.seperator) +" "+
                        model.Runtime +" "+ context.getString(R.string.seperator)+" "+
                        model.Year

            }

        }


    } //end of method bindMultiViews....



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var  ivImage: ImageView
        lateinit var tvTitle: TextView
        lateinit var tvGenre: TextView

        init {
                var imageId=R.id.ivImage
                var titleId=R.id.tvTitle
                var tvGenreId=R.id.tvGenre
            try {
                if (imageId !=null)
                    ivImage = itemView.findViewById(R.id.ivImage)
                if (titleId !=null)
                    tvTitle = itemView.findViewById(R.id.tvTitle)
                if (tvGenreId !=null)
                    tvGenre = itemView.findViewById(R.id.tvGenre)
            } catch (e: Exception) {
            }
        }
    }

    init {
        this.mParents = mParents
        context = mContext
        layoutId=layout

    }
} //end of class FixturesInnerAdapter....
