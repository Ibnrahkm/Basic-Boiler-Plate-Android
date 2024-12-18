package com.ibt.ava.view.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.dagger.component.DaggerBaseFragmentComponent
import com.ibt.ava.dagger.component.DaggerStoryFragmentComponent
import com.ibt.ava.dagger.module.ActivityModule
import com.ibt.ava.databinding.LayoutStoryFragmentBinding
import com.ibt.ava.service.model.news.details.NewsDetails
import com.ibt.ava.service.model.stories.Item
import com.ibt.ava.service.model.stories.Story
import com.ibt.ava.util.Helper
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.activity.SingleNewsDetailActivity
import com.ibt.ava.view.ui.activity.SingleNewsSpecialDetailActivity
import com.ibt.ava.viewmodel.MainViewModel
import jp.shts.android.storiesprogressview.StoriesProgressView
import java.lang.reflect.Type
import javax.inject.Inject


class StoryFragment : Fragment(), StoriesProgressView.StoriesListener {

    lateinit var binding: LayoutStoryFragmentBinding
    var gson = Gson()
    var counter = 0
    var stories: List<Item> = ArrayList<Item>()
    var mainTitle = ""
    var mainImage = ""
    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var model: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutStoryFragmentBinding>(
            inflater, R.layout.layout_story_fragment, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(data: String): StoryFragment {
            val args = Bundle()
            args.putString("data", data)
            val fragment = StoryFragment()
            fragment.setArguments(args)
            return fragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       /* setStyle(STYLE_NORMAL, R.style.TransparentStyle)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)*/
        DaggerStoryFragmentComponent.builder().activityModule(ActivityModule(requireActivity()))
            .appComponent(AppController.get(requireActivity()).component).build().inject(this)
        var data = requireArguments().getString("data")
        var story = gson.fromJson(data, Story::class.java)
        mainImage = story.thumbnail.toString()
        mainTitle = story.title
        stories = story.items

        binding.stories.setStoriesCount(stories.size)
        binding.stories.setStoryDuration(6000L)
        binding.stories.setStoriesListener(this)
        binding.stories.startStories(counter)

        binding.tvFirstAuthorName.text = mainTitle
        Glide.with(requireActivity()).load(mainImage).placeholder(R.drawable.ava_link)
            .into(binding.ivAuthor)
        binding.reverse.setOnClickListener { // inside on click we are
            binding.stories.reverse()
        }

        binding.btnCancel.setOnClickListener {
           AllStoryFragment.cancelClicked.value=true
        }

        // binding.reverse.setOnTouchListener(onTouchListener)

        binding.skip.setOnClickListener { // inside on click we are
            binding.stories.skip()
        }

        // binding.skip.setOnTouchListener(onTouchListener)
        binding.tvFirstTitle.text = stories[counter].title
        binding.tvFirstTitle.tag = stories[counter].link
        binding.tvFirstTime.text = stories[counter].createdAt
        Glide.with(requireActivity()).load(stories[counter].imageUrl).into(binding.image)


        binding.abcd.setOnClickListener {

            var s = binding.tvFirstTitle.tag.toString().split("/")
            var id = s[s.size - 1]
            model.getNewsDetails(id).observe(this, Observer {
                if (it != null) {
                    if (it.status == Resource.Status.LOADING) {
                        Helper.showProgressDialog("", "", requireActivity())
                    } else if (it.status == Resource.Status.SUCCESS) {
                        Helper.hideProgressDialog()
                        var data = Gson().fromJson((it.data as String), NewsDetails::class.java)
                        if (data != null) {
                            if (!data.mediaGallery.isNullOrEmpty()) {
                                var i = Intent(
                                    requireActivity(),
                                    SingleNewsSpecialDetailActivity::class.java
                                )
                                i.putExtra("id", data.id.toString())
                                i.putExtra("video", false)
                                this.startActivity(i)
                            } else if (!data.youtubeKey.isNullOrEmpty()) {
                                var i = Intent(
                                    requireActivity(),
                                    SingleNewsSpecialDetailActivity::class.java
                                )
                                i.putExtra("id", data.id.toString())
                                i.putExtra("video", true)
                                this.startActivity(i)
                            } else if (data.primaryCategory.id == 12) {
                                var i = Intent(
                                    requireActivity(),
                                    SingleNewsSpecialDetailActivity::class.java
                                )
                                i.putExtra("id", data.id.toString())
                                i.putExtra("opinion", true)
                                this.startActivity(i)
                            } else if (data.isSpecialToAva) {
                                var i =
                                    Intent(requireActivity(), SingleNewsDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                this.startActivity(i)
                            } else {
                                var i =
                                    Intent(requireActivity(), SingleNewsDetailActivity::class.java)
                                i.putExtra("id", data.id.toString())
                                this.startActivity(i)
                            }
                        }
                    } else if (it.status == Resource.Status.ERROR) {
                        Helper.hideProgressDialog()
                    }
                }
            })

        }

    }

    override fun onNext() {
        ++counter
        binding.tvFirstTitle.text = stories[counter].title
        binding.tvFirstTitle.tag = stories[counter].link
        binding.tvFirstTime.text = stories[counter].createdAt
        Glide.with(requireActivity()).load(stories[counter].imageUrl).into(binding.image)
    }

    override fun onPrev() {
        if (counter > 0) {
            --counter
            binding.tvFirstTitle.text = stories[counter].title
            binding.tvFirstTitle.tag = stories[counter].link
            binding.tvFirstTime.text = stories[counter].createdAt
            Glide.with(requireActivity()).load(stories[counter].imageUrl).into(binding.image)
        }
    }

    override fun onComplete() {
        AllStoryFragment.storyFinish.value=true
    }

    override fun onDestroy() {
        binding.stories.destroy()
        super.onDestroy()
    }
}