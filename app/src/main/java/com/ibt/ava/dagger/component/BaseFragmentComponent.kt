package com.ibt.ava.dagger.component

import com.ibt.ava.dagger.module.ActivityModule
import com.ibt.ava.dagger.module.ViewModelProviderModule
import com.ibt.ava.dagger.scope.ActivityScope
import com.ibt.ava.view.ui.fragment.BaseFragment
import dagger.Component

/**
 * providing main view component
 */
@ActivityScope
@Component(modules = arrayOf(ActivityModule::class,ViewModelProviderModule::class), dependencies = arrayOf(AppComponent::class))
interface BaseFragmentComponent {
    fun inject(baseFragment: BaseFragment)

}