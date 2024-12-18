package com.ibt.ava.dagger.component

import android.content.Context
import com.ibt.ava.AppController
import com.ibt.ava.dagger.module.ContextModule
import com.ibt.ava.dagger.module.RetrofitModule
import com.ibt.ava.service.APIInterface
import dagger.Component
import javax.inject.Singleton


/**
 * app component for providing api interface
 * context
 */
@Singleton
@Component(modules = arrayOf(RetrofitModule::class, ContextModule::class))
interface AppComponent {
    fun provideInterface(): APIInterface
    fun provideAppContext():Context
    fun inject(controller: AppController)

}