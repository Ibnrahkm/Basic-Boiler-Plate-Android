package com.ibt.ava.view.adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFooterBinding
import com.ibt.ava.databinding.LayoutHomeScreenPodcastRowBinding
import com.ibt.ava.databinding.LayoutNinthRecRowRowBinding
import com.ibt.ava.databinding.LayoutStoriesRowBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.podcast.Podcast
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.fragment.MediaPlayerFragment
import com.ibt.ava.view.ui.fragment.PodcastChannelsFragment


/*
* Transaction history recycler view adapter
* */
class HomePodcastAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Podcast> = ArrayList<Podcast>()
    var clickedData: MutableLiveData<Podcast> = MutableLiveData<Podcast>()

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }

    /**
     * returning 2 types of views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            FooterViewHolder(
                DataBindingUtil.inflate<LayoutFooterBinding>(
                    LayoutInflater.from(context),
                    R.layout.layout_footer, parent, false
                )
            )
        } else {
            TransactionViewHolder(
                DataBindingUtil.inflate<LayoutHomeScreenPodcastRowBinding>(
                    LayoutInflater.from(context),
                    R.layout.layout_home_screen_podcast_row, parent, false
                )
            )
        }
    }

    /**
     * return total data list size with extra one for showing loading spinner
     */
    override fun getItemCount(): Int {
        return dataList.size + 1
    }

    /**
     * called on every item
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        /**
         * if its loading spinner and has more then calling next data
         * else hidden loading spinner
         */
        if (holder is FooterViewHolder) {
            holder.binding.progressSpinner.visibility = View.GONE
        } else if (holder is TransactionViewHolder) {
            holder.binding.model = dataList[position]

            holder.binding.root.setOnClickListener {

                var f = PodcastChannelsFragment()
                var b = Bundle()
                b.putString(
                    "data",
                    Gson().toJson(dataList[position])
                )
                f.arguments = b
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: List<Podcast>) {
        this.dataList = ArrayList(dataList)
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM
    }

    /**
     * helper method to determine the loading spinner position
     */
    private fun isPositionFooter(position: Int): Boolean {
        return position == dataList.size
    }


    /**
     * inner class of item view holder
     */

    inner class TransactionViewHolder constructor(internal var binding: LayoutHomeScreenPodcastRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}