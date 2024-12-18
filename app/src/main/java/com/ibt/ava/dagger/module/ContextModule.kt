package com.ibt.ava.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * providing app context
 */
@Module
class ContextModule @Inject constructor(val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context {
        return context
    }

}