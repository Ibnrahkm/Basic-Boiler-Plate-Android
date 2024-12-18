package com.ibt.ava.view.ui.fragment


import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.appbar.AppBarLayout
import com.ibt.ava.AppController
import com.ibt.ava.R
import com.ibt.ava.dagger.component.DaggerBaseFragmentComponent
import com.ibt.ava.dagger.module.ActivityModule
import com.ibt.ava.util.Helper
import com.ibt.ava.view.ui.activity.HomeActivity
import com.ibt.ava.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_home.appbar
import kotlinx.android.synthetic.main.activity_home.fragment_container
import kotlinx.android.synthetic.main.activity_home.navView
import javax.inject.Inject


open class BaseFragment : DialogFragment() {

    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var model: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentStyle)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        DaggerBaseFragmentComponent.builder().activityModule(ActivityModule(requireActivity()))
            .appComponent(AppController.get(requireActivity()).component).build().inject(this)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    /*    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

            dialog.setOnShowListener { dialogInterface: DialogInterface? ->
                val bottomSheet =
                    dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet!!.background = ColorDrawable(Color.TRANSPARENT)
                setupFullHeight(bottomSheet)
            }
            return dialog
        }

        public fun setupFullHeight(bottomSheet: View?) {
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            val layoutParams = bottomSheet!!.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams = layoutParams
           // bottomSheet.requestLayout()
        }*/


    fun handleAppbar(reset: Boolean) {
        if (!reset) {
            Helper.handleStatusBar(
                requireActivity().window!!, ContextCompat.getColor(
                    requireActivity(), android.R.color.black
                ), requireActivity()
            )
            (requireActivity() as HomeActivity).appbar.visibility = View.GONE
            var lp =
                (requireActivity() as HomeActivity).fragment_container.layoutParams as CoordinatorLayout.LayoutParams
            lp.behavior = null
            (requireActivity() as HomeActivity).fragment_container.requestLayout()
            (requireActivity() as HomeActivity).navView.visibility = View.GONE
        } else {
            Helper.handleStatusBar(
                requireActivity().window!!, ContextCompat.getColor(
                    requireActivity(), android.R.color.transparent
                ), requireActivity()
            )
        }

    }
}