package com.ibt.ava.dagger.module

import android.app.Activity
import com.ibt.ava.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Inject

/**
 * activity module for providing activity context
 */
@Module
class ActivityModule @Inject constructor(val context: Activity) {
    @Provides
    @ActivityScope
    fun provideActivityContext(): Activity {
        return context
    }

}