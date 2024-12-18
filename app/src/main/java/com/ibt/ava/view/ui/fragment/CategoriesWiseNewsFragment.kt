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
import com.ibt.ava.R
import com.ibt.ava.databinding.FragmentHomeBinding
import com.ibt.ava.databinding.LayoutCategoriesBinding
import com.ibt.ava.databinding.LayoutCategoriesWiseNewsFragmentBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categories.Categories
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.service.model.news.multimedia.Data
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.util.TouchDetectableScrollView.OnMyScrollChangeListener
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
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import java.lang.reflect.Type


class CategoriesWiseNewsFragment : BaseFragment() {

    lateinit var binding: LayoutCategoriesWiseNewsFragmentBinding
    var gson = Gson()
    lateinit var opinionAdapter: HomeSectionNineAdapter
    lateinit var scoopAdapter: HomeForthSectionScoopDataAdapter
    lateinit var multimediaVideoTypeAdapter: MultimediaVideoTypeAdapter
    lateinit var homeThirdSectionTabDataAdapter: HomeThirdSectionTabDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutCategoriesWiseNewsFragmentBinding>(
            inflater, R.layout.layout_categories_wise_news_fragment, null, false
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
        var categoryId = requireArguments().getString("id")
        var categoryName = requireArguments().getString("name")
        binding.tvCategoryName.setText(categoryName)

        when (categoryId) {
            "12" -> {
                opinionAdapter = HomeSectionNineAdapter(requireActivity())
                opinionAdapter.setIsFromCategory(true)
                binding.layoutOpinion.rec.adapter = opinionAdapter
                binding.layoutOpinion.layoutOpinionBody.visibility = View.VISIBLE
                binding.layoutVideos.layoutVideosBody.visibility = View.GONE
                binding.layoutScoop.layoutScoopBody.visibility = View.GONE
                binding.layoutNormal.layoutNormalBody.visibility = View.GONE


                model.getNewsByCategory(1, 10, Integer.parseInt(categoryId))
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson(
                                        (it.data as String),
                                        CategoryNews::class.java
                                    )
                                    if (!data.data.isNullOrEmpty()) {

                                        var json = gson.toJson(data.data)
                                        val type: Type =
                                            object : TypeToken<List<Featurednews?>?>() {}.type

                                        var datas: List<Featurednews> = gson.fromJson(json, type)
                                        if (datas.isNullOrEmpty()) {
                                            binding.layoutOpinion.layoutOpinionBody.visibility =
                                                View.GONE
                                        } else {
                                            binding.layoutOpinion.ivAuthor.tag = gson.toJson(datas[0].author.get(0))
                                            binding.layoutOpinion.tvFirstTitle.setText(datas[0].title)
                                            binding.layoutOpinion.tvFirstTime.setText(datas[0].publishedAt)
                                            binding.layoutOpinion.tvFirstAuthorName.setText(
                                                datas[0].author.get(
                                                    0
                                                ).name
                                            )
                                            Helper.setImagePromatcally(
                                                binding.layoutOpinion.imageNewsFirst,
                                                datas[0].featuredImageUrl
                                            )
                                            if (datas[0].author.get(0).avatar.isNullOrEmpty()) {
                                                //   binding.layoutOpinion.ivAuthor.visibility=View.GONE
                                            } else {
                                                Helper.setImagePromatcally(
                                                    binding.layoutOpinion.ivAuthor,
                                                    datas[0].author.get(0).avatar
                                                )
                                            }

                                            opinionAdapter.provideData(datas.subList(1, datas.size))


                                            binding.layoutOpinion.ivAuthor.setOnClickListener {
                                                if (binding.layoutOpinion.ivAuthor.tag != null) {
                                                    var f = AuthorProfileFragment()
                                                    var b = Bundle()
                                                    b.putString("author", binding.layoutOpinion.ivAuthor.tag.toString())
                                                    f.arguments = b
                                                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                                                }

                                            }
                                            binding.layoutOpinion.tvFirstAuthorName.setOnClickListener {
                                                if (binding.layoutOpinion.ivAuthor.tag != null) {
                                                    var f = AuthorProfileFragment()
                                                    var b = Bundle()
                                                    b.putString("author", binding.layoutOpinion.ivAuthor.tag.toString())
                                                    f.arguments = b
                                                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                                                }

                                            }


                                            binding.layoutOpinion.btnFirstNews.setOnClickListener {
                                                var i = Intent(
                                                    requireActivity(),
                                                    SingleNewsSpecialDetailActivity::class.java
                                                )
                                                i.putExtra("id", datas[0].id.toString())
                                                i.putExtra("opinion", true)
                                                startActivity(i)
                                            }
                                        }
                                    }

                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
            }

            "17" -> {
                binding.layoutOpinion.layoutOpinionBody.visibility = View.GONE
                binding.layoutVideos.layoutVideosBody.visibility = View.VISIBLE
                binding.layoutScoop.layoutScoopBody.visibility = View.GONE
                binding.layoutNormal.layoutNormalBody.visibility = View.GONE

                multimediaVideoTypeAdapter = MultimediaVideoTypeAdapter(requireActivity())
                binding.layoutVideos.recVideos.adapter = multimediaVideoTypeAdapter
                model.getNewsByCategory(1, 10, Integer.parseInt(categoryId))
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson(
                                        (it.data as String),
                                        CategoryNews::class.java
                                    )
                                    if (!data.data.isNullOrEmpty()) {

                                        var json = gson.toJson(data.data)
                                        val type: Type =
                                            object : TypeToken<List<Data?>?>() {}.type

                                        var datas: List<Data> = gson.fromJson(json, type)
                                        if (datas.isNullOrEmpty()) {
                                            binding.layoutVideos.layoutVideosBody.visibility =
                                                View.GONE
                                        } else {
                                            binding.layoutVideos.tvFirstTitle.setText(datas[0].title)
                                            binding.layoutVideos.tvFirstTime.setText(datas[0].publishedAt)
                                            binding.layoutVideos.tvFirstCategory.setText(datas[0].primaryCategory.name)
                                            Helper.setImagePromatcally(
                                                binding.layoutVideos.imageNewsFirst,
                                                datas[0].featuredImageUrl
                                            )
                                            multimediaVideoTypeAdapter.provideData(
                                                datas.subList(
                                                    1,
                                                    datas.size
                                                )
                                            )




                                            binding.layoutVideos.layoutVideo.setOnClickListener {
                                                var i = Intent(
                                                    requireActivity(),
                                                    SingleNewsSpecialDetailActivity::class.java
                                                )
                                                i.putExtra("id", datas[0].id.toString())
                                                i.putExtra("video", true)
                                                startActivity(i)
                                            }
                                        }
                                    }

                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
            }

            "scoop" -> {
                binding.layoutOpinion.layoutOpinionBody.visibility = View.GONE
                binding.layoutVideos.layoutVideosBody.visibility = View.GONE
                binding.layoutScoop.layoutScoopBody.visibility = View.VISIBLE
                binding.layoutNormal.layoutNormalBody.visibility = View.GONE
                scoopAdapter = HomeForthSectionScoopDataAdapter(requireActivity())
                binding.layoutScoop.rec.adapter = scoopAdapter

                model.getScoopNews(1, 10, true)
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson(
                                        (it.data as String),
                                        CategoryNews::class.java
                                    )
                                    if (!data.data.isNullOrEmpty()) {

                                        var json = gson.toJson(data.data)
                                        val type: Type =
                                            object : TypeToken<List<Featurednews?>?>() {}.type

                                        var datas: List<Featurednews> = gson.fromJson(json, type)
                                        if (datas.isNullOrEmpty()) {
                                            binding.layoutScoop.layoutScoopBody.visibility =
                                                View.GONE
                                        } else {
                                            scoopAdapter.provideData(datas)
                                        }
                                    }

                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
            }

            "latest" -> {
                binding.layoutOpinion.layoutOpinionBody.visibility = View.GONE
                binding.layoutVideos.layoutVideosBody.visibility = View.GONE
                binding.layoutScoop.layoutScoopBody.visibility = View.GONE
                binding.layoutNormal.layoutNormalBody.visibility = View.VISIBLE

                homeThirdSectionTabDataAdapter = HomeThirdSectionTabDataAdapter(requireActivity())
                binding.layoutNormal.rec.adapter = homeThirdSectionTabDataAdapter
                model.getLatestNews(1, 10, true)
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson(
                                        (it.data as String),
                                        CategoryNews::class.java
                                    )
                                    if (!data.data.isNullOrEmpty()) {

                                        var json = gson.toJson(data.data)
                                        val type: Type =
                                            object : TypeToken<List<Featurednews?>?>() {}.type

                                        var datas: List<Featurednews> = gson.fromJson(json, type)
                                        if (datas.isNullOrEmpty()) {
                                            binding.layoutNormal.layoutNormalBody.visibility =
                                                View.GONE
                                        } else {
                                            binding.layoutNormal.ivAuthor.tag = gson.toJson(datas[0].author.get(0))
                                            binding.layoutNormal.tvFirstTitle.setText(datas[0].title)
                                            binding.layoutNormal.tvFirstTime.setText(datas[0].publishedAt)
                                            binding.layoutNormal.tvFirstCategory.setText(datas[0].primaryCategory.name)
                                            binding.layoutNormal.tvFirstAuthorName.setText(
                                                datas[0].author.get(
                                                    0
                                                ).name
                                            )
                                            if (datas[0].author.get(0).avatar.isNullOrEmpty()) {
                                                //    binding.layoutNormal.ivAuthor.visibility=View.GONE
                                            } else {
                                                Helper.setImagePromatcally(
                                                    binding.layoutNormal.ivAuthor,
                                                    datas[0].author.get(0).avatar
                                                )
                                            }

                                            Helper.setImagePromatcally(
                                                binding.layoutNormal.imageNewsFirst,
                                                datas[0].featuredImageUrl
                                            )
                                            homeThirdSectionTabDataAdapter.provideData(
                                                datas.subList(
                                                    1,
                                                    datas.size
                                                )
                                            )


                                            binding.layoutNormal.ivAuthor.setOnClickListener {
                                                if (binding.layoutNormal.ivAuthor.tag != null) {
                                                    var f = AuthorProfileFragment()
                                                    var b = Bundle()
                                                    b.putString("author", binding.layoutNormal.ivAuthor.tag.toString())
                                                    f.arguments = b
                                                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                                                }

                                            }
                                            binding.layoutNormal.tvFirstAuthorName.setOnClickListener {
                                                if (binding.layoutNormal.ivAuthor.tag != null) {
                                                    var f = AuthorProfileFragment()
                                                    var b = Bundle()
                                                    b.putString("author", binding.layoutNormal.ivAuthor.tag.toString())
                                                    f.arguments = b
                                                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                                                }

                                            }




                                            binding.layoutNormal.btnFirstNews.setOnClickListener {
                                                var i = Intent(
                                                    requireActivity(),
                                                    SingleNewsDetailActivity::class.java
                                                )
                                                i.putExtra("id", datas[0].id.toString())
                                                startActivity(i)
                                            }
                                        }
                                    }

                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
            }

            else -> {
                binding.layoutOpinion.layoutOpinionBody.visibility = View.GONE
                binding.layoutVideos.layoutVideosBody.visibility = View.GONE
                binding.layoutScoop.layoutScoopBody.visibility = View.GONE
                binding.layoutNormal.layoutNormalBody.visibility = View.VISIBLE

                homeThirdSectionTabDataAdapter = HomeThirdSectionTabDataAdapter(requireActivity())
                binding.layoutNormal.rec.adapter = homeThirdSectionTabDataAdapter
                Log.e("sdcsdcdsc", categoryId.toString())
                model.getNewsByCategory(1, 10, Integer.parseInt(categoryId))
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson(
                                        (it.data as String),
                                        CategoryNews::class.java
                                    )
                                    if (!data.data.isNullOrEmpty()) {

                                        var json = gson.toJson(data.data)
                                        val type: Type =
                                            object : TypeToken<List<Featurednews?>?>() {}.type

                                        var datas: List<Featurednews> = gson.fromJson(json, type)
                                        if (datas.isNullOrEmpty()) {
                                            binding.layoutNormal.layoutNormalBody.visibility =
                                                View.GONE
                                        } else {
                                            binding.layoutNormal.ivAuthor.tag = gson.toJson(datas[0].author.get(0))
                                            binding.layoutNormal.tvFirstTitle.setText(datas[0].title)
                                            binding.layoutNormal.tvFirstTime.setText(datas[0].publishedAt)
                                            binding.layoutNormal.tvFirstCategory.setText(datas[0].primaryCategory.name)
                                            binding.layoutNormal.tvFirstAuthorName.setText(
                                                datas[0].author.get(
                                                    0
                                                ).name
                                            )

                                            if (datas[0].author.get(0).avatar.isNullOrEmpty()) {
                                                //   binding.layoutNormal.ivAuthor.visibility=View.GONE
                                            } else {
                                                Helper.setImagePromatcally(
                                                    binding.layoutNormal.ivAuthor,
                                                    datas[0].author.get(0).avatar
                                                )
                                            }
                                            Helper.setImagePromatcally(
                                                binding.layoutNormal.imageNewsFirst,
                                                datas[0].featuredImageUrl
                                            )
                                            homeThirdSectionTabDataAdapter.provideData(
                                                datas.subList(
                                                    1,
                                                    datas.size
                                                )
                                            )

                                            binding.layoutNormal.ivAuthor.setOnClickListener {
                                                if (binding.layoutNormal.ivAuthor.tag != null) {
                                                    var f = AuthorProfileFragment()
                                                    var b = Bundle()
                                                    b.putString("author", binding.layoutNormal.ivAuthor.tag.toString())
                                                    f.arguments = b
                                                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                                                }

                                            }
                                            binding.layoutNormal.tvFirstAuthorName.setOnClickListener {
                                                if (binding.layoutNormal.ivAuthor.tag != null) {
                                                    var f = AuthorProfileFragment()
                                                    var b = Bundle()
                                                    b.putString("author", binding.layoutNormal.ivAuthor.tag.toString())
                                                    f.arguments = b
                                                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                                                }

                                            }







                                            binding.layoutNormal.btnFirstNews.setOnClickListener {
                                                var i = Intent(
                                                    requireActivity(),
                                                    SingleNewsDetailActivity::class.java
                                                )
                                                i.putExtra("id", datas[0].id.toString())
                                                startActivity(i)
                                            }
                                        }
                                    }

                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
            }
        }



        try {
            scoopAdapter.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                if (it != null) {
                    scoopAdapter.loadMoreForScroll.value = null

                    model.getScoopNews(it, 10, true)
                        .observe(viewLifecycleOwner,
                            Observer {
                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        var data = Gson().fromJson(
                                            (it.data as String),
                                            CategoryNews::class.java
                                        )
                                        if (!data.data.isNullOrEmpty()) {

                                            var json = gson.toJson(data.data)
                                            val type: Type =
                                                object : TypeToken<List<Featurednews?>?>() {}.type

                                            var datas: List<Featurednews> =
                                                gson.fromJson(json, type)
                                            scoopAdapter.provideDataInfinite(datas)
                                        }

                                    } else if (it.status == Resource.Status.ERROR) {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })
                }


            })
        } catch (ex: Exception) {

        }

        try {
            opinionAdapter.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                if (it != null) {
                    opinionAdapter.loadMoreForScroll.value = null
                    model.getNewsByCategory(it, 10, Integer.parseInt(categoryId))
                        .observe(viewLifecycleOwner,
                            Observer {
                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        var data = Gson().fromJson(
                                            (it.data as String),
                                            CategoryNews::class.java
                                        )
                                        if (!data.data.isNullOrEmpty()) {

                                            var json = gson.toJson(data.data)
                                            val type: Type =
                                                object : TypeToken<List<Featurednews?>?>() {}.type

                                            var datas: List<Featurednews> =
                                                gson.fromJson(json, type)
                                            opinionAdapter.provideDataInfinite(datas)
                                        }

                                    } else if (it.status == Resource.Status.ERROR) {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })

                }


            })
        } catch (ex: Exception) {

        }

        try {
            multimediaVideoTypeAdapter.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                if (it != null) {
                    multimediaVideoTypeAdapter.loadMoreForScroll.value = null
                    model.getNewsByCategory(it, 10, Integer.parseInt(categoryId))
                        .observe(viewLifecycleOwner,
                            Observer {
                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        var data = Gson().fromJson(
                                            (it.data as String),
                                            CategoryNews::class.java
                                        )
                                        if (!data.data.isNullOrEmpty()) {

                                            var json = gson.toJson(data.data)
                                            val type: Type =
                                                object : TypeToken<List<Data?>?>() {}.type

                                            var datas: List<Data> = gson.fromJson(json, type)
                                            multimediaVideoTypeAdapter.provideDataInfinite(datas)
                                        }

                                    } else if (it.status == Resource.Status.ERROR) {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })
                }


            })
        } catch (ex: Exception) {

        }

        try {
            homeThirdSectionTabDataAdapter.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                if (it != null) {
                    homeThirdSectionTabDataAdapter.loadMoreForScroll.value = null

                    if (categoryId.equals("latest", true)) {
                        model.getLatestNews(it, 10, true)
                            .observe(viewLifecycleOwner,
                                Observer {
                                    if (it != null) {
                                        if (it.status == Resource.Status.LOADING) {
                                        } else if (it.status == Resource.Status.SUCCESS) {
                                            Helper.hideProgressDialog()
                                            var data = Gson().fromJson(
                                                (it.data as String),
                                                CategoryNews::class.java
                                            )
                                            if (!data.data.isNullOrEmpty()) {

                                                var json = gson.toJson(data.data)
                                                val type: Type =
                                                    object :
                                                        TypeToken<List<Featurednews?>?>() {}.type

                                                var datas: List<Featurednews> =
                                                    gson.fromJson(json, type)
                                                homeThirdSectionTabDataAdapter.provideDataInfinite(
                                                    datas
                                                )
                                            }

                                        } else if (it.status == Resource.Status.ERROR) {
                                            Helper.hideProgressDialog()
                                        }
                                    }
                                })
                    } else {
                        model.getNewsByCategory(it, 10, Integer.parseInt(categoryId))
                            .observe(viewLifecycleOwner,
                                Observer {
                                    if (it != null) {
                                        if (it.status == Resource.Status.LOADING) {
                                        } else if (it.status == Resource.Status.SUCCESS) {
                                            Helper.hideProgressDialog()
                                            var data = Gson().fromJson(
                                                (it.data as String),
                                                CategoryNews::class.java
                                            )
                                            var json = gson.toJson(data.data)
                                            val type: Type =
                                                object :
                                                    TypeToken<List<Featurednews?>?>() {}.type

                                            var datas: List<Featurednews> =
                                                gson.fromJson(json, type)
                                            homeThirdSectionTabDataAdapter.provideDataInfinite(datas)
                                        } else if (it.status == Resource.Status.ERROR) {
                                            Helper.hideProgressDialog()
                                        }
                                    }
                                })
                    }

                }


            })
        } catch (ex: Exception) {

        }

        binding.scrollView.myScrollChangeListener = object : OnMyScrollChangeListener {
            override fun onScrollUp() {
            }

            override fun onScrollDown() {
            }

            override fun onBottomReached() {
                try {
                    if (opinionAdapter.currentSize > 0) {
                        opinionAdapter.loadMoreForScroll.value =
                            ++opinionAdapter.pageCounterForScroll
                    }
                } catch (ex: Exception) {

                }

                try {
                    if (scoopAdapter.currentSize > 0) {
                        scoopAdapter.loadMoreForScroll.value = ++scoopAdapter.pageCounterForScroll
                    }
                } catch (ex: Exception) {

                }

                try {
                    if (multimediaVideoTypeAdapter.currentSize > 0) {
                        multimediaVideoTypeAdapter.loadMoreForScroll.value =
                            ++multimediaVideoTypeAdapter.pageCounterForScroll
                    }
                } catch (ex: Exception) {

                }

                try {
                    if (homeThirdSectionTabDataAdapter.currentSize > 0) {
                        Log.e(
                            "sdvsdvdsv",
                            homeThirdSectionTabDataAdapter.currentSize.toString() + "asvsdvdvbsd"
                        )
                        homeThirdSectionTabDataAdapter.loadMoreForScroll.value =
                            ++homeThirdSectionTabDataAdapter.pageCounterForScroll
                    }
                } catch (ex: Exception) {

                }
            }
        }
    }
}