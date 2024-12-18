package com.ibt.ava.view.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ibt.ava.R
import com.ibt.ava.databinding.LayoutAllStoryFragmentBinding
import com.ibt.ava.service.model.stories.Story
import com.ibt.ava.view.ui.activity.HomeActivity
import me.kungfucat.viewpagertransformers.AccordionTransformer
import me.kungfucat.viewpagertransformers.CubeOutTransformer
import java.lang.reflect.Type


class AllStoryFragment : BaseFragment() {

    lateinit var binding: LayoutAllStoryFragmentBinding
    var gson = Gson()
    var stories: List<Story> = ArrayList<Story>()

    companion object {
        var storyFinish = MutableLiveData<Boolean>()
        var cancelClicked = MutableLiveData<Boolean>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<LayoutAllStoryFragmentBinding>(
            inflater, R.layout.layout_all_story_fragment, null, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var position = requireArguments().getInt("position")
        val pagerAdapter = ViewPagerAdapter(childFragmentManager, stories)
        binding.viewpager.setAdapter(pagerAdapter)
        binding.viewpager.setCurrentItem(position, false)

        //        viewPager.setPageTransformer(true, new DefaultTransformer());
//        viewPager.setPageTransformer(true, new TranslationYTransformer(TranslationYTransformer.TOP_TO_BOTTOM));
        binding.viewpager.setPageTransformer(true, CubeOutTransformer());
//        viewPager.setPageTransformer(true, new RandomTransformer());
//        viewPager.setPageTransformer(true, new DepthPageTransformer());
//        viewPager.setPageTransformer(true, new RotateAboutTopTransformer());
//        binding.viewpager.setPageTransformer(true, AccordionTransformer())

        storyFinish.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                storyFinish.value = null
                if (binding.viewpager.currentItem == stories.size - 1) {
                    dismiss()
                } else {
                    binding.viewpager.setCurrentItem(binding.viewpager.currentItem + 1, true)
                }
            }
        })
        cancelClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cancelClicked.value = null
                dismiss()
            }
        })
    }

    fun setDataList(stories: ArrayList<Story>) {
        this.stories = stories
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    class ViewPagerAdapter(fm: FragmentManager?, var arrayList: List<Story>) :
        FragmentPagerAdapter(fm!!, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        var gson = Gson()
        override fun getItem(position: Int): Fragment {
            var fragment: StoryFragment? = null
            if (position < arrayList.size) {
                fragment = StoryFragment.newInstance(gson.toJson(arrayList[position]))
                Log.e("sadvsdvd", arrayList.size.toString())
            }
            return fragment!!
        }

        override fun getCount(): Int {
            return arrayList.size
        }
    }
}