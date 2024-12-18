package com.ibt.ava.view.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFooterBinding
import com.ibt.ava.databinding.LayoutNotificationRowBinding
import com.ibt.ava.databinding.LayoutPodcastRowBinding
import com.ibt.ava.databinding.LayoutStoriesRowBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.details.NewsDetails
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.notifications.Datum
import com.ibt.ava.service.model.notifications.Notification
import com.ibt.ava.service.model.podcasts.Podcast
import com.ibt.ava.service.model.stories.Story
import com.ibt.ava.service.model.user.User
import com.ibt.ava.service.model.weather.List
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.ibt.ava.view.ui.fragment.MediaPlayerFragment
import com.ibt.ava.view.ui.fragment.StoryFragment
import com.ibt.ava.viewmodel.MainViewModel
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import org.json.JSONObject
import java.lang.reflect.Type


/*
* Transaction history recycler view adapter
* */
class NotificationAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Datum> = ArrayList<Datum>()
    var clickedData: MutableLiveData<Datum> = MutableLiveData<Datum>()
lateinit var model:MainViewModel
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
                DataBindingUtil.inflate<LayoutNotificationRowBinding>(
                    LayoutInflater.from(context), R.layout.layout_notification_row, parent, false
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
                var s = dataList[position].link.split("/")
                var id=s[s.size - 1]
                model.getNewsDetails(id).observe(context as HomeActivity, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", context)
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data = Gson().fromJson((it.data as String), NewsDetails::class.java)
                            if (data != null) {
                                if (!data.mediaGallery.isNullOrEmpty()) {
                                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("video", false)
                                    context.startActivity(i)
                                } else if (!data.youtubeKey.isNullOrEmpty()) {
                                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("video", true)
                                    context.startActivity(i)
                                } else if (data.primaryCategory.id == 12) {
                                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("opinion", true)
                                    context.startActivity(i)
                                } else if (data.isSpecialToAva) {
                                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    context.startActivity(i)
                                } else {
                                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    context.startActivity(i)
                                }
                            }
                        } else if (it.status == Resource.Status.ERROR) {
                            Helper.hideProgressDialog()
                        }
                    }
                })
            }
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: kotlin.collections.List<Datum>) {
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

    inner class TransactionViewHolder constructor(internal var binding: LayoutNotificationRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}