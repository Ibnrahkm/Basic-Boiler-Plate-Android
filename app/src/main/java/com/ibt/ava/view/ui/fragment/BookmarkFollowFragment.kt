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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutBookmarkFollowFragmentBinding
import com.ibt.ava.databinding.LayoutMultimediaFragmentBinding
import com.ibt.ava.service.model.follow.FollowList
import com.ibt.ava.service.model.news.details.NewsDetails
import com.ibt.ava.service.model.news.multimedia.Data
import com.ibt.ava.service.model.news.multimedia.MultimediaNews
import com.ibt.ava.service.model.user.Bookmark
import com.ibt.ava.service.model.user.Bookmarks
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.adapter.AuthorListDataAdapter
import com.ibt.ava.view.adapter.BookmarkListDataAdapter
import com.ibt.ava.view.adapter.MultimediaImagesDataAdapter
import com.ibt.ava.view.adapter.MultimediaVideoTypeAdapter
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import org.json.JSONObject


class BookmarkFollowFragment : BaseFragment() {

    lateinit var binding: LayoutBookmarkFollowFragmentBinding
    lateinit var adapter: BookmarkListDataAdapter
    lateinit var adapterAuthor: AuthorListDataAdapter
    lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutBookmarkFollowFragmentBinding>(
            inflater, R.layout.layout_bookmark_follow_fragment, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = BookmarkListDataAdapter(requireActivity())
        adapterAuthor = AuthorListDataAdapter(requireActivity())
        binding.btnDelete.setOnClickListener {

            if (AppController.loggedIn()) {
                model.deleteAllBookmark("Bearer ${user.accessToken}", user.user__1.id.toString())
                    .observe(viewLifecycleOwner,
                        Observer {
                            if (it != null) {

                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
                                } else if (it.status == Resource.Status.SUCCESS) {
                                    Helper.hideProgressDialog()
                                    adapter.dataList.clear()
                                    adapter.notifyDataSetChanged()
                                } else if (it.status == Resource.Status.ERROR) {
                                    Helper.hideProgressDialog()
                                }
                            }

                        })
            } else {
                AppController.setBookmarks(ArrayList<Bookmark>())
                Helper.hideProgressDialog()
                adapter.dataList.clear()
                adapter.notifyDataSetChanged()
            }

        }

        adapterAuthor.clickedData.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                var m = it
                var res = MutableLiveData<Boolean>()
                Helper.showDecisionDialog(
                    "لابردن",
                    "ڕەتکردنەوە",
                    "ئاگاداری",
                    "دڵنیای کە دەتەوێت فۆڵۆوی ئەم نووسەرە بکەیت؟",
                    requireActivity(),
                    res
                )
                res.observe(viewLifecycleOwner, Observer {
                    if (it != null) {
                        if (it) {
                            model.deleteFollow("Bearer ${user.accessToken}", m.id.toString())
                                .observe(viewLifecycleOwner,
                                    Observer {
                                        if (it != null) {
                                            if (it.status == Resource.Status.LOADING) {
                                                Helper.showProgressDialog("", "", requireActivity())
                                            } else if (it.status == Resource.Status.SUCCESS) {
                                                Helper.hideProgressDialog()
                                                var p = adapterAuthor.dataList.indexOf(m)
                                                adapterAuthor.dataList.removeAt(p)
                                                adapterAuthor.notifyItemRemoved(p)
                                                adapterAuthor.notifyItemRangeChanged(
                                                    0,
                                                    adapterAuthor.dataList.size
                                                )
                                            } else if (it.status == Resource.Status.ERROR) {
                                                Helper.hideProgressDialog()
                                            }
                                        }
                                    })
                        }
                        res.value = null
                    }
                })

                adapterAuthor.clickedData.value = null
            }
        })


        adapterAuthor.rowClicked.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                var m = it
                var f = AuthorProfileFragment()
                var b = Bundle()
                b.putString("author", Gson().toJson(adapterAuthor.rowClicked.value))
                f.arguments = b
                f.show((context as HomeActivity).supportFragmentManager, null)
                adapterAuthor.clickedData.value = null
            }
        })

        adapter.clickedData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                var note: String = it.note
                var bId = it.id
                model.getNewsDetails(it.newsId.toString()).observe(this, Observer {
                    if (it != null) {
                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data = Gson().fromJson((it.data as String), NewsDetails::class.java)
                            if (data != null) {
                                if (!data.youtubeKey.isNullOrEmpty()) {
                                    var i =
                                        Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("video", true)
                                    i.putExtra("notes", note)
                                    i.putExtra("bid", bId.toString())
                                    startActivity(i)
                                } else if (!data.mediaGallery.isNullOrEmpty()) {
                                    var i =
                                        Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("video", false)
                                    i.putExtra("notes", note)
                                    i.putExtra("bid", bId.toString())
                                    startActivity(i)
                                } else if (data.primaryCategory.id == 12) {
                                    var i =
                                        Intent(context, SingleNewsSpecialDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("opinion", true)
                                    i.putExtra("notes", note)
                                    i.putExtra("bid", bId.toString())
                                    startActivity(i)
                                } else {
                                    var i = Intent(context, SingleNewsDetailActivity::class.java)
                                    i.putExtra("id", data.id.toString())
                                    i.putExtra("notes", note)
                                    i.putExtra("bid", bId.toString())
                                    startActivity(i)
                                }
                            }
                        } else if (it.status == Resource.Status.ERROR) {
                            Helper.hideProgressDialog()
                        }
                    }
                })


                adapter.clickedData.value = null
            }


        })
    }

    override fun onResume() {
        super.onResume()

        var bookmark = requireArguments().getBoolean("bookmark")
        if (AppController.loggedIn()) {
            user = Gson().fromJson(AppController.getUserData(), User::class.java)
        }

        if (bookmark) {
            binding.btnDelete.visibility = View.VISIBLE
            binding.rec.adapter = adapter
            if (AppController.loggedIn()) {
                model.getBookmakredList("Bearer ${user.accessToken}", user.user__1.id.toString())
                    .observe(viewLifecycleOwner, Observer {
                        if (it != null) {

                            if (it.status == Resource.Status.LOADING) {
                                Helper.showProgressDialog("", "", requireActivity())
                            } else if (it.status == Resource.Status.SUCCESS) {
                                Helper.hideProgressDialog()
                                var data =
                                    Gson().fromJson((it.data as String), Bookmarks::class.java)
                                if (data != null && !data.bookmarks.isNullOrEmpty()) {
                                    adapter.provideData(data.bookmarks)
                                } else {
                                    adapter.dataList.clear()
                                    adapter.notifyDataSetChanged()
                                }
                            } else if (it.status == Resource.Status.ERROR) {
                                Helper.hideProgressDialog()
                            }
                        }
                    })
            } else {
                adapter.provideData(AppController.getBookmarks())
            }

        } else {
            binding.btnDelete.visibility = View.GONE
            binding.rec.adapter = adapterAuthor
            model.followList("Bearer ${user.accessToken}", user.user__1.id.toString())
                .observe(viewLifecycleOwner, Observer {
                    if (it != null) {

                        if (it.status == Resource.Status.LOADING) {
                            Helper.showProgressDialog("", "", requireActivity())
                        } else if (it.status == Resource.Status.SUCCESS) {
                            Helper.hideProgressDialog()
                            var data = Gson().fromJson((it.data as String), FollowList::class.java)
                            if (data != null && !data.data.isNullOrEmpty()) {
                                adapterAuthor.provideData(data.data)
                            } else {
                                adapterAuthor.dataList.clear()
                                adapterAuthor.notifyDataSetChanged()
                            }
                        } else if (it.status == Resource.Status.ERROR) {
                            Helper.hideProgressDialog()
                        }
                    }
                })
        }


    }
}