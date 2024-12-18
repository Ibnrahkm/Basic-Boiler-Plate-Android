package com.ibt.ava.view.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFooterBinding
import com.ibt.ava.databinding.LayoutNinthRecRowRowBinding
import com.ibt.ava.databinding.LayoutStoriesRowBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.util.Helper
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.ibt.ava.view.ui.fragment.AuthorProfileFragment


/*
* Transaction history recycler view adapter
* */
class HomeSectionNineAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Featurednews> = ArrayList<Featurednews>()
    var clickedData: MutableLiveData<Featurednews> = MutableLiveData<Featurednews>()
    var isFromCategory = false
    var currentSize = 0
    var pageCounter = 1
    var pageCounterForScroll = 1
    var loadMoreForScroll = MutableLiveData<Int>()
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
                    LayoutInflater.from(context),
                    R.layout.layout_footer, parent, false
                )
            )
        } else {
            TransactionViewHolder(
                DataBindingUtil.inflate<LayoutNinthRecRowRowBinding>(
                    LayoutInflater.from(context),
                    R.layout.layout_ninth_rec_row_row, parent, false
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

    fun setIsFromCategory(category: Boolean) {
        this.isFromCategory = category
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
                holder.binding.progressSpinner.visibility=View.GONE
                loadMore.value = ++pageCounter
            }else{
                holder.binding.progressSpinner.visibility=View.GONE
            }

        } else if (holder is TransactionViewHolder) {
            if (isFromCategory) {
                var l = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                l.topMargin = 20
                l.bottomMargin=20
                holder.binding.layoutToMatchparent.layoutParams = l
                holder.binding.layoutToMatchParent.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            holder.binding.model = dataList[position]

            holder.binding.root.setOnClickListener {
                if (!dataList[position].mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", dataList[position].id.toString())
                    i.putExtra("video", false)
                    context.startActivity(i)
                } else if (!dataList[position].youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", dataList[position].id.toString())
                    i.putExtra("video", true)
                    context.startActivity(i)
                } else if (dataList[position].primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", dataList[position].id.toString())
                    i.putExtra("opinion", true)
                    context.startActivity(i)
                } else if (dataList[position].isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", dataList[position].id.toString())
                    context.startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", dataList[position].id.toString())
                    context.startActivity(i)
                }
            }

            if (dataList[position].author.get(0).avatar.isNullOrEmpty()){
              //  holder.binding.ivAuthor.visibility=View.GONE
            }else{
                Helper.setImagePromatcally(holder.binding.ivAuthor,dataList[position].author.get(0).avatar)
            }
            holder.binding.ivAuthor.setOnClickListener {
                var f= AuthorProfileFragment()
                var b= Bundle()
                b.putString("author", Gson().toJson(dataList[position].author.get(0)))
                f.arguments=b
                try {
                    f.show((context as HomeActivity).supportFragmentManager,null)
                }catch (ex:Exception){

                }
                try {
                    f.show((context as SingleNewsDetailActivity).supportFragmentManager,null)
                }catch (ex:Exception){

                }
                try {
                    f.show((context as SingleNewsSpecialDetailActivity).supportFragmentManager,null)
                }catch (ex:Exception){

                }

            }
            holder.binding.layoutAuthor.setOnClickListener {
                var f= AuthorProfileFragment()
                var b= Bundle()
                b.putString("author", Gson().toJson(dataList[position].author.get(0)))
                f.arguments=b
                try {
                    f.show((context as HomeActivity).supportFragmentManager,null)
                }catch (ex:Exception){

                }
                try {
                    f.show((context as SingleNewsDetailActivity).supportFragmentManager,null)
                }catch (ex:Exception){

                }
                try {
                    f.show((context as SingleNewsSpecialDetailActivity).supportFragmentManager,null)
                }catch (ex:Exception){

                }

            }
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: List<Featurednews>) {
        currentSize = dataList.size
        pageCounter=1
        pageCounterForScroll=1
        this.dataList = ArrayList<Featurednews>(dataList)
        notifyDataSetChanged()
    }
    /**
     * provide  data to the adapter
     */
    fun provideDataInfinite(dataList: List<Featurednews>) {
        var last = itemCount
        currentSize = dataList.size
        this.dataList.addAll(dataList)
        notifyItemRangeInserted(last, dataList.size)
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

    inner class TransactionViewHolder constructor(internal var binding: LayoutNinthRecRowRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}