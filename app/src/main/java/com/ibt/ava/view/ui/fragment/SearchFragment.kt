package com.ibt.ava.view.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.util.BeanUtil
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.R
import com.ibt.ava.databinding.FragmentHomeBinding
import com.ibt.ava.databinding.LayoutCategoriesBinding
import com.ibt.ava.databinding.LayoutSearchFragmentBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categories.Categories
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.categorynews.PrimaryCategory
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.service.model.search.SearchResult
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
import java.lang.reflect.Type


class SearchFragment : BaseFragment() {

    lateinit var binding: LayoutSearchFragmentBinding
    var gson = Gson()
    lateinit var adapter: HomeFifthSectionDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutSearchFragmentBinding>(
            inflater, R.layout.layout_search_fragment, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = HomeFifthSectionDataAdapter(requireActivity())
        binding.rec.adapter = adapter
        var timer = Handler(Looper.getMainLooper())
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnCancelText.setOnClickListener {
            binding.etSearch.setText("")
            adapter.provideData(ArrayList<Featurednews>())
        }
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer.dump(LogPrinter(Log.DEBUG, "TAG"), "PREFIX")
                timer.postDelayed(Runnable {

                    if (binding.etSearch.text.toString().trim().equals("")){
                        adapter.provideData(ArrayList<Featurednews>())
                    }else{
                        model.getSearchNews(binding.etSearch.text.toString().trim(),1,10)
                            .observe(viewLifecycleOwner,
                                Observer {

                                    if (it != null) {
                                        if (it.status == Resource.Status.LOADING) {
                                            //Helper.showProgressDialog("", "", requireActivity())
                                        } else if (it.status == Resource.Status.SUCCESS) {
                                            Helper.hideProgressDialog()
                                            var data = Gson().fromJson(
                                                (it.data as String),
                                                SearchResult::class.java
                                            )
                                            if (!data.data.data.isNullOrEmpty()) {
                                                var datas=ArrayList<Featurednews>()
                                                for (i in data.data.data){
                                                    var a=Featurednews()
                                                    a.id=i.id
                                                    a.title=i.title
                                                    a.slug=i.slug
                                                    a.featuredImageUrl=i.image
                                                    var pc=com.ibt.ava.service.model.news.home.PrimaryCategory()
                                                    pc.id=i.primaryCategory.id
                                                    pc.name=i.primaryCategory.name
                                                    pc.slug=i.primaryCategory.slug
                                                    a.primaryCategory=pc
                                                    a.publishedAt=i.publishedAt
                                                    datas.add(a)
                                                }
                                                adapter.provideData(datas)
                                                Log.e("ascsdvsdv",gson.toJson(datas[0]))
                                            }

                                        } else if (it.status == Resource.Status.ERROR) {
                                            Helper.hideProgressDialog()
                                        }
                                    }
                                })
                    }
                }, 200)


            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })


        adapter.loadMore.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                adapter.loadMore.value = null
                model.getSearchNews(binding.etSearch.text.toString().trim(),it,10)
                    .observe(viewLifecycleOwner,
                        Observer {

                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    //Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson(
                                        (it.data as String),
                                        SearchResult::class.java
                                    )
                                    var datas=ArrayList<Featurednews>()
                                    for (i in data.data.data){
                                        var a=Featurednews()
                                        a.id=i.id
                                        a.title=i.title
                                        a.slug=i.slug
                                        a.featuredImageUrl=i.image
                                        var pc=com.ibt.ava.service.model.news.home.PrimaryCategory()
                                        pc.id=i.primaryCategory.id
                                        pc.name=i.primaryCategory.name
                                        pc.slug=i.primaryCategory.slug
                                        a.primaryCategory=pc
                                        a.publishedAt=i.publishedAt
                                        datas.add(a)
                                    }
                                    adapter.provideDataInfinite(datas)
                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
            }
        })
    }
}