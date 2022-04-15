package com.dogoapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogoapp.R

class BreedsAdapter(
    private val mValues: ArrayList<String>,
    private val callback: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    companion object {
        private const val VIEW_TYPE_EMPTY = 0
        private const val VIEW_TYPE_DATA = 1
    }

    private var mValuesFiltered: ArrayList<String> = mValues

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DATA)
            ViewHolderData(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_breeds_breed_view, parent, false)
            )
        else ViewHolderEmpty(
            LayoutInflater.from(parent.context).inflate(R.layout.empty_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_DATA -> {
                val dataHolder = holder as ViewHolderData
                dataHolder.breedsTextView.text = mValuesFiltered[position]

                dataHolder.breedsTextView.setOnClickListener {
                    callback.onClick(
                        mValuesFiltered[position]
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mValuesFiltered.size > 0) mValuesFiltered.size else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (mValuesFiltered.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_DATA
    }

    class ViewHolderData(mView: View) : RecyclerView.ViewHolder(mView) {
        val breedsTextView: TextView = mView.findViewById(R.id.breedName)
    }

    class ViewHolderEmpty(mView: View) : RecyclerView.ViewHolder(mView) {
    }

    interface OnClickListener {
        fun onClick(breed: String)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) {
                    mValuesFiltered = mValues
                } else {
                    val filteredList = ArrayList<String>()
                    mValues.filter {
                        (it.contains(constraint!!))
                    }.forEach { filteredList.add(it) }
                    mValuesFiltered = filteredList

                }
                return FilterResults().apply { values = mValuesFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mValuesFiltered = if (results?.values == null) {
                    ArrayList()
                } else {
                    results.values as ArrayList<String>
                }

                notifyDataSetChanged()
            }
        }
    }
}