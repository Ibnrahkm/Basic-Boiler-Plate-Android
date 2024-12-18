package com.ibt.ava.view.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutAuthorProfileFragmentBinding
import com.ibt.ava.service.model.news.categorynews.Author
import com.ibt.ava.service.model.news.home.Featurednews
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.adapter.AuthorNewsDataAdapter
import com.ibt.ava.view.ui.activity.AuthenticationActivity
import org.json.JSONObject
import java.lang.reflect.Type


class AuthorProfileFragment : BaseFragment() {

    lateinit var binding: LayoutAuthorProfileFragmentBinding
    var gson = Gson()
    lateinit var authorNewsDataAdapter: AuthorNewsDataAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutAuthorProfileFragmentBinding>(
            inflater, R.layout.layout_author_profile_fragment, null, false
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
        var authorData = requireArguments().getString("author")
        var author = gson.fromJson(authorData, Author::class.java)
        binding.model = author
        binding.btnAddList.tag = false

        authorNewsDataAdapter = AuthorNewsDataAdapter(requireActivity())

        binding.rec.adapter = authorNewsDataAdapter
        if (AppController.loggedIn()) {
            val user =
                Gson().fromJson(AppController.getUserData(), User::class.java)

            model.isAuthorFollowed("Bearer ${user.accessToken}", author.id.toString()).observe(this,
                Observer {
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
        } else {
            binding.btnAddList.tag = false
            binding.btnAddList.setImageResource(R.drawable.follow_detail)
        }

        binding.btnAddList.setOnClickListener {
            if (AppController.loggedIn()) {
                val user = Gson().fromJson(AppController.getUserData(), User::class.java)
                var status = !(binding.btnAddList.tag as Boolean)
                if (status) {
                    model.createFollow("Bearer ${user.accessToken}", author.id.toString())
                        .observe(this, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
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
                    model.deleteFollow("Bearer ${user.accessToken}", author.id.toString())
                        .observe(this, Observer {
                            if (it != null) {
                                if (it.status == Resource.Status.LOADING) {
                                    Helper.showProgressDialog("", "", requireActivity())
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
                startActivity(Intent(requireActivity(), AuthenticationActivity::class.java))
            }
        }


        authorNewsDataAdapter.loadMore.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                authorNewsDataAdapter.loadMore.value=null

                model.getAuthorNews(author.id.toString(), "",it,10).observe(this, Observer {
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

        model.getAuthorNews(author.id.toString(), "",1,10).observe(this, Observer {
            if (it != null) {
                if (it.status == Resource.Status.LOADING) {
                    Helper.showProgressDialog("", "", requireActivity())
                } else if (it.status == Resource.Status.SUCCESS) {
                    Helper.hideProgressDialog()
                    var json = JSONObject(it.data.toString())
                    var total=json.getJSONObject("meta").getInt("total")
                    binding.tvCategory.text = "$total هەواڵی گشتی"
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