package com.example.mvvm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm.R
import com.example.mvvm.models.NicePlace
import de.hdodenhof.circleimageview.CircleImageView


class RecyclerAdapter(
    context: Context,
    nicePlaces: List<NicePlace>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mNicePlaces: List<NicePlace> = ArrayList()
    private val mContext: Context
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
// Set the name of the 'NicePlace'
        (viewHolder as ViewHolder).mName.text = mNicePlaces[i].title
        // Set the image
        val defaultOptions: RequestOptions = RequestOptions()
            .error(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
        Glide.with(mContext)
            .setDefaultRequestOptions(defaultOptions)
            .load(mNicePlaces[i].imageUrl)
            .into(viewHolder.mImage)
    }

    override fun getItemCount(): Int {
        return mNicePlaces.size
    }

    private inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val mImage: CircleImageView
        val mName: TextView

        init {
            mImage = itemView.findViewById(R.id.image)
            mName = itemView.findViewById(R.id.image_name)
        }
    }

    init {
        mNicePlaces = nicePlaces
        mContext = context
    }
}