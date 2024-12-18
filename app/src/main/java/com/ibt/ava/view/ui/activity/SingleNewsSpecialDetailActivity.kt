package com.ibt.ava.view.ui.activity


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SeekBar
import android.widget.TableLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ibt.ava.service.model.ShowcaseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.ActivitySingleNewsSpecialBinding
import com.ibt.ava.databinding.LayoutBookmarkedBinding
import com.ibt.ava.databinding.LayoutTextResizerBinding
import com.ibt.ava.service.model.TransactionHistoryModel
import com.ibt.ava.service.model.news.categorynews.CategoryNews
import com.ibt.ava.service.model.news.details.NewsDetails
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.adapter.HomeThirdSectionTabDataAdapter
import com.ibt.ava.view.adapter.ImageGalleryAdapter
import com.ibt.ava.view.adapter.ShowCasePagerAdapter
import com.ibt.ava.view.adapter.TabRowAdapter
import com.ibt.ava.view.ui.fragment.AuthorProfileFragment
import com.ibt.ava.view.ui.fragment.FollowFragment
import com.ibt.ava.view.ui.fragment.TagNewsFragment
import com.ibt.ava.view.ui.fragment.TvNewsFragment
import org.json.JSONObject
import java.lang.reflect.Type


/*
* transaction detail screen
* */
class SingleNewsSpecialDetailActivity : BaseActivity() {

    companion object {
        val model = MutableLiveData<TransactionHistoryModel>()
    }

    private lateinit var binding: ActivitySingleNewsSpecialBinding
    var shareLink = "https://www.ava.news/news/"
    var youtubeKey = "";
    lateinit var viewpagerAdapter: ShowCasePagerAdapter
    var id = ""
    var authorId = ""
    lateinit var imageGalleryAdapter: ImageGalleryAdapter
    var gson = Gson()
    var newsData = NewsDetails()

    lateinit var homeThirdSectionTabDataAdapter: HomeThirdSectionTabDataAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Helper.handleStatusBar(
            window!!, ContextCompat.getColor(
                this@SingleNewsSpecialDetailActivity, R.color.transparent
            ), this@SingleNewsSpecialDetailActivity
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_news_special)

        binding.ivToolLeft.setOnClickListener {
            finish()
        }

        homeThirdSectionTabDataAdapter = HomeThirdSectionTabDataAdapter(this)
        binding.rec.adapter = homeThirdSectionTabDataAdapter
        if (intent.hasExtra("notes")) {
            binding.layoutNotes.visibility = View.VISIBLE
            binding.tvNotes.text = intent.getStringExtra("notes");
        }
        binding.ivAuthor.setOnClickListener {
            if (binding.ivAuthor.tag != null) {
                var f = AuthorProfileFragment()
                var b = Bundle()
                b.putString("author", binding.ivAuthor.tag.toString())
                f.arguments = b
                f.show(supportFragmentManager, null)
            }

        }
        binding.tvAuthor.setOnClickListener {
            if (binding.ivAuthor.tag != null) {
                var f = AuthorProfileFragment()
                var b = Bundle()
                b.putString("author", binding.ivAuthor.tag.toString())
                f.arguments = b
                f.show(supportFragmentManager, null)
            }

        }

        binding.tvFirstAuthorName.setOnClickListener {
            if (binding.ivAuthor.tag != null) {
                var f = AuthorProfileFragment()
                var b = Bundle()
                b.putString("author", binding.ivAuthor.tag.toString())
                f.arguments = b
                f.show(supportFragmentManager, null)
            }

        }

        binding.btnEditNote.setOnClickListener {
            var f = FollowFragment()
            var bundle = Bundle()
            bundle.putString("id", id)
            if (!intent.hasExtra("bid") || !intent.hasExtra("notes")) {
                bundle.putString("bid", binding.tvNotes.tag.toString())
                bundle.putString("notes", binding.tvNotes.text.toString())
            } else {
                bundle.putString("bid", intent.getStringExtra("bid"))
                bundle.putString("notes", intent.getStringExtra("notes"))
            }
            f.arguments = bundle
            f.show(supportFragmentManager, null)
        }
        binding.btnAddList.tag = false
        binding.btnAddList.setOnClickListener {
            if (AppController.loggedIn()) {
                val user = Gson().fromJson(AppController.getUserData(), User::class.java)
                var status = (binding.btnAddList.tag as Boolean)
                if (!status) {
                    model.createFollow("Bearer ${user.accessToken}", authorId)
                        .observe(this, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", this)
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    binding.btnAddList.tag = true
                                    binding.btnAddList.setImageResource(R.drawable.unfollow_details)
                                } else {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
                } else {
                    model.deleteFollow("Bearer ${user.accessToken}", authorId)
                        .observe(this, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", this)
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    binding.btnAddList.tag = false
                                    binding.btnAddList.setImageResource(R.drawable.follow_detail)
                                } else {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
                }
            } else {
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }
        }

        binding.ivToolFav.tag = false
        binding.ivToolFav.setOnClickListener {
            if (AppController.loggedIn()) {
                val user = Gson().fromJson(AppController.getUserData(), User::class.java)
                var status = !(binding.ivToolFav.tag as Boolean)
                if (status) {
                    var f = FollowFragment()
                    var bundle = Bundle()
                    bundle.putString("id", id)
                    f.arguments = bundle
                    f.show(supportFragmentManager, null)
                } else {
                    model.deleteBookmark("Bearer ${user.accessToken}", id).observe(this, Observer {
                        if (it != null) {
                            if (it.status == Resource.Status.SUCCESS) {
                                binding.ivToolFav.tag = false
                                binding.ivToolFav.setImageResource(R.drawable.save)
                                binding.layoutNotes.visibility = View.GONE
                            }
                        }
                    })
                }
            } else {
                var status = !(binding.ivToolFav.tag as Boolean)
                if (status) {
                    var f = FollowFragment()
                    var bundle = Bundle()
                    bundle.putString("id", newsData.id.toString())
                    bundle.putString("title", newsData.title)
                    bundle.putString("image", newsData.featuredImageUrl)
                    bundle.putString("category", newsData.primaryCategory.name)
                    f.arguments = bundle
                    f.show(supportFragmentManager, null)
                } else {
                    val data = AppController.getBookmarks()
                    var p = -1
                    for (i in data.indices) {
                        if (data[i].newsId.equals(id.toInt())) {
                            p = i
                        }
                    }
                    if (p > -1) {
                        data.removeAt(p)
                    }
                    AppController.setBookmarks(data)
                    binding.ivToolFav.tag = false
                    binding.ivToolFav.setImageResource(R.drawable.save)
                    binding.layoutNotes.visibility = View.GONE
                }
            }
        }


        FollowFragment.bookmarked.observe(this, Observer {
            if (it!=null) {
                if (intent.hasExtra("notes")) {
                    binding.layoutNotes.visibility = View.VISIBLE
                    binding.tvNotes.text = it
                } else {
                    var dialog = Dialog(this)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
                    var binding = DataBindingUtil.inflate<LayoutBookmarkedBinding>(
                        layoutInflater, R.layout.layout_bookmarked, null, false
                    )
                    dialog.setContentView(binding.root)
                    dialog.window!!.setLayout(
                        resources.getDimensionPixelSize(R.dimen._130sdp),
                        resources.getDimensionPixelSize(R.dimen._40sdp)
                    )
                    dialog.show()
                }
                binding.ivToolFav.setImageResource(R.drawable.save_ok)
                binding.ivToolFav.tag = true
                binding.tvNotes.text = it
                binding.tvNotes.tag = "0"
                binding.layoutNotes.visibility = View.VISIBLE
                FollowFragment.bookmarked.value = null
            }
        })

        Helper.handleInternet(this, binding)
        //binding.tvFirstAuthorName.paintFlags = binding.tvFirstAuthorName.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        viewpagerAdapter = ShowCasePagerAdapter(this)
        imageGalleryAdapter = ImageGalleryAdapter(this)
        binding.recGallery.adapter = imageGalleryAdapter
        binding.appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            if (Math.abs(verticalOffset) >= totalScrollRange) {
                // AppBar is fully collapsed

                if (intent.hasExtra("opinion")) {
                    binding.btnOpinion.visibility = View.GONE
                }
                if (intent.hasExtra("video") && !intent.getBooleanExtra("video", false)) {
                    binding.cat.visibility = View.GONE
                }
            } else {
                // AppBar is expanded or partially expanded
                if (intent.hasExtra("opinion")) {
                    binding.btnOpinion.visibility = View.VISIBLE
                }
                if (intent.hasExtra("video") && !intent.getBooleanExtra("video", false)) {
                    binding.cat.visibility = View.VISIBLE
                }
            }
        }
        binding.ivToolShare.setOnClickListener {
            if (!shareLink.isNullOrEmpty()) {
                ShareCompat.IntentBuilder.from(this).setType("text/plain")
                    .setChooserTitle("Share news").setText(shareLink).startChooser();
            }

        }
        binding.viewPager.adapter = viewpagerAdapter
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                binding.pageIndicatorView.selection = position
            }

        })

        if (intent.hasExtra("id")) {


            binding.tvContents.webViewClient= object : WebViewClient(){
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    try{
                        var url=request!!.url
                        var s = url.toString().split("/")
                        var id=s[s.size - 1]
                        model.getNewsDetails(id).observe(this@SingleNewsSpecialDetailActivity, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", this@SingleNewsSpecialDetailActivity)
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    var data = Gson().fromJson((it.data as String), NewsDetails::class.java)
                                    if (data != null) {
                                        if (!data.mediaGallery.isNullOrEmpty()) {
                                            var i = Intent(this@SingleNewsSpecialDetailActivity, SingleNewsSpecialDetailActivity::class.java)
                                            i.putExtra("id", data.id.toString())
                                            i.putExtra("video", false)
                                            startActivity(i)
                                        } else if (!data.youtubeKey.isNullOrEmpty()) {
                                            var i = Intent(this@SingleNewsSpecialDetailActivity, SingleNewsSpecialDetailActivity::class.java)
                                            i.putExtra("id", data.id.toString())
                                            i.putExtra("video", true)
                                            startActivity(i)
                                        } else if (data.primaryCategory.id == 12) {
                                            var i = Intent(this@SingleNewsSpecialDetailActivity, SingleNewsSpecialDetailActivity::class.java)
                                            i.putExtra("id", data.id.toString())
                                            i.putExtra("opinion", true)
                                            startActivity(i)
                                        } else if (data.isSpecialToAva) {
                                            var i = Intent(this@SingleNewsSpecialDetailActivity, SingleNewsDetailActivity::class.java)
                                            i.putExtra("id", data.id.toString())
                                            startActivity(i)
                                        } else {
                                            var i = Intent(this@SingleNewsSpecialDetailActivity, SingleNewsDetailActivity::class.java)
                                            i.putExtra("id", data.id.toString())
                                            startActivity(i)
                                        }
                                    }
                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }
                        })
                    }catch(ex:Exception){
                        return false
                    }
                    return true;
                }
            }




            binding.tvContents.settings.javaScriptEnabled = true
            binding.tvContents.settings.loadWithOverviewMode = true;
            binding.tvContents.settings.useWideViewPort = true;
            binding.tvContents.settings.textZoom += 100
            var min = this.binding.tvContents.settings.textZoom - 20
            var max = this.binding.tvContents.settings.textZoom + 100
            binding.ivToolTextSize.setOnClickListener {

                var dialog = Dialog(this)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
                var binding = DataBindingUtil.inflate<LayoutTextResizerBinding>(
                    layoutInflater, R.layout.layout_text_resizer, null, false
                )
                dialog.setContentView(binding.root)

                binding.seekbar.min = min
                binding.seekbar.max = max
                binding.seekbar.progress = this.binding.tvContents.settings.textZoom
                binding.seekbar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                        textBigger(p1)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {
                    }

                    override fun onStopTrackingTouch(p0: SeekBar?) {
                    }

                })
                dialog.show()
            }
            if (intent.hasExtra("video")) {
                if (intent.getBooleanExtra("video", false)) {
                    binding.btnPlay.visibility = View.VISIBLE
                } else {
                    binding.layoutColor.setBackgroundColor(Color.parseColor("#081e58"))
                    binding.btnPlay.visibility = View.GONE
                    binding.hide.visibility = View.GONE
                    binding.hideNormal.visibility = View.GONE
                    binding.hideViewpager.visibility = View.VISIBLE
                    binding.cat.visibility = View.VISIBLE
                    binding.hideSpecial.visibility = View.VISIBLE
                    binding.authorHide.visibility = View.GONE
                    binding.tabs.visibility = View.GONE
                    binding.imagesGallery.visibility = View.VISIBLE
                    // binding.tvTitleToPadding.setPadding(0,10,0,0)
                }
            } else {
                binding.btnPlay.visibility = View.GONE
            }

            if (intent.hasExtra("opinion")) {
                binding.btnOpinion.visibility = View.VISIBLE
                var l: CoordinatorLayout.LayoutParams =
                    binding.scrollviewMargin.layoutParams as CoordinatorLayout.LayoutParams
                l.topMargin = 15
                binding.scrollviewMargin.layoutParams = l
            }

            binding.btnPlay.setOnClickListener {
                if (!youtubeKey.isNullOrEmpty()) {
                    var b = Bundle()
                    b.putString("url", "https://www.youtube.com/watch?v=$youtubeKey")
                    var f = TvNewsFragment()
                    f.arguments = b
                    f.show(supportFragmentManager, null)
                    //Helper.openYoutube(youtubeKey, this)
                }
            }

            model.getNewsDetails(intent.getStringExtra("id")!!).observe(this, Observer {
                if (it != null) {
                    if (it.status == Resource.Status.LOADING) {
                        Helper.showProgressDialog("", "", this)
                    } else if (it.status == Resource.Status.SUCCESS) {
                        Helper.hideProgressDialog()
                        var data = Gson().fromJson((it.data as String), NewsDetails::class.java)
                        if (data != null) {
                            binding.model = data
                            newsData = data
                            model.getNewsByCategory(1, 4, data.primaryCategory.id)
                                .observe(this, Observer {
                                    if (it != null) {
                                        if (it.status == Resource.Status.LOADING) {

                                        } else if (it.status == Resource.Status.SUCCESS) {
                                            var data = Gson().fromJson(
                                                (it.data as String), CategoryNews::class.java
                                            )
                                            if (!data.data.isNullOrEmpty()) {

                                                var json = gson.toJson(data.data)
                                                val type: Type = object :
                                                    TypeToken<List<Featurednews?>?>() {}.type

                                                var datas: List<Featurednews> =
                                                    gson.fromJson(json, type)
                                                if (datas.isNullOrEmpty()) {
                                                    binding.rec.visibility = View.GONE
                                                } else {
                                                    binding.rec.visibility = View.VISIBLE
                                                    homeThirdSectionTabDataAdapter.provideData(datas)
                                                }
                                            }
                                        } else {
                                            binding.rec.visibility = View.GONE
                                        }
                                    }
                                })


                            id = data.id.toString()
                            authorId = data.author.get(0).id.toString()
                            if (AppController.loggedIn()) {
                                val user =
                                    Gson().fromJson(AppController.getUserData(), User::class.java)

                                model.isAuthorFollowed("Bearer ${user.accessToken}", authorId)
                                    .observe(this, Observer {
                                        if (it != null) {
                                            if (it.status == Resource.Status.SUCCESS) {
                                                var json = JSONObject(it.data.toString())
                                                var followed = json.getBoolean("success")
                                                if (followed) {
                                                    binding.btnAddList.setImageResource(R.drawable.unfollow_details)
                                                    binding.btnAddList.tag = true
                                                } else {
                                                    binding.btnAddList.tag = false
                                                    binding.btnAddList.setImageResource(R.drawable.follow_detail)
                                                }
                                            }

                                        }
                                    })



                                model.isBookMarked("Bearer ${user.accessToken}", id)
                                    .observe(this, Observer {
                                        if (it != null) {
                                            if (it.status == Resource.Status.SUCCESS) {
                                                var json = JSONObject(it.data.toString())
                                                var bookMark = json.getBoolean("is_bookmarked")

                                                if (bookMark) {
                                                    var note = json.getString("note")
                                                    var bookMarkId = json.getInt("bookmark_id")
                                                    binding.ivToolFav.setImageResource(R.drawable.save_ok)
                                                    binding.ivToolFav.tag = true
                                                    binding.tvNotes.text = note
                                                    binding.tvNotes.tag = bookMarkId.toString()
                                                    binding.layoutNotes.visibility = View.VISIBLE
                                                } else {
                                                    binding.ivToolFav.tag = false
                                                }
                                            }
                                        }
                                    })
                            } else {
                                val data = AppController.getBookmarks()
                                for (i in data.indices) {
                                    if (data[i].newsId.equals(id.toInt())) {
                                        binding.ivToolFav.setImageResource(R.drawable.save_ok)
                                        binding.ivToolFav.tag = true
                                        binding.tvNotes.text = data[i].note
                                        binding.tvNotes.tag = "0"
                                        binding.layoutNotes.visibility = View.VISIBLE
                                        break
                                    }
                                }
                            }
                            if (!data.youtubeKey.isNullOrEmpty()) {
                                youtubeKey = data.youtubeKey
                            }

                            if (data.author.get(0).avatar.isNullOrEmpty()) {

                            } else {
                                Helper.setImagePromatcally(
                                    binding.ivAuthor, data.author.get(0).avatar
                                )

                            }
                            binding.ivAuthor.tag = gson.toJson(data.author.get(0))

                            shareLink += data.id
                            val pish =
                                "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/opensans_regular.ttf\")}img { width:100%;height:auto;display:block; }body { font-family: MyFont;text-align: right;}</style></head><body>"/*      val pish =
                                        "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/opensans_regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"
                            */

                            val pish1 =
                                "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/opensans_regular.ttf\")}img { width:100%;height:auto;display:block; }body { background-color: #081e58; color: white;font-family: MyFont;text-align: right;}</style></head><body>"/*      val pish =
                                        "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/opensans_regular.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>"
                            */
                            val pas = "</body></html>"
                            val myHtmlString =
                                if (intent.hasExtra("video") && !intent.getBooleanExtra(
                                        "video", false
                                    )
                                ) {
                                    pish1 + data.content + pas
                                } else {
                                    pish + data.content + pas
                                }
                            binding.tvContents.loadDataWithBaseURL(
                                null, myHtmlString, "text/html", "UTF-8", null
                            )

                            if (!data.tag.isNullOrEmpty()) {
                                for (i in data.tag.indices) {
                                    var adapter= TabRowAdapter(this)
                                    binding.tabs.adapter=adapter
                                    adapter.provideData(ArrayList(data.tag!!))
                                }
                            }else{
                                binding.tabs.visibility=View.GONE
                            }


                            if (intent.hasExtra("video") && !intent.getBooleanExtra(
                                    "video", false
                                )
                            ) {
                                var list = ArrayList<ShowcaseModel>()
                                if (!data.mediaGallery.isNullOrEmpty()) {
                                    for (i in data.mediaGallery) {
                                        list.add(
                                            ShowcaseModel(
                                                1, i.url, i.title, "", ""
                                            )
                                        )
                                    }
                                    viewpagerAdapter.provideDataAndContext(list.subList(0, 1))
                                    imageGalleryAdapter.provideData(list)
                                    binding.pageIndicatorView.visibility = View.GONE
                                    binding.pageIndicatorView.count = list.size
                                    binding.pageIndicatorView.selection = 0
                                }

                                binding.tvAuthor.setText("وێنە: " + data.author.get(0).name)
                            }
                        }
                    } else if (it.status == Resource.Status.ERROR) {
                        Helper.hideProgressDialog()
                    }
                }
            })

        }


    }

    private fun textSmaller() {
        val settings: WebSettings = binding.tvContents.getSettings()
        settings.textZoom -= 10
    }

    private fun textBigger(progrss: Int) {
        val settings: WebSettings = binding.tvContents.getSettings()
        settings.textZoom = progrss
    }
}
