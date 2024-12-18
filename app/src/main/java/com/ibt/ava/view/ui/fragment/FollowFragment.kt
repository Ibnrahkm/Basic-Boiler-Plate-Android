package com.ibt.ava.view.ui.fragment


import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutFollowBinding
import com.ibt.ava.service.model.user.Bookmark
import com.ibt.ava.service.model.user.User
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import org.json.JSONObject


class FollowFragment : BaseFragment() {

    lateinit var binding: LayoutFollowBinding
    var gson = Gson()

    companion object {
        var bookmarked = MutableLiveData<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutFollowBinding>(
            inflater, R.layout.layout_follow, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        var user = gson.fromJson(AppController.getUserData(), User::class.java)


        if (requireArguments().containsKey("notes")) {
            binding.etFollowText.setText(requireArguments().getString("notes"))
        }

        binding.btnSubmit.setOnClickListener {

            var note = binding.etFollowText.text.toString().trim()
         //   if (!note.isNullOrEmpty()) {
                if (requireArguments().containsKey("notes")){
                    if (AppController.loggedIn()){
                        var json=JSONObject()
                        json.put("note",note)
                        model.updateBookmark("Bearer ${user.accessToken}",requireArguments().getString("bid", ""),json.toString())
                            .observe(viewLifecycleOwner, Observer {

                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                        Helper.showProgressDialog("", "", requireActivity())
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        dismiss()
                                        bookmarked.value = note
                                    } else {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })
                    }else{
                        var datas=AppController.getBookmarks()
                        for (i in datas){
                            if (i.newsId.equals(requireArguments().getString("id", ""))){
                                i.note=note
                                break
                            }
                        }
                        AppController.setBookmarks(datas)
                        Helper.hideProgressDialog()
                        dismiss()
                        bookmarked.value = note
                    }

                }else{
                    if (AppController.loggedIn()){
                        model.createBookmark(
                            "Bearer ${user.accessToken}",
                            requireArguments().getString("id", ""),
                            user.user__1.id.toString(),
                            note
                        )
                            .observe(viewLifecycleOwner, Observer {

                                if (it != null) {
                                    if (it.status == Resource.Status.LOADING) {
                                        Helper.showProgressDialog("", "", requireActivity())
                                    } else if (it.status == Resource.Status.SUCCESS) {
                                        Helper.hideProgressDialog()
                                        dismiss()
                                        bookmarked.value = note
                                    } else {
                                        Helper.hideProgressDialog()
                                    }
                                }
                            })
                    }else{
                        var datas=AppController.getBookmarks()
                        var bookmark=Bookmark()
                        bookmark.newsId=requireArguments().getString("id", "").toInt()
                        bookmark.newstitle=requireArguments().getString("title")
                        bookmark.newsFeaturedImageUrl=requireArguments().getString("image")
                        bookmark.categoryName=requireArguments().getString("category")
                        bookmark.note=note
                        datas.add(bookmark)
                        AppController.setBookmarks(datas)
                        Helper.hideProgressDialog()
                        dismiss()
                        bookmarked.value = note
                    }

                }
          //  }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}