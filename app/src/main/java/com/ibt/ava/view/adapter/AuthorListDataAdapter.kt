package com.ibt.ava.view.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutAuthorRowBinding
import com.ibt.ava.databinding.LayoutBookmarkRowBinding
import com.ibt.ava.databinding.LayoutFifthRowBinding
import com.ibt.ava.databinding.LayoutFirstRowBinding
import com.ibt.ava.databinding.LayoutFooterBinding
import com.ibt.ava.databinding.LayoutHomeScreenPodcastRowBinding
import com.ibt.ava.databinding.LayoutNinthRecRowRowBinding
import com.ibt.ava.databinding.LayoutStoriesRowBinding
import com.ibt.ava.databinding.LayoutThirdTabDataRowBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.follow.Datum
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.user.Bookmark
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity


/*
* Transaction history recycler view adapter
* */
class AuthorListDataAdapter constructor(val context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * global variables
     */
    var dataList: ArrayList<Datum> = ArrayList<Datum>()
    var clickedData: MutableLiveData<Datum> = MutableLiveData<Datum>()
    var rowClicked: MutableLiveData<Datum> = MutableLiveData<Datum>()

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
                DataBindingUtil.inflate<LayoutAuthorRowBinding>(
                    LayoutInflater.from(context),
                    R.layout.layout_author_row, parent, false
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
            holder.binding.tvCategory.setText(dataList[position].totalNewsCount.toString()+" هەواڵی گشتی")
            holder.binding.btnUnfollow.setOnClickListener {
                clickedData.value=dataList[position]
            }
            holder.binding.root.setOnClickListener {
                rowClicked.value=dataList[position]
            }
        }
    }


    /**
     * provide  data to the adapter
     */
    fun provideData(dataList: List<Datum>) {
        this.dataList = dataList as ArrayList
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

    inner class TransactionViewHolder constructor(internal var binding: LayoutAuthorRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class FooterViewHolder constructor(internal var binding: LayoutFooterBinding) :
        RecyclerView.ViewHolder(binding.root)
}