package com.ibt.ava.view.adapter

import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFooterBinding
import com.ibt.ava.databinding.LayoutStoriesRowBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.stories.Story
import com.ibt.ava.service.model.weather.List
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.fragment.AllStoryFragment
import com.ibt.ava.view.ui.fragment.MediaPlayerFragment
import com.ibt.ava.view.ui.fragment.StoryFragment
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners


/*
* Transaction history recycler view adapter
* */
class StoriesAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Story> = ArrayList<Story>()
    var clickedData: MutableLiveData<Story> = MutableLiveData<Story>()

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
                DataBindingUtil.inflate<LayoutStoriesRowBinding>(
                    LayoutInflater.from(context), R.layout.layout_stories_row, parent, false
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

            if (position <= 1) {
                holder.binding.live.visibility = View.VISIBLE
            } else {
                holder.binding.live.visibility = View.GONE
            }

            if (position == 2) {
                holder.binding.divider.visibility = View.VISIBLE
            } else {
                holder.binding.divider.visibility = View.GONE
            }

            holder.binding.root.setOnClickListener {
                if (position == 0) {
                    var f = MediaPlayerFragment()
                    var b = Bundle()
                    b.putString(
                        "url",
                        "https://avamedia.live/cdn/ava.m3u8"
                    )
                    f.arguments = b
                    f.show((context as HomeActivity).supportFragmentManager, null)
                } else if (position == 1) {
                    var f = MediaPlayerFragment()
                    var b = Bundle()
                    b.putString(
                        "url", "https://a5.asurahosting.com:7090/radio.mp3"
                    )
                    f.arguments = b
                    f.show((context as HomeActivity).supportFragmentManager, null)

                } else {
                    var f = AllStoryFragment()
                    f.setDataList(ArrayList(dataList.subList(2,dataList.size)))
                    var b = Bundle()
                    b.putInt("position", position-2)
                    f.arguments = b
                    f.show((context as HomeActivity).supportFragmentManager, null)

                    /*var f = StoryFragment()
                    f.mainImage = dataList[position].thumbnail.toString()
                    f.mainTitle = dataList[position].title
                    var b = Bundle()
                    b.putString("data", Gson().toJson(dataList[position].items))
                    f.arguments = b
                    f.show((context as HomeActivity).supportFragmentManager, null)
                    */
                    /* StoryView.Builder((context as HomeActivity).supportFragmentManager)
                         .setStoriesList(dataList[position].storyLink) // Required
                         .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                         .setTitleText(dataList[position].storiesName) // Default is Hidden
                         .setSubtitleText(dataList[position].storiesName) // Default is Hidden
                         .setTitleLogoUrl(dataList[position].storiesName) // Default is Hidden
                         .setStoryClickListeners(object : StoryClickListeners {
                             override fun onDescriptionClickListener(position: Int) {
                                 //your action
                             }

                             override fun onTitleIconClickListener(position: Int) {
                                 //your action
                             }
                         }) // Optional Listeners
                         .build() // Must be called before calling show method
                         .show()*/
                }

            }
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: ArrayList<Story>) {
        this.dataList = dataList
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

    inner class TransactionViewHolder constructor(internal var binding: LayoutStoriesRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}