package com.ibt.ava.view.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.gson.Gson
import com.ibt.ava.R
import com.ibt.ava.databinding.FragmentHomeBinding
import com.ibt.ava.databinding.LayoutCategoriesBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categories.Categories
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.adapter.CategoriesAdapter
import com.ibt.ava.view.adapter.HomeEighthSection1DataAdapter
import com.ibt.ava.view.adapter.HomeFifthSectionDataAdapter
import com.ibt.ava.view.adapter.HomeFirstSectionDataAdapter
import com.ibt.ava.view.adapter.HomeForthSectionScoopDataAdapter
import com.ibt.ava.view.adapter.HomePodcastAdapter
import com.ibt.ava.view.adapter.HomeScreenVideoAdapter
import com.ibt.ava.view.adapter.HomeSectionNineAdapter
import com.ibt.ava.view.adapter.HomeThirdSectionTabDataAdapter
import com.ibt.ava.view.adapter.StoriesAdapter
import com.ibt.ava.view.ui.activity.HomeActivity
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer


class CategoriesFragment : BaseFragment() {

    lateinit var binding: LayoutCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutCategoriesBinding>(
            inflater, R.layout.layout_categories, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as HomeActivity).navButtonHandler("more")

        var categoriesAdapter = CategoriesAdapter(requireActivity())

        binding.rec2.adapter = categoriesAdapter
        var list = Helper.prepareStaticCategoryData();
        categoriesAdapter.provideData(list.subList(1, list.size))
    }
}