package com.ibt.ava.dagger.component

import com.ibt.ava.dagger.module.ViewModelProviderModule
import com.ibt.ava.dagger.scope.ActivityScope
import com.ibt.ava.view.ui.activity.SplashActivity
import dagger.Component

/**
 * providing splash component
 */
@ActivityScope
@Component(modules = arrayOf(ViewModelProviderModule::class), dependencies = arrayOf(AppComponent::class))
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)

}