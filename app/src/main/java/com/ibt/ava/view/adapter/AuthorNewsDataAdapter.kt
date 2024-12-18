package com.ibt.ava.view.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutAuthorNewsRowBinding
import com.ibt.ava.databinding.LayoutFooterBinding
import com.ibt.ava.databinding.LayoutProgressVerticalBinding
import com.ibt.ava.databinding.LayoutThirdTabDataRowBinding
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.util.Helper
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity


/*
* Transaction history recycler view adapter
* */
class AuthorNewsDataAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Featurednews> = ArrayList<Featurednews>()
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
                    LayoutInflater.from(context),
                    R.layout.layout_footer, parent, false
                )
            )
        } else {
            TransactionViewHolder(
                DataBindingUtil.inflate<LayoutAuthorNewsRowBinding>(
                    LayoutInflater.from(context),
                    R.layout.layout_author_news_row, parent, false
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
                holder.binding.progressSpinner.visibility=View.GONE
                loadMore.value = ++pageCounter
            }else{
                holder.binding.progressSpinner.visibility=View.GONE
            }

        } else if (holder is TransactionViewHolder) {


            holder.binding.model = dataList[position]

            if (!dataList[position].mediaGallery.isNullOrEmpty()) {
                holder.binding.layoutGallery.visibility = View.VISIBLE
                holder.binding.layoutVideo.visibility = View.GONE
                holder.binding.layoutOpinion.visibility = View.GONE
                holder.binding.layoutScoop.visibility = View.GONE
                holder.binding.layoutNormal.visibility = View.GONE

            } else if (!dataList[position].youtubeKey.isNullOrEmpty()) {
                holder.binding.layoutVideo.visibility = View.VISIBLE
                holder.binding.layoutGallery.visibility = View.GONE
                holder.binding.layoutOpinion.visibility = View.GONE
                holder.binding.layoutScoop.visibility = View.GONE
                holder.binding.layoutNormal.visibility = View.GONE
            } else if (dataList[position].primaryCategory.id == 12) {
                holder.binding.layoutOpinion.visibility = View.VISIBLE
                holder.binding.layoutGallery.visibility = View.GONE
                holder.binding.layoutVideo.visibility = View.GONE
                holder.binding.layoutScoop.visibility = View.GONE
                holder.binding.layoutNormal.visibility = View.GONE
            } else if (dataList[position].isSpecialToAva()) {
                holder.binding.layoutScoop.visibility = View.VISIBLE
                holder.binding.layoutGallery.visibility = View.GONE
                holder.binding.layoutVideo.visibility = View.GONE
                holder.binding.layoutOpinion.visibility = View.GONE
                holder.binding.layoutNormal.visibility = View.GONE
            } else {
                holder.binding.layoutNormal.visibility = View.VISIBLE
                holder.binding.layoutGallery.visibility = View.GONE
                holder.binding.layoutVideo.visibility = View.GONE
                holder.binding.layoutOpinion.visibility = View.GONE
                holder.binding.layoutScoop.visibility = View.GONE
            }


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
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: List<Featurednews>) {
        currentSize = dataList.size
        pageCounter=1
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

    inner class TransactionViewHolder constructor(internal var binding: LayoutAuthorNewsRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}