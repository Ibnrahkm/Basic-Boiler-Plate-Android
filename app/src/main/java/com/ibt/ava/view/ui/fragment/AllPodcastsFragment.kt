package com.ibt.ava.view.ui.fragment

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.FragmentHomeBinding
import com.ibt.ava.databinding.LayoutAllPodcastsBinding
import com.ibt.ava.databinding.LayoutAuthorProfileFragmentBinding
import com.ibt.ava.databinding.LayoutCategoriesBinding
import com.ibt.ava.databinding.LayoutCategoriesWiseNewsFragmentBinding
import com.ibt.ava.databinding.LayoutPodcastChannelsBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categories.Categories
import com.ibt.ava.service.model.news.categorynews.Author
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.service.model.news.multimedia.Data
import com.ibt.ava.service.model.podcasts.Podcast
import com.ibt.ava.service.model.podcasts.PodcastArrayData
import com.ibt.ava.service.model.podcasts.PodcastList
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.adapter.AllPodcastsAdapter
import com.ibt.ava.view.adapter.AuthorNewsDataAdapter
import com.ibt.ava.view.adapter.CategoriesAdapter
import com.ibt.ava.view.adapter.HomeEighthSection1DataAdapter
import com.ibt.ava.view.adapter.HomeFifthSectionDataAdapter
import com.ibt.ava.view.adapter.HomeFirstSectionDataAdapter
import com.ibt.ava.view.adapter.HomeForthSectionScoopDataAdapter
import com.ibt.ava.view.adapter.HomePodcastAdapter
import com.ibt.ava.view.adapter.HomeScreenVideoAdapter
import com.ibt.ava.view.adapter.HomeSectionNineAdapter
import com.ibt.ava.view.adapter.HomeThirdSectionTabDataAdapter
import com.ibt.ava.view.adapter.MultimediaVideoTypeAdapter
import com.ibt.ava.view.adapter.PodcastsAdapter
import com.ibt.ava.view.adapter.StoriesAdapter
import com.ibt.ava.view.ui.activity.AuthenticationActivity
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.ibt.ava.view.ui.fragment.FollowFragment.Companion.bookmarked
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import org.json.JSONObject
import java.lang.reflect.Type


class AllPodcastsFragment : BaseFragment() {

    lateinit var binding: LayoutAllPodcastsBinding
    var gson = Gson()
    lateinit var podcastsAdapter: AllPodcastsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutAllPodcastsBinding>(
            inflater, R.layout.layout_all_podcasts, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnBack.setOnClickListener {
            dismiss()
        }
        podcastsAdapter = AllPodcastsAdapter(requireActivity())
        binding.rec.adapter = podcastsAdapter
        model.getAllPodcasts(1,10).observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.status == Resource.Status.LOADING) {
                    Helper.showProgressDialog("", "", requireActivity())
                } else if (it.status == Resource.Status.SUCCESS) {
                    Helper.hideProgressDialog()
                    var data = gson.fromJson(
                        it.data.toString(),
                        com.ibt.ava.service.model.podcastchannels.Podcast::class.java
                    )
                    if (!data.data.data.isNullOrEmpty()) {
                        Log.e("sdvsdvbds", "sdvsdvsdvbsd")
                        podcastsAdapter.provideData(ArrayList(data.data.data))
                    }
                } else {
                    Helper.hideProgressDialog()
                }
            }
        })

        podcastsAdapter.loadMore.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                podcastsAdapter.loadMore.value = null
                model.getAllPodcasts(it, 10)
                    .observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            if (it.status == Resource.Status.LOADING) {
                            } else if (it.status == Resource.Status.SUCCESS) {
                                Helper.hideProgressDialog()
                                var data = gson.fromJson(
                                    it.data.toString(),
                                    com.ibt.ava.service.model.podcastchannels.Podcast::class.java
                                )
                                podcastsAdapter.provideDataInfinite(ArrayList(data.data.data))
                            } else if (it.status == Resource.Status.ERROR) {
                                Helper.hideProgressDialog()
                            }
                        }
                    })
            }
        })
    }
}