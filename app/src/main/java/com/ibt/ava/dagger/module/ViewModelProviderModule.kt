package com.ibt.ava.dagger.module

import androidx.lifecycle.ViewModelProvider
import com.ibt.ava.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * providing view model
 */
@Module
abstract class ViewModelProviderModule {

    /**
     * binding base viewmodel factory
     */
    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory


    /**
     * providing main view model with repository
     */
    @Binds
    @IntoMap
    @ViewModelMappingKey(MainViewModel::class)
    abstract fun provideMainViewModel(viewModel: MainViewModel): MainViewModel



    /**
     * providing splash view model with repository
     */
    @Binds
    @IntoMap
    @ViewModelMappingKey(SplashViewModel::class)
    abstract fun provideSplashViewModel(viewModel: SplashViewModel): SplashViewModel


}