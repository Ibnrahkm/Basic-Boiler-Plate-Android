package com.ibt.ava.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ibt.ava.service.model.ShowcaseModel
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutIntroBinding
import com.ibt.ava.databinding.LayoutIntroViewpagerBinding
import javax.inject.Inject


/*
* showcase view pager adapter
* */

class ShowCasePagerAdapter @Inject constructor(val context: Activity) : PagerAdapter() {

    /**
     * global variables
     */
    lateinit var datalist: List<ShowcaseModel>

    init {
        datalist = ArrayList<ShowcaseModel>()
    }

    /**
     * setting data to the viewpager adapter
     */
    fun provideDataAndContext(datalist: List<ShowcaseModel>) {
        this.datalist = datalist
        notifyDataSetChanged()
    }

    /**
     * returning view if its from object
     */
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj as LinearLayout
    }

    /**
     * called on every item
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        /**
         * setting layout with data binding
         */
        val binding = DataBindingUtil.inflate<LayoutIntroViewpagerBinding>(
            LayoutInflater.from(context), R.layout.layout_intro_viewpager, container, false
        )
        /**
         * setting up data to view model
         */
        binding.model = datalist[position]
        container.addView(binding.root as LinearLayout)
        return binding.root as LinearLayout
    }

    /**
     * returning total data list size
     */
    override fun getCount(): Int {
        return datalist.size
    }

    /**
     * destroying item whenever gone from context
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as LinearLayout)
    }

}