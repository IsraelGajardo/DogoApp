package com.dogoapp.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dogoapp.R

class BreedImagesAdapter(
    private val context: Context,
    private val mValues: List<String>,
    private val callback: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_DATA = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderData(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_breed_images_dogos_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_DATA -> {
                val dataHolder = holder as ViewHolderData
                Glide.with(context).load(mValues[position]).into(dataHolder.dogoImageView)
                dataHolder.dogoImageView.setOnClickListener {
                    callback.onClick(
                        mValues[position]
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_DATA
    }

    class ViewHolderData(mView: View) : RecyclerView.ViewHolder(mView) {
        val dogoImageView: ImageView = mView.findViewById(R.id.image)
    }

    interface OnClickListener {
        fun onClick(url: String)
    }
}