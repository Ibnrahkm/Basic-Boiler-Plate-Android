package com.ibt.ava.dagger.component

import com.ibt.ava.dagger.module.ActivityModule
import com.ibt.ava.dagger.module.ViewModelProviderModule
import com.ibt.ava.dagger.scope.ActivityScope
import com.ibt.ava.view.ui.activity.BaseActivity
import com.ibt.ava.view.ui.activity.SplashActivity
import com.ibt.ava.view.ui.fragment.BaseFragment
import dagger.Component

/**
 * providing main view component
 */
@ActivityScope
@Component(modules = arrayOf(ViewModelProviderModule::class), dependencies = arrayOf(AppComponent::class))
interface BaseActivityComponent {
    fun inject(baseActivity: BaseActivity)

}