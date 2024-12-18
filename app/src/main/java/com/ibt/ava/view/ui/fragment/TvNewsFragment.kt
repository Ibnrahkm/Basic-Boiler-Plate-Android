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
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
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
import com.ibt.ava.databinding.LayoutTvNewsFragmentBinding
import com.ibt.ava.service.model.Stories
import com.ibt.ava.service.model.currentweather.Weather
import com.ibt.ava.service.model.news.categories.Categories
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.news.home.HomeNews
import com.ibt.ava.service.model.news.multimedia.Data
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
import com.ibt.ava.view.adapter.MultimediaVideoTypeAdapter
import com.ibt.ava.view.adapter.StoriesAdapter
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import java.lang.reflect.Type


class TvNewsFragment : BaseFragment() {

    lateinit var binding: LayoutTvNewsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutTvNewsFragmentBinding>(
            inflater, R.layout.layout_tv_news_fragment, null, false
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
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webChromeClient = WebChromeClient()
        binding.webview.webViewClient = WebViewClient()
        val htmlData = getHTMLData(requireArguments().getString(
            "url"
        ).toString()
            .split("=")[1])
               /*binding.webview.loadDataWithBaseURL(
                   "https://www.youtube.com",
                   htmlData,
                   "text/html",
                   "UTF-8",
                   null
               )*/
        binding.webview.loadDataWithBaseURL(
                  "https://www.youtube.com",
                  htmlData,
                  "text/html",
                  "UTF-8",
                  null
              )


    }



    fun getData(videoId: String):String{
        var html="<html>\n" +
                "<body height=\"100%\">\n" +
                "<table height=\"100%\" width=\"100%\">\n" +
                "  <tr>\n" +
                "    <td valign=\"middle\" align=\"center\">\n" +
                "      <iframe allow=\"autoplay\" allowfullscreen=\"allowfullscreen\"\n" +
                "        mozallowfullscreen=\"mozallowfullscreen\" \n" +
                "        msallowfullscreen=\"msallowfullscreen\" \n" +
                "        oallowfullscreen=\"oallowfullscreen\" \n" +
                "        webkitallowfullscreen=\"webkitallowfullscreen\" style=\"width:100%;height:500px;\" class=\"youtube-player\" type=\"text/html\" src=\"https://www.youtube.com/embed/$videoId?wmode=opaque&autohide=1&autoplay=1&volume=0&vol=0&mute=1\" frameborder=\"0\">&lt;br /&gt;</iframe>\n" +
                "  </td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>"
        return html
    }

    fun getHTMLData(videoId: String): String {
        return """
        <html>
        
            <body style="margin:0px;padding:0px;">
               <div id="player"></div>
                <script>
                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '500px',
                            width: '100%',
                            videoId: '$videoId',
                            'rel': 0,
                            playerVars: {
                                'playsinline': 1,
                                'autoplay': 1,
                                'mute': 1,
                                'controls': 1,
                                'modestbranding': 1
                            },
                            events: {
                                'onReady': onPlayerReady
                            }
                        });
                    }
                    function onPlayerReady(event) {
                     player.playVideo();
                        // Player is ready
                    }
                    function seekTo(time) {
                        player.seekTo(time, true);
                    }
                      function playVideo() {
                        player.playVideo();
                    }
                    function pauseVideo() {
                        player.pauseVideo();
                    }
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """.trimIndent()
    }
}