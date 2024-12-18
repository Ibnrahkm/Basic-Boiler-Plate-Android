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
import com.ibt.ava.databinding.LayoutPodcastRowBinding
import com.ibt.ava.databinding.LayoutStoriesRowBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.podcastchannels.Datum
import com.ibt.ava.service.model.podcasts.Channel
import com.ibt.ava.service.model.podcasts.Podcast
import com.ibt.ava.service.model.podcasts.PodcastArrayData
import com.ibt.ava.service.model.stories.Story
import com.ibt.ava.service.model.weather.List
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.fragment.MediaPlayerFragment
import com.ibt.ava.view.ui.fragment.PodcastPlayerFragment
import com.ibt.ava.view.ui.fragment.StoryFragment
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners


/*
* Transaction history recycler view adapter
* */
class PodcastsAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Podcast> = ArrayList<Podcast>()
    var clickedData: MutableLiveData<Podcast> = MutableLiveData<Podcast>()
    lateinit var channel: Channel
    var currentSize = 0
    var pageCounter = 1
    var loadMore = MutableLiveData<Int>()
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
                    LayoutInflater.from(context), R.layout.layout_footer, parent, false
                )
            )
        } else {
            TransactionViewHolder(
                DataBindingUtil.inflate<LayoutPodcastRowBinding>(
                    LayoutInflater.from(context), R.layout.layout_podcast_row, parent, false
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
            if (currentSize > 0) {
                holder.binding.progressSpinner.visibility = View.GONE
                loadMore.value = ++pageCounter
            } else {
                holder.binding.progressSpinner.visibility = View.GONE
            }
        } else if (holder is TransactionViewHolder) {
            holder.binding.model = dataList[position]

            holder.binding.root.setOnClickListener {
                var f = PodcastPlayerFragment()
                var b = Bundle()
                var p = PodcastArrayData(dataList, channel)
                b.putString(
                    "data", Gson().toJson(p)
                )
                b.putString("pos", position.toString())
                f.arguments = b
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: PodcastArrayData) {
        currentSize = dataList.podcast.size
        pageCounter=1
        this.dataList = dataList.podcast
        this.channel = dataList.channel
        notifyDataSetChanged()
    }
    /**
     * provide  data to the adapter
     */
    fun provideDataInfinite(dataList: PodcastArrayData) {
        var last = itemCount
        currentSize = dataList.podcast.size
        this.dataList.addAll(dataList.podcast)
        notifyItemRangeInserted(last, dataList.podcast.size)
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

    inner class TransactionViewHolder constructor(internal var binding: LayoutPodcastRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}