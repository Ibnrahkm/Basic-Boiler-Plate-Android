package com.ibt.ava.view.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutMultimediaFragmentBinding
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.multimedia.Data
import com.ibt.ava.service.model.news.multimedia.MultimediaNews
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.util.TouchDetectableScrollView.OnMyScrollChangeListener
import com.ibt.ava.view.adapter.AllPodcastsAdapter
import com.ibt.ava.view.adapter.MultimediaImagesDataAdapter
import com.ibt.ava.view.adapter.MultimediaVideoTypeAdapter
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import java.lang.reflect.Type


class MultimediaFragment : BaseFragment() {

    lateinit var binding: LayoutMultimediaFragmentBinding
    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutMultimediaFragmentBinding>(
            inflater, R.layout.layout_multimedia_fragment, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as HomeActivity).navButtonHandler("video")
        var adapter = MultimediaVideoTypeAdapter(requireActivity())
        var adapterImages = MultimediaImagesDataAdapter(requireActivity())
        var adapterPodcast = AllPodcastsAdapter(requireActivity())

        if (requireArguments().containsKey("podcast")) {
            binding.layoutToHide.visibility = View.GONE
            binding.recVideos.adapter = adapterPodcast
            model.getAllPodcasts(1, 10).observe(viewLifecycleOwner, Observer {
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
                            adapterPodcast.provideData(ArrayList(data.data.data))
                        }
                    } else {
                        Helper.hideProgressDialog()
                    }
                }
            })


            adapterPodcast.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                if (it != null) {
                    adapterPodcast.loadMoreForScroll.value = null
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
                                    adapterPodcast.provideDataInfinite(ArrayList(data.data.data))
                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
                }
            })
        } else {
            var video = requireArguments().getBoolean("multimedia")

            if (video) {
                binding.layoutVideo.visibility = View.VISIBLE
                binding.layoutImages.visibility = View.GONE
                binding.recVideos.adapter = adapter
            } else {
                binding.layoutVideo.visibility = View.GONE
                binding.layoutImages.visibility = View.VISIBLE
                binding.recVideos.adapter = adapterImages
            }
            model.getMultimediaNews(video, 1, 10)
                .observe(viewLifecycleOwner, Observer {
                    if (it != null) {

                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data =
                                Gson().fromJson((it.data as String), MultimediaNews::class.java)

                            if (data != null && !data.data.isNullOrEmpty()) {
                                var mainList = ArrayList<Data>()

                                for (i in data.data) {
                                    if (video) {
                                        Log.e("asvsdvsdvsd", video.toString())
                                        if (!i.youtubeKey
                                                .isNullOrEmpty() || !i.kwikmotionKey
                                                .isNullOrEmpty()
                                        ) {
                                            mainList.add(i)
                                        }
                                    } else {
                                        if (i.youtubeKey
                                                .isNullOrEmpty() && i.kwikmotionKey
                                                .isNullOrEmpty()
                                        ) {
                                            mainList.add(i)
                                        }
                                    }

                                }

                                if (mainList.size > 0) {
                                    if (video) {
                                        binding.tvFirstTitle.setText(mainList[0].title)
                                        binding.tvFirstTime.setText(mainList[0].publishedAt)
                                        binding.tvFirstCategory.setText(mainList[0].primaryCategory.name)
                                        Helper.setImagePromatcally(
                                            binding.imageNewsFirst, mainList[0].featuredImageUrl
                                        )

                                        binding.layoutVideo.setOnClickListener {
                                            var i = Intent(
                                                requireActivity(),
                                                SingleNewsSpecialDetailActivity::class.java
                                            )
                                            i.putExtra("id", mainList[0].id.toString())
                                            i.putExtra("video", true)
                                            startActivity(i)
                                        }

                                        binding.btnPlay.setOnClickListener {
                                            var i = Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                            i.putExtra("id", mainList[0].id.toString())
                                            i.putExtra("video", true)
                                            startActivity(i)
                                        }

                                    } else {
                                        binding.tvFirstTitleImages.setText(mainList[0].title)
                                        binding.tvFirstTimeImages.setText(mainList[0].publishedAt)
                                        binding.tvFirstCategoryImages.setText(mainList[0].primaryCategory.name)
                                        binding.tvImagesCount.setText(mainList[0].mediaGallery.size.toString() + "وێنە ")
                                        Helper.setImagePromatcally(
                                            binding.imageNewsFirstImage,
                                            mainList[0].featuredImageUrl
                                        )
                                        binding.layoutImages.setOnClickListener {
                                            var i = Intent(
                                                requireActivity(),
                                                SingleNewsSpecialDetailActivity::class.java
                                            )
                                            i.putExtra("id", mainList[0].id.toString())
                                            i.putExtra("video", false)
                                            startActivity(i)
                                        }
                                    }

                                    var list = ArrayList<Data>()
                                    for (i in 1 until mainList.size) {
                                        list.add(mainList[i])
                                    }
                                    if (video) {
                                        adapter.provideData(list)
                                    } else {
                                        adapterImages.provideData(list)
                                    }
                                }
                            }
                        } else if (it.status == Resource.Status.ERROR) {
                            Helper.hideProgressDialog()
                            Log.e("dasdvsdv", it.error!!.message.toString())
                        }
                    }
                })
        }


        binding.scrollView.myScrollChangeListener = object : OnMyScrollChangeListener {
            override fun onScrollUp() {
            }

            override fun onScrollDown() {
            }

            override fun onBottomReached() {
                if (requireArguments().containsKey("podcast")) {
                    if (adapterPodcast.currentSize > 0){
                        adapterPodcast.loadMoreForScroll.value = ++adapterPodcast.pageCounterForScroll
                    }
                } else if (requireArguments().containsKey("multimedia")) {
                    var video = requireArguments().getBoolean("multimedia")
                    if (video) {
                        if (adapter.currentSize>0){
                            adapter.loadMoreForScroll.value = ++adapter.pageCounterForScroll
                        }
                    } else {
                        if (adapterImages.currentSize>0){
                            adapterImages.loadMoreForScroll.value = ++adapterImages.pageCounterForScroll
                        }
                    }
                }
            }
        }


        if (requireArguments().containsKey("multimedia")) {
            var video = requireArguments().getBoolean("multimedia")
            if (video) {
                adapter.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                    if (it != null) {
                        adapter.loadMoreForScroll.value = null
                        model.getMultimediaNews(video, it, 10)
                            .observe(viewLifecycleOwner, Observer {
                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        var data = Gson().fromJson(
                                            (it.data as String),
                                            MultimediaNews::class.java
                                        )
                                        var mainList = ArrayList<Data>()
                                        if (data != null && !data.data.isNullOrEmpty()) {
                                            for (i in data.data) {
                                                if (!i.youtubeKey
                                                        .isNullOrEmpty() || !i.kwikmotionKey
                                                        .isNullOrEmpty()
                                                ) {
                                                    mainList.add(i)
                                                }
                                            }
                                        }
                                        adapter.provideDataInfinite(mainList)
                                    } else if (it.status == Resource.Status.ERROR) {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })
                    }


                })
            } else {
                adapterImages.loadMoreForScroll.observe(viewLifecycleOwner, Observer {

                    if (it != null) {
                        adapterImages.loadMoreForScroll.value = null
                        model.getMultimediaNews(video, it, 10)
                            .observe(viewLifecycleOwner, Observer {
                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        var data = Gson().fromJson(
                                            (it.data as String),
                                            MultimediaNews::class.java
                                        )
                                        var mainList = ArrayList<Data>()
                                        if (data != null && !data.data.isNullOrEmpty()) {
                                            for (i in data.data) {
                                                if (i.youtubeKey
                                                        .isNullOrEmpty() && i.kwikmotionKey
                                                        .isNullOrEmpty()
                                                ) {
                                                    mainList.add(i)
                                                }
                                            }
                                        }
                                        adapterImages.provideDataInfinite(mainList)
                                    } else if (it.status == Resource.Status.ERROR) {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })
                    }


                })
            }

        }

    }
}