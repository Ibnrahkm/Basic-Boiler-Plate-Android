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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.FragmentHomeBinding
import com.ibt.ava.databinding.LayoutAuthorProfileFragmentBinding
import com.ibt.ava.databinding.LayoutCategoriesBinding
import com.ibt.ava.databinding.LayoutCategoriesWiseNewsFragmentBinding
import com.ibt.ava.databinding.LayoutTagNewsBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categories.Categories
import com.ibt.ava.service.model.news.categorynews.Author
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.service.model.news.home.Tag
import com.ibt.ava.service.model.news.multimedia.Data
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
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
import com.ibt.ava.view.adapter.StoriesAdapter
import com.ibt.ava.view.ui.activity.AuthenticationActivity
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import org.json.JSONObject
import java.lang.reflect.Type


class TagNewsFragment : BaseFragment() {

    lateinit var binding: LayoutTagNewsBinding
    var gson = Gson()
    lateinit var authorNewsDataAdapter: AuthorNewsDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutTagNewsBinding>(
            inflater, R.layout.layout_tag_news, null, false
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
        var tagdata = requireArguments().getString("tag")
        var tag = gson.fromJson(tagdata, Tag::class.java)
        binding.model = tag
        authorNewsDataAdapter = AuthorNewsDataAdapter(requireActivity())
        binding.rec.adapter = authorNewsDataAdapter





        authorNewsDataAdapter.loadMore.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                authorNewsDataAdapter.loadMore.value = null
                model.getTagNews(tag.id.toString(), "", it, 10).observe(this, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var json = JSONObject(it.data.toString())
                            val type: Type =
                                object :
                                    TypeToken<List<Featurednews?>?>() {}.type
                            var datas: List<Featurednews> =
                                gson.fromJson(json.getJSONArray("data").toString(), type)
                            authorNewsDataAdapter.provideDataInfinite(datas)
                        } else {
                            Helper.hideProgressDialog()
                        }
                    }
                })
            }


        })













        model.getTagNews(tag.id.toString(), "", 1, 10).observe(this, Observer {
            if (it != null) {
                if (it.status == Resource.Status.LOADING) {
                    Helper.showProgressDialog("", "", requireActivity())
                } else if (it.status == Resource.Status.SUCCESS) {
                    Helper.hideProgressDialog()
                    var json = JSONObject(it.data.toString())
                    val type: Type =
                        object :
                            TypeToken<List<Featurednews?>?>() {}.type

                    var datas: List<Featurednews> =
                        gson.fromJson(json.getJSONArray("data").toString(), type)
                    if (datas.isNullOrEmpty()) {
                        binding.rec.visibility =
                            View.GONE
                    } else {
                        binding.rec.visibility = View.VISIBLE
                        authorNewsDataAdapter.provideData(datas)
                    }
                } else {
                    Helper.hideProgressDialog()
                }
            }
        })
    }
}