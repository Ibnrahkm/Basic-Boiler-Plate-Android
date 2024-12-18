package com.ibt.ava.view.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.R
import com.ibt.ava.databinding.FragmentHomeBinding
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.service.model.stories.Story
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.util.TouchDetectableScrollView.OnMyScrollChangeListener
import com.ibt.ava.util.Url
import com.ibt.ava.view.adapter.HomeEighthSection1DataAdapter
import com.ibt.ava.view.adapter.HomeFifthSectionDataAdapter
import com.ibt.ava.view.adapter.HomeFirstSectionDataAdapter
import com.ibt.ava.view.adapter.HomeForthSectionScoopDataAdapter
import com.ibt.ava.view.adapter.HomePaginatedThirdSectionTabDataAdapter
import com.ibt.ava.view.adapter.HomePodcastAdapter
import com.ibt.ava.view.adapter.HomeScreenVideoAdapter
import com.ibt.ava.view.adapter.HomeSectionNineAdapter
import com.ibt.ava.view.adapter.HomeThirdSectionTabDataAdapter
import com.ibt.ava.view.adapter.StoriesAdapter
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import java.lang.reflect.Type


class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding
    var gson: Gson = Gson()

    lateinit var tabDataAdapter: HomePaginatedThirdSectionTabDataAdapter
    lateinit var homeFirstRowAdapter: HomeFirstSectionDataAdapter
    lateinit var homePodcastAdapter: HomePodcastAdapter
    lateinit var homeSecondRowAdapter: HomeFirstSectionDataAdapter
    lateinit var homeForthScoopRowAdapter: HomeForthSectionScoopDataAdapter
    lateinit var homeLatestRowAdapter: HomeFifthSectionDataAdapter
    lateinit var homeFifthRowAdapter: HomeFifthSectionDataAdapter
    lateinit var homeFifthRowHealthAdapter: HomeFifthSectionDataAdapter
    lateinit var homeFifthRowEnvironmentsAdapter: HomeFifthSectionDataAdapter
    lateinit var homeSixth1RowAdapter: HomeThirdSectionTabDataAdapter
    lateinit var homeSixth1RowHealthAdapter: HomeThirdSectionTabDataAdapter
    lateinit var homeSixth1RowEnvironmentsAdapter: HomeThirdSectionTabDataAdapter
    lateinit var homeSixth2RowAdapter: HomeFifthSectionDataAdapter
    lateinit var homeSixth2ReportRowAdapter: HomeFifthSectionDataAdapter
    lateinit var homeSixth2CultureRowAdapter: HomeFifthSectionDataAdapter
    lateinit var homeSevenRowAdapter: HomeFifthSectionDataAdapter
    lateinit var homeEighthRow1Adapter: HomeEighthSection1DataAdapter
    lateinit var homeEighthRow2Adapter: HomeFifthSectionDataAdapter
    lateinit var homeSectionNineAdapter: HomeSectionNineAdapter
    lateinit var homeVideoAdapter: HomeScreenVideoAdapter

    lateinit var data: HomeNews
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Center the initial position
        (requireActivity() as HomeActivity).navButtonHandler("home")


        var storiesAdapter = StoriesAdapter(requireActivity())



        binding.breakingNewsSection.recStories.adapter = storiesAdapter


        homePodcastAdapter = HomePodcastAdapter(requireActivity())
        tabDataAdapter = HomePaginatedThirdSectionTabDataAdapter(requireActivity())
        homeFirstRowAdapter = HomeFirstSectionDataAdapter(requireActivity())
        homeSecondRowAdapter = HomeFirstSectionDataAdapter(requireActivity())
        homeForthScoopRowAdapter = HomeForthSectionScoopDataAdapter(requireActivity())
        homeLatestRowAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeFifthRowAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeFifthRowHealthAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeFifthRowEnvironmentsAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeSixth1RowAdapter = HomeThirdSectionTabDataAdapter(requireActivity())
        homeSixth1RowHealthAdapter = HomeThirdSectionTabDataAdapter(requireActivity())
        homeSixth1RowEnvironmentsAdapter = HomeThirdSectionTabDataAdapter(requireActivity())
        homeSixth2RowAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeSixth2ReportRowAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeSixth2CultureRowAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeSevenRowAdapter = HomeFifthSectionDataAdapter(requireActivity())
        homeEighthRow1Adapter = HomeEighthSection1DataAdapter(requireActivity())
        homeEighthRow2Adapter = HomeFifthSectionDataAdapter(requireActivity())
        homeSectionNineAdapter = HomeSectionNineAdapter(requireActivity())
        homeVideoAdapter = HomeScreenVideoAdapter(requireActivity())
        binding.thirdSection.rec.adapter = tabDataAdapter
        binding.breakingNewsSection.rec.adapter = homeFirstRowAdapter
        binding.fastNewsSection.rec.adapter = homeSecondRowAdapter
        binding.scoopSection.rec.adapter = homeForthScoopRowAdapter
        binding.kurdistanNews.rec.adapter = homeFifthRowAdapter
        binding.healthSection.rec.adapter = homeFifthRowHealthAdapter
        binding.environmentsSection.rec.adapter = homeFifthRowEnvironmentsAdapter
        binding.latestSection.rec.adapter = homeLatestRowAdapter
        binding.kurdistanNews.rec1.adapter = homeSixth1RowAdapter
        binding.healthSection.rec1.adapter = homeSixth1RowHealthAdapter
        binding.environmentsSection.rec1.adapter = homeSixth1RowEnvironmentsAdapter
        binding.iraqNewsSection.rec2.adapter = homeSixth2RowAdapter
        binding.reportSection.rec2.adapter = homeSixth2ReportRowAdapter
        binding.cultureSection.rec2.adapter = homeSixth2CultureRowAdapter
        binding.businessNewsSection.rec.adapter = homeSevenRowAdapter
        binding.worldNewsSection.rec1.adapter = homeEighthRow1Adapter
        binding.worldNewsSection.rec2.adapter = homeEighthRow2Adapter
        binding.opinionNewsSection.rec.adapter = homeSectionNineAdapter
        binding.multimediaNewsSection.recVideos.adapter = homeVideoAdapter

        binding.shimmarLayout.startShimmer()


        binding.layoutOriginal.myScrollChangeListener = object : OnMyScrollChangeListener {
            override fun onScrollUp() {
            }

            override fun onScrollDown() {
            }

            override fun onBottomReached() {
                if (binding.layoutToHide.visibility == View.GONE) {
                    paginatedTabItemData(
                        tabDataAdapter.pageCounter,
                        10,
                        binding.thirdSection.tabs.getTabAt(binding.thirdSection.tabs.selectedTabPosition)!!.tag.toString()
                            .toInt(),
                        tabDataAdapter,
                        gson
                    )
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshLayout.setRefreshing(false)
                requireActivity().recreate()
            }, 1000)
        }


        setupStoryData(storiesAdapter)
        model.getHomeNews().observe(viewLifecycleOwner, Observer {
            if (it != null) {

                if (it.status == Resource.Status.LOADING) {
                    Helper.showProgressDialog("", "", requireActivity())
                } else if (it.status == Resource.Status.SUCCESS) {
                    Helper.hideProgressDialog()
                    var data = Gson().fromJson((it.data as String), HomeNews::class.java)
                    if (data != null && data.data != null) {
                        binding.model = data.data
                        this.data = data
                        handleBreakingNews()
                        handleFastNews()
                        handleTabNews()
                        handleScoopNews()
                        handleLatestNews()
                        handleKurdistanNews()
                        handleIraqNews()
                        handleWorldNews()
                        handleBusinessNews()
                        handleOpinionNews()
                        handleMultimediaNews()
                        handleReportNews()
                        handleHealthNews()
                        handleCultureNews()
                        handleEnvironmentsNews()
                        handlePodcastsNews()

                    }

                    binding.shimmarLayout.stopShimmer()
                    binding.shimmarLayout.visibility = View.GONE
                    binding.layoutOriginal.visibility = View.VISIBLE
                } else if (it.status == Resource.Status.ERROR) {
                    Helper.hideProgressDialog()
                }
            }
        })
       populateWeatherData()
    }


    private fun populateWeatherData(){
        model.getIp().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {

                }

                Resource.Status.SUCCESS -> {
                    var ip = it.data.toString().trim()
                    model.getCurrentWeather(ip).observe(viewLifecycleOwner, Observer {
                        when (it.status) {
                            Resource.Status.LOADING -> {

                            }

                            Resource.Status.SUCCESS -> {
                                handleWeatherData(ip,it.data as String)
                            }

                            Resource.Status.ERROR -> {

                            }
                        }
                    })
                }

                Resource.Status.ERROR -> {

                }
            }
        })
    }
    private fun handleWeatherData(ip:String,data:String){
        var data = Gson().fromJson((data), Weather::class.java)
        (requireActivity() as HomeActivity).binding.tvWeather.setText(
            data.main.temp.toInt().toString() + "°C"
        )

        Helper.setImagePromatcally(
            (requireActivity() as HomeActivity).binding.ivCurrentWeather,
            String.format(
                Url.WEATHER_ICON, data.weather.get(0).icon
            )
        )
        model.getAllWeather(ip, "6").observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {

                }

                Resource.Status.SUCCESS -> {
                    try {
                        var d = Gson().fromJson(
                            (it.data as String),
                            com.ibt.ava.service.model.weather.Weather::class.java
                        )
                        binding.weatherNewsSection.tvNow.setText(
                            data.main.temp.toInt().toString() + " پلەی سەدی"
                        )
                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivCurrent,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    0
                                ).weather.get(0).icon
                            )
                        )

                        Helper.setImagePromatcally(
                            (requireActivity() as HomeActivity).binding.ivCurrentWeather,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    0
                                ).weather.get(0).icon
                            )
                        )


                        binding.weatherNewsSection.tvFirstTime.setText(
                            Helper.convertEpochToHour(
                                d.list.get(0).dt.toLong()
                            )
                        )

                        binding.weatherNewsSection.tvSatureday.setText(
                            d.list.get(
                                0
                            ).main.temp.toInt().toString() + "°C"
                        )
                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivFirst,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    0
                                ).weather.get(0).icon
                            )
                        )

                        binding.weatherNewsSection.tvSecondTime.setText(
                            Helper.convertEpochToHour(
                                d.list.get(1).dt.toLong()
                            )
                        )

                        binding.weatherNewsSection.tvSunday.setText(
                            d.list.get(
                                1
                            ).main.temp.toInt().toString() + "°C"
                        )
                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivSecond,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    1
                                ).weather.get(0).icon
                            )
                        )

                        binding.weatherNewsSection.tvThirdTime.setText(
                            Helper.convertEpochToHour(
                                d.list.get(2).dt.toLong()
                            )
                        )

                        binding.weatherNewsSection.tvMonday.setText(
                            d.list.get(
                                2
                            ).main.temp.toInt().toString() + "°C"
                        )

                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivThird,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    2
                                ).weather.get(0).icon
                            )
                        )

                        binding.weatherNewsSection.tvFourthTime.setText(
                            Helper.convertEpochToHour(
                                d.list.get(3).dt.toLong()
                            )
                        )

                        binding.weatherNewsSection.tvTuesday.setText(
                            d.list.get(
                                3
                            ).main.temp.toInt().toString() + "°C"
                        )
                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivFourth,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    3
                                ).weather.get(0).icon
                            )
                        )

                        binding.weatherNewsSection.tvFifthTime.setText(
                            Helper.convertEpochToHour(
                                d.list.get(4).dt.toLong()
                            )
                        )

                        binding.weatherNewsSection.tvWesnesday.setText(
                            d.list.get(
                                4
                            ).main.temp.toInt().toString() + "°C"
                        )
                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivFifth,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    4
                                ).weather.get(0).icon
                            )
                        )

                        binding.weatherNewsSection.tvSixthTime.setText(
                            Helper.convertEpochToHour(
                                d.list.get(5).dt.toLong()
                            )
                        )
                        binding.weatherNewsSection.tvThurday.setText(
                            d.list.get(
                                5
                            ).main.temp.toInt().toString() + "°C"
                        )
                        Helper.setImagePromatcally(
                            binding.weatherNewsSection.ivSixth,
                            String.format(
                                Url.WEATHER_ICON, d.list.get(
                                    5
                                ).weather.get(0).icon
                            )
                        )
                    } catch (ex: Exception) {
                        Log.e("asdsdvsdvb", ex.message.toString())
                    }
                }

                Resource.Status.ERROR -> {

                }
            }
        })
    }

    private fun setupStoryData(storiesAdapter: StoriesAdapter) {

        model.getAllStories().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.status == Resource.Status.LOADING) {
                } else if (it.status == Resource.Status.SUCCESS) {
                    val type: Type =
                        object :
                            TypeToken<ArrayList<Story?>?>() {}.type
                    var stories: ArrayList<Story> = gson.fromJson(it.data.toString(), type)
                    stories.add(0, Story().apply {
                        title = "تەلەفزیۆن"
                        thumbnail = R.drawable.story_tv
                    })
                    stories.add(1, Story().apply {
                        title = "ڕادیۆ"
                        thumbnail = R.drawable.story_mic
                    })
                    storiesAdapter.provideData(stories)
                }
            }
        })
    }


    private fun handleTabSelected(
        tab: Tab?, data: HomeNews, tabDataAdapter: HomePaginatedThirdSectionTabDataAdapter
    ) {
        var gson = Gson()
        // binding.thirdSection.tvFirstAuthorName.paintFlags = binding.thirdSection.tvFirstAuthorName.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        if (tab!!.position == 0) {
            if (data.data.mainnews.size == 0) {
                binding.thirdSection.layoutThirdSectionBody.visibility = View.GONE
            } else {
                binding.thirdSection.tvFirstTitle.setText(data.data.mainnews.get(0).title)
                binding.thirdSection.tvFirstTime.setText(data.data.mainnews.get(0).publishedAt)
                binding.thirdSection.tvFirstCategory.setText(data.data.mainnews.get(0).primaryCategory.name)
                binding.thirdSection.tvFirstAuthorName.setText(
                    data.data.mainnews.get(0).author.get(
                        0
                    ).name
                )
                binding.thirdSection.ivAuthor.tag =
                    gson.toJson(data.data.mainnews.get(0).author.get(0))
                if (data.data.mainnews.get(0).author.get(0).avatar.isNullOrEmpty()) {
                    //  binding.thirdSection.ivAuthor.visibility = View.GONE
                } else {
                    Helper.setImagePromatcally(
                        binding.thirdSection.ivAuthor,
                        data.data.mainnews.get(0).author.get(0).avatar
                    )
                }

                Helper.setImagePromatcally(
                    binding.thirdSection.imageNewsFirst,
                    data.data.mainnews.get(0).featuredImageUrl
                )
                var fList = ArrayList<Featurednews>()
                for (i in 1 until data.data.mainnews.size) {
                    if (i < 5) {
                        fList.add(data.data.mainnews[i])
                    }
                }
                tabDataAdapter.provideData(fList)

                binding.thirdSection.layoutThirdSectionBody.setOnClickListener {
                    var d = data.data.mainnews.get(0)
                    if (!d.mediaGallery.isNullOrEmpty()) {
                        var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                        i.putExtra("id", d.id.toString())
                        i.putExtra("video", false)
                        startActivity(i)
                    } else if (!d.youtubeKey.isNullOrEmpty()) {
                        var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                        i.putExtra("id", d.id.toString())
                        i.putExtra("video", true)
                        startActivity(i)
                    } else if (d.primaryCategory.id == 12) {
                        var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                        i.putExtra("id", d.id.toString())
                        i.putExtra("opinion", true)
                        startActivity(i)
                    } else if (d.isSpecialToAva()) {
                        var i = Intent(context, SingleNewsDetailActivity::class.java)
                        i.putExtra("id", d.id.toString())
                        startActivity(i)
                    } else {
                        var i = Intent(context, SingleNewsDetailActivity::class.java)
                        i.putExtra("id", d.id.toString())
                        startActivity(i)
                    }
                }
                binding.thirdSection.tvFirstAuthorName.setOnClickListener {
                    if (binding.thirdSection.ivAuthor.tag != null) {
                        var f = AuthorProfileFragment()
                        var b = Bundle()
                        b.putString("author", binding.thirdSection.ivAuthor.tag.toString())
                        f.arguments = b
                        f.show((requireActivity() as HomeActivity).supportFragmentManager, null)

                    }
                }
                binding.thirdSection.ivAuthor.setOnClickListener {
                    if (binding.thirdSection.ivAuthor.tag != null) {
                        var f = AuthorProfileFragment()
                        var b = Bundle()
                        b.putString("author", binding.thirdSection.ivAuthor.tag.toString())
                        f.arguments = b
                        f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                    }
                }
            }
        } else {
            model.getNewsByCategory(1, 10, tab.tag.toString().toInt())
                .observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data =
                                Gson().fromJson((it.data as String), CategoryNews::class.java)
                            tabDataAdapter.pageCounter = data.meta.currentPage + 1;
                            if (!data.data.isNullOrEmpty()) {

                                var json = gson.toJson(data.data)
                                val type: Type = object : TypeToken<List<Featurednews?>?>() {}.type

                                var datas: List<Featurednews> = gson.fromJson(json, type)
                                if (datas.isNullOrEmpty()) {
                                    binding.thirdSection.layoutThirdSectionBody.visibility =
                                        View.GONE
                                } else {
                                    binding.thirdSection.tvFirstTitle.setText(datas[0].title)
                                    binding.thirdSection.tvFirstTime.setText(datas[0].publishedAt)
                                    binding.thirdSection.tvFirstCategory.setText(datas[0].primaryCategory.name)
                                    binding.thirdSection.tvFirstAuthorName.setText(
                                        datas[0].author.get(
                                            0
                                        ).name
                                    )
                                    binding.thirdSection.ivAuthor.tag =
                                        gson.toJson(datas[0].author.get(0))
                                    if (datas[0].author.get(0).avatar.isNullOrEmpty()) {
                                        //  binding.thirdSection.ivAuthor.visibility = View.GONE
                                    } else {
                                        Helper.setImagePromatcally(
                                            binding.thirdSection.ivAuthor,
                                            datas[0].author.get(0).avatar
                                        )
                                    }
                                    Helper.setImagePromatcally(
                                        binding.thirdSection.imageNewsFirst,
                                        datas[0].featuredImageUrl
                                    )
                                    var fList = datas.subList(1, datas.size)
                                    tabDataAdapter.provideData(fList)

                                    binding.thirdSection.layoutThirdSectionBody.setOnClickListener {
                                        var d = datas[0]
                                        if (!d.mediaGallery.isNullOrEmpty()) {
                                            var i = Intent(
                                                context,
                                                SingleNewsSpecialDetailActivity::class.java
                                            )
                                            i.putExtra("id", d.id.toString())
                                            i.putExtra("video", false)
                                            startActivity(i)
                                        } else if (!d.youtubeKey.isNullOrEmpty()) {
                                            var i = Intent(
                                                context,
                                                SingleNewsSpecialDetailActivity::class.java
                                            )
                                            i.putExtra("id", d.id.toString())
                                            i.putExtra("video", true)
                                            startActivity(i)
                                        } else if (d.primaryCategory.id == 12) {
                                            var i = Intent(
                                                context,
                                                SingleNewsSpecialDetailActivity::class.java
                                            )
                                            i.putExtra("id", d.id.toString())
                                            i.putExtra("opinion", true)
                                            startActivity(i)
                                        } else if (d.isSpecialToAva()) {
                                            var i = Intent(
                                                context,
                                                SingleNewsDetailActivity::class.java
                                            )
                                            i.putExtra("id", d.id.toString())
                                            startActivity(i)
                                        } else {
                                            var i = Intent(
                                                context,
                                                SingleNewsDetailActivity::class.java
                                            )
                                            i.putExtra("id", d.id.toString())
                                            startActivity(i)
                                        }
                                    }

                                    binding.thirdSection.tvFirstAuthorName.setOnClickListener {
                                        if (binding.thirdSection.ivAuthor.tag != null) {
                                            var f = AuthorProfileFragment()
                                            var b = Bundle()
                                            b.putString(
                                                "author",
                                                binding.thirdSection.ivAuthor.tag.toString()
                                            )
                                            f.arguments = b
                                            f.show(
                                                (requireActivity() as HomeActivity).supportFragmentManager,
                                                null
                                            )

                                        }
                                    }
                                    binding.thirdSection.ivAuthor.setOnClickListener {
                                        if (binding.thirdSection.ivAuthor.tag != null) {
                                            var f = AuthorProfileFragment()
                                            var b = Bundle()
                                            b.putString(
                                                "author",
                                                binding.thirdSection.ivAuthor.tag.toString()
                                            )
                                            f.arguments = b
                                            f.show(
                                                (requireActivity() as HomeActivity).supportFragmentManager,
                                                null
                                            )
                                        }
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


    private fun paginatedTabItemData(
        page: Int,
        limit: Int,
        category: Int,
        tabDataAdapter: HomePaginatedThirdSectionTabDataAdapter,
        gson: Gson
    ) {
        model.getNewsByCategory(page, limit, category)
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if (it.status == Resource.Status.LOADING) {

                    } else if (it.status == Resource.Status.SUCCESS) {
                        Helper.hideProgressDialog()
                        var data =
                            Gson().fromJson((it.data as String), CategoryNews::class.java)
                        tabDataAdapter.pageCounter = data.meta.currentPage + 1;
                        if (!data.data.isNullOrEmpty()) {

                            var json = gson.toJson(data.data)
                            val type: Type = object : TypeToken<List<Featurednews?>?>() {}.type
                            var datas: List<Featurednews> = gson.fromJson(json, type)
                            tabDataAdapter.provideDataInfinite(datas)
                        }

                    } else if (it.status == Resource.Status.ERROR) {
                        Helper.hideProgressDialog()
                    }
                }
            })
    }


    private fun handleBreakingNews() {
        if (data.data.breakingnews.isNullOrEmpty()) {
            binding.breakingNewsSection.layoutFirstScreenBody.visibility = View.GONE
        } else {

            var news = data.data.breakingnews.get(0)
            for (i in data.data.breakingnews) {
                if (i.isHighlightedBreakingNews()) {
                    news = i
                    break
                }
            }

            if (!news!!.liveTitle.isNullOrEmpty()) {
                binding.breakingNewsSection.tvFirstText.text =
                    news!!.liveTitle
            } else {
                binding.breakingNewsSection.tvFirstText.text = "هەواڵی خێرا"
            }


            Glide.with(requireActivity()).asGif().load(R.drawable.radar_first)
                .into(binding.breakingNewsSection.radar)
            binding.breakingNewsSection.tvFirstTitle.setText(news.title)
            binding.breakingNewsSection.tvFirstTime.setText(news.publishedAt)
            Helper.setImagePromatcally(
                binding.breakingNewsSection.imageNewsFirst,
                news.featuredImageUrl
            )

            var fList = ArrayList<Featurednews>()
            for (i in 0 until data.data.breakingnews.size) {
                if (data.data.breakingnews[i].id != news.id) {
                    fList.add(data.data.breakingnews[i])
                }
                /*if (i < 3) {
                    fList.add(data.data.breakingnews[i])
                }*/
            }
            homeFirstRowAdapter.isFromFirst(true)
            homeFirstRowAdapter.provideData(fList)

            binding.breakingNewsSection.btnFirstNews.setOnClickListener {

                var d = news
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

            binding.breakingNewsSection.imageNewsFirst.setOnClickListener {
                var d = news
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

        }

    }

    private fun handleFastNews() {
        if (data.data.fastnews.isNullOrEmpty()) {
            binding.fastNewsSection.layoutSecondSectionBody.visibility = View.GONE
        } else {

            var news = data.data.fastnews.get(0)
            for (i in data.data.fastnews) {
                if (i.isHighlightedBreakingNews()) {
                    news = i
                    break
                }
            }

            if (!news!!.liveTitle.isNullOrEmpty()) {
                binding.fastNewsSection.tvFirstText.text =
                    news!!.liveTitle
            } else {
                binding.fastNewsSection.tvFirstText.text = "رووماڵ"
            }

            Glide.with(requireActivity()).asGif().load(R.drawable.radar_second)
                .into(binding.fastNewsSection.radar)
            binding.fastNewsSection.tvFirstTitle.setText(news.title)
            binding.fastNewsSection.tvFirstTime.setText(news.publishedAt)
            Helper.setImagePromatcally(
                binding.fastNewsSection.imageNewsFirst,
                news.featuredImageUrl
            )

            var fList = ArrayList<Featurednews>()
            for (i in 0 until data.data.fastnews.size) {
                if (data.data.fastnews[i].id != news.id) {
                    fList.add(data.data.fastnews[i])
                }

                /*  if (i < 3) {
                      fList.add(data.data.fastnews[i])
                  }*/
            }
            homeSecondRowAdapter.isFromFirst(false)
            homeSecondRowAdapter.provideData(fList)

            binding.fastNewsSection.btnFirstNews.setOnClickListener {
                var d = news
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }
            binding.fastNewsSection.imageNewsFirst.setOnClickListener {
                var d = news
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }
        }

    }


    private fun handleTabNews() {
        var list = Helper.prepareStaticCategoryData()
        for (i in list.indices) {
            var f = i == 0
            binding.thirdSection.tabs.addTab(
                binding.thirdSection.tabs.newTab().setText(list[i].name)
                    .setTag(list[i].id), f
            )
        }
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable { binding.thirdSection.tabs.getTabAt(0)?.select() }, 100
        )

        binding.thirdSection.tabs.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                if (tab!!.position > 0) {
                    binding.layoutToHide.visibility = View.GONE
                } else {
                    binding.layoutToHide.visibility = View.VISIBLE
                }
                tabDataAdapter.pageCounter = 1
                handleTabSelected(tab, data, tabDataAdapter)
            }

            override fun onTabUnselected(tab: Tab?) {
            }

            override fun onTabReselected(tab: Tab?) {
                if (tab!!.position > 0) {
                    binding.layoutToHide.visibility = View.GONE
                } else {
                    binding.layoutToHide.visibility = View.VISIBLE
                }
                tabDataAdapter.pageCounter = 1
                handleTabSelected(tab, data, tabDataAdapter)
            }

        })
    }


    private fun handleScoopNews() {
        binding.scoopSection.btnScoop.setOnClickListener {
            var f = CategoriesWiseNewsFragment()
            var bun = Bundle()
            bun.putString("id", "scoop")
            bun.putString("name", binding.scoopSection.btnScoop.text.toString())
            f.arguments = bun
            f.show((context as HomeActivity).supportFragmentManager, null)
        }
        homeForthScoopRowAdapter.provideData(data.data.scoop.subList(0, 4))

    }

    private fun handleLatestNews() {
        binding.latestSection.btnLatest.setOnClickListener {
            var f = CategoriesWiseNewsFragment()
            var bun = Bundle()
            bun.putString("id", "latest")
            bun.putString("name", binding.latestSection.btnLatest.text.toString())
            f.arguments = bun
            f.show((context as HomeActivity).supportFragmentManager, null)
        }
        if (data.data.lastestnews.size == 0) {
            binding.latestSection.layoutLatestScreenBody.visibility = View.GONE
        } else {
            binding.latestSection.tvFirstTitle.setText(data.data.lastestnews.get(0).title)
            binding.latestSection.tvFirstTime.setText(data.data.lastestnews.get(0).publishedAt)
            binding.latestSection.tvFirstCategory.setText(
                data.data.lastestnews.get(
                    0
                ).primaryCategory.name
            )
            Helper.setImagePromatcally(
                binding.latestSection.imageNewsFirst,
                data.data.lastestnews.get(0).featuredImageUrl
            )

            var fffList = ArrayList<Featurednews>()
            for (i in 1 until data.data.lastestnews.size) {
                if (i < 4) {
                    fffList.add(data.data.lastestnews[i])
                }
            }
            homeLatestRowAdapter.provideData(fffList)

            binding.latestSection.btnFirstNews.setOnClickListener {
                var d = data.data.lastestnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

            binding.latestSection.imageNewsFirst.setOnClickListener {
                var d = data.data.lastestnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }
        }

    }

    private fun handleKurdistanNews() {
        if (data.data.kurdistannews.size == 0) {
            binding.kurdistanNews.layoutFifthScreenBody.visibility = View.GONE
        } else {

            var fffList1 = ArrayList<Featurednews>()
            var fffList = ArrayList<Featurednews>()
            for (i in 0 until data.data.kurdistannews.size) {
                if (i < 2) {
                    fffList.add(data.data.kurdistannews[i])
                } else {
                    if (i <= 4) {
                        fffList1.add(data.data.kurdistannews[i])
                    }
                }
            }
            homeFifthRowAdapter.provideData(fffList1)
            homeSixth1RowAdapter.provideData(fffList)

            binding.kurdistanNews.btnFifthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.kurdistannews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.kurdistanNews.btnFifthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }


    }

    private fun handleIraqNews() {

        if (data.data.iraqnews.size == 0) {
            binding.iraqNewsSection.layoutSixthScreenBody.visibility = View.GONE
        } else {

            binding.iraqNewsSection.tvFirstTitle.setText(data.data.iraqnews.get(0).title)
            binding.iraqNewsSection.tvFirstTime.setText(
                data.data.iraqnews.get(0).publishedAt
            )
            binding.iraqNewsSection.tvFirstCategory.setText(
                data.data.iraqnews.get(
                    0
                ).primaryCategory.name
            )
            Helper.setImagePromatcally(
                binding.iraqNewsSection.imageNewsFirst,
                data.data.iraqnews.get(0).featuredImageUrl
            )


            binding.iraqNewsSection.btnFirstNews.setOnClickListener {
                var d = data.data.iraqnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

            binding.iraqNewsSection.imageNewsFirst.setOnClickListener {
                var d = data.data.iraqnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }


            var fffList = ArrayList<Featurednews>()
            for (i in 1 until data.data.iraqnews.size) {
                if (i < 4) {
                    fffList.add(data.data.iraqnews[i])
                }
            }
            homeSixth2RowAdapter.provideData(fffList)

            binding.iraqNewsSection.btnSixthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.iraqnews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.iraqNewsSection.btnSixthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }
    }

    private fun handleWorldNews() {

        if (data.data.worldnews.size == 0) {
            binding.worldNewsSection.layoutEightScreenBody.visibility = View.GONE
        } else {
            var fffList = ArrayList<Featurednews>()
            var fffList1 = ArrayList<Featurednews>()
            for (i in 0 until data.data.worldnews.size) {
                if (i < 2) {
                    fffList.add(data.data.worldnews[i])
                } else {
                    if (i < 5) {
                        fffList1.add(data.data.worldnews[i])
                    }
                }
            }
            homeEighthRow1Adapter.provideData(fffList)
            homeEighthRow2Adapter.provideData(fffList1)
            binding.worldNewsSection.btnEightSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.worldnews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.worldNewsSection.btnEightSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }
    }

    private fun handleBusinessNews() {
        if (data.data.businessnews.size == 0) {
            binding.businessNewsSection.layoutSevenScreenBody.visibility = View.GONE
        } else {

            if (!data.data.exchange.isNullOrEmpty()) {
                binding.businessNewsSection.tvExchange.isSelected = true
                var sb = StringBuilder()
                for (i in data.data.exchange) {
                    sb.append(i.amountFrom).append(i.currencyFrom).append(" = ")
                        .append(i.amountTo).append(i.currencyTo).append("   ")
                        .append("|").append("   ")
                }
                var s = sb.toString().trim();
                s = s.substring(0, s.length - 1)
                binding.businessNewsSection.tvExchange.setText(s)
            } else {
                binding.businessNewsSection.exchange.visibility = View.GONE
            }

            binding.businessNewsSection.tvFirstTitle.setText(data.data.businessnews.get(0).title)
            binding.businessNewsSection.tvFirstTime.setText(data.data.businessnews.get(0).publishedAt)
            binding.businessNewsSection.tvFirstCategory.setText(
                data.data.businessnews.get(
                    0
                ).primaryCategory.name
            )
            Helper.setImagePromatcally(
                binding.businessNewsSection.imageNewsFirst,
                data.data.businessnews.get(0).featuredImageUrl
            )
            var fffList = ArrayList<Featurednews>()
            for (i in 1 until data.data.businessnews.size) {
                if (i < 4) {
                    fffList.add(data.data.businessnews[i])
                }
            }
            homeSevenRowAdapter.provideData(fffList)

            binding.businessNewsSection.btnFirstNews.setOnClickListener {
                var d = data.data.businessnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }


            binding.businessNewsSection.imageNewsFirst.setOnClickListener {
                var d = data.data.businessnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

            binding.businessNewsSection.btnSevenSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.businessnews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.businessNewsSection.btnSevenSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }

    }

    private fun handleOpinionNews() {
        if (data.data.opinionnews.size == 0) {
            binding.opinionNewsSection.layoutNinethScreenBody.visibility = View.GONE
        } else {

            var news = data.data.opinionnews.get(0)
            for (i in data.data.opinionnews) {
                if (i.isHighlightedOpinion()) {
                    news = i
                    break
                }
            }

            binding.opinionNewsSection.tvFirstTitle.setText(news.title)
            binding.opinionNewsSection.tvFirstTime.setText(news.publishedAt)
            binding.opinionNewsSection.tvFirstAuthorName.setText(
                data.data.opinionnews.get(
                    0
                ).author.get(0).name
            )
            binding.opinionNewsSection.ivAuthor.tag =
                gson.toJson(data.data.mainnews.get(0).author.get(0))
            if (news.author.get(0).avatar.isNullOrEmpty()) {
                //  binding.opinionNewsSection.ivAuthor.visibility = View.GONE
            } else {
                Helper.setImagePromatcally(
                    binding.opinionNewsSection.ivAuthor,
                    news.author.get(0).avatar
                )
            }


            Helper.setImagePromatcally(
                binding.opinionNewsSection.imageNewsFirst,
                news.featuredImageUrl
            )

            var fffList = ArrayList<Featurednews>()
            for (i in 0 until data.data.opinionnews.size) {
                if (fffList.size < 4) {
                    if (data.data.opinionnews[i].id != news.id) {
                        fffList.add(data.data.opinionnews[i])
                    }
                }
            }
            homeSectionNineAdapter.provideData(fffList)

            binding.opinionNewsSection.btnFirstNews.setOnClickListener {
                var i = Intent(
                    requireActivity(),
                    SingleNewsSpecialDetailActivity::class.java
                )
                i.putExtra("id", news.id.toString())
                i.putExtra("opinion", true)
                startActivity(i)
            }

            binding.opinionNewsSection.imageNewsFirst.setOnClickListener {
                var i = Intent(
                    requireActivity(),
                    SingleNewsSpecialDetailActivity::class.java
                )
                i.putExtra("id", news.id.toString())
                i.putExtra("opinion", true)
                startActivity(i)
            }

            binding.opinionNewsSection.ivAuthor.setOnClickListener {
                if (binding.opinionNewsSection.ivAuthor.tag != null) {
                    var f = AuthorProfileFragment()
                    var b = Bundle()
                    b.putString("author", binding.opinionNewsSection.ivAuthor.tag.toString())
                    f.arguments = b
                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                }
            }

            binding.opinionNewsSection.tvFirstAuthorName.setOnClickListener {
                if (binding.opinionNewsSection.ivAuthor.tag != null) {
                    var f = AuthorProfileFragment()
                    var b = Bundle()
                    b.putString("author", binding.opinionNewsSection.ivAuthor.tag.toString())
                    f.arguments = b
                    f.show((requireActivity() as HomeActivity).supportFragmentManager, null)
                }
            }

            binding.opinionNewsSection.btnNinthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    news.primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.opinionNewsSection.btnNinthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }

    }

    private fun handleMultimediaNews() {

        var mList = ArrayList<Featurednews>()

        for (i in data.data.multimedianews) {
            if (!i.youtubeKey.isNullOrEmpty() || !i.kwikmotionKey.isNullOrEmpty()) {
                mList.add(i)
            }
        }

        if (mList.size == 0) {
            binding.multimediaNewsSection.layoutTenthScreenBody.visibility = View.GONE
        } else {
            binding.multimediaNewsSection.tvFirstTitle.setText(mList[0].title)
            binding.multimediaNewsSection.tvFirstTime.setText(mList[0].publishedAt)
            Helper.setImagePromatcally(
                binding.multimediaNewsSection.imageNewsFirst, mList[0].featuredImageUrl
            )
            var fffList = ArrayList<Featurednews>()
            for (i in 1 until mList.size) {
                if (i < 4) {
                    fffList.add(mList[i])
                }
            }
            homeVideoAdapter.provideData(fffList)

            binding.multimediaNewsSection.btnFirstNews.setOnClickListener {
                var i = Intent(
                    requireActivity(),
                    SingleNewsSpecialDetailActivity::class.java
                )
                i.putExtra("id", mList[0].id.toString())
                i.putExtra("video", true)
                startActivity(i)
            }



            binding.multimediaNewsSection.imageNewsFirst.setOnClickListener {
                var i = Intent(
                    requireActivity(),
                    SingleNewsSpecialDetailActivity::class.java
                )
                i.putExtra("id", mList[0].id.toString())
                i.putExtra("video", true)
                startActivity(i)
            }

            binding.multimediaNewsSection.btnPlay.setOnClickListener {
                var i = Intent(requireActivity(), SingleNewsSpecialDetailActivity::class.java)
                i.putExtra("id", mList[0].id.toString())
                i.putExtra("video", true)
                startActivity(i)
            }

            binding.multimediaNewsSection.btnTenthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.multimedianews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.multimediaNewsSection.btnTenthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }
    }

    private fun handleReportNews() {

        if (data.data.reportnews.size == 0) {
            binding.reportSection.layoutSixthScreenBody.visibility = View.GONE
        } else {

            binding.reportSection.tvFirstTitle.setText(data.data.reportnews.get(0).title)
            binding.reportSection.tvFirstTime.setText(
                data.data.reportnews.get(0).publishedAt
            )
            binding.reportSection.tvFirstCategory.setText(
                data.data.reportnews.get(
                    0
                ).primaryCategory.name
            )
            Helper.setImagePromatcally(
                binding.reportSection.imageNewsFirst,
                data.data.reportnews.get(0).featuredImageUrl
            )


            binding.reportSection.btnFirstNews.setOnClickListener {
                var d = data.data.reportnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

            binding.reportSection.imageNewsFirst.setOnClickListener {
                var d = data.data.reportnews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }


            var fffList = ArrayList<Featurednews>()
            for (i in 1 until data.data.reportnews.size) {
                if (i < 4) {
                    fffList.add(data.data.reportnews[i])
                }
            }
            homeSixth2ReportRowAdapter.provideData(fffList)

            binding.reportSection.btnSixthSection.text="راپۆرت"

            binding.reportSection.btnSixthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.reportnews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.reportSection.btnSixthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }


    }

    private fun handleHealthNews() {
        if (data.data.healthnews.size == 0) {
            binding.healthSection.layoutFifthScreenBody.visibility = View.GONE
        } else {

            var fffList1 = ArrayList<Featurednews>()
            var fffList = ArrayList<Featurednews>()
            for (i in 0 until data.data.healthnews.size) {
                if (i < 2) {
                    fffList.add(data.data.healthnews[i])
                } else {
                    if (i <= 4) {
                        fffList1.add(data.data.healthnews[i])
                    }
                }
            }
            homeFifthRowHealthAdapter.provideData(fffList1)
            homeSixth1RowHealthAdapter.provideData(fffList)

            binding.healthSection.btnFifthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.healthnews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.healthSection.btnFifthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
            binding.healthSection.btnFifthSection.text="تەندروستی"
        }

    }

    private fun handleCultureNews() {
        if (data.data.culturenews.size == 0) {
            binding.cultureSection.layoutSixthScreenBody.visibility = View.GONE
        } else {

            binding.cultureSection.tvFirstTitle.setText(data.data.culturenews.get(0).title)
            binding.cultureSection.tvFirstTime.setText(
                data.data.culturenews.get(0).publishedAt
            )
            binding.cultureSection.tvFirstCategory.setText(
                data.data.culturenews.get(
                    0
                ).primaryCategory.name
            )
            Helper.setImagePromatcally(
                binding.cultureSection.imageNewsFirst,
                data.data.culturenews.get(0).featuredImageUrl
            )


            binding.cultureSection.btnFirstNews.setOnClickListener {
                var d = data.data.culturenews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }

            binding.cultureSection.imageNewsFirst.setOnClickListener {
                var d = data.data.culturenews.get(0)
                if (!d.mediaGallery.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", false)
                    startActivity(i)
                } else if (!d.youtubeKey.isNullOrEmpty()) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("video", true)
                    startActivity(i)
                } else if (d.primaryCategory.id == 12) {
                    var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    i.putExtra("opinion", true)
                    startActivity(i)
                } else if (d.isSpecialToAva()) {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                } else {
                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                    i.putExtra("id", d.id.toString())
                    startActivity(i)
                }
            }


            var fffList = ArrayList<Featurednews>()
            for (i in 1 until data.data.culturenews.size) {
                if (i < 4) {
                    fffList.add(data.data.culturenews[i])
                }
            }
            homeSixth2CultureRowAdapter.provideData(fffList)

            binding.cultureSection.btnSixthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.culturenews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.cultureSection.btnSixthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }

            binding.cultureSection.btnSixthSection.text="کولتوور"
        }

    }

    private fun handleEnvironmentsNews() {
        if (data.data.environmentnews.size == 0) {
            binding.environmentsSection.layoutFifthScreenBody.visibility = View.GONE
        } else {

            var fffList1 = ArrayList<Featurednews>()
            var fffList = ArrayList<Featurednews>()
            for (i in 0 until data.data.environmentnews.size) {
                if (i < 2) {
                    fffList.add(data.data.environmentnews[i])
                } else {
                    if (i <= 4) {
                        fffList1.add(data.data.environmentnews[i])
                    }
                }
            }
            homeFifthRowEnvironmentsAdapter.provideData(fffList1)
            homeSixth1RowEnvironmentsAdapter.provideData(fffList)

            binding.environmentsSection.btnFifthSection.setOnClickListener {
                var f = CategoriesWiseNewsFragment()
                var bun = Bundle()
                bun.putString(
                    "id",
                    data.data.environmentnews.get(0).primaryCategory.id.toString()
                )
                bun.putString(
                    "name",
                    binding.environmentsSection.btnFifthSection.text.toString()
                )
                f.arguments = bun
                f.show((context as HomeActivity).supportFragmentManager, null)
            }

            binding.environmentsSection.btnFifthSection.text="ژینگە"
        }

    }

    private fun handlePodcastsNews() {
        if (data.data.podcasts.size == 0) {
            binding.podcastNewsSection.layoutTweleveScreenBody.visibility = View.GONE
        } else {
            homePodcastAdapter.provideData(data.data.podcasts)
            binding.podcastNewsSection.picker.adapter = homePodcastAdapter
            binding.podcastNewsSection.picker.setItemTransformer(
                ScaleTransformer.Builder().setMaxScale(0.96f).setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                    .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                    .build()
            )
            if (data.data.podcasts.size > 1) binding.podcastNewsSection.picker.scrollToPosition(
                1
            )

            binding.podcastNewsSection.btnTwelveSection.setOnClickListener {
                var f = AllPodcastsFragment()
                f.show((context as HomeActivity).supportFragmentManager, null)
            }
        }

    }

}