package com.ibt.ava.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/*
* Custom view model provider factory for customizing
* the constructor parameters
* */
@Singleton
class ViewModelProviderFactory @Inject constructor(val viewmodels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {


    /**
     * overriding the create method for creating and returning dynamic view model class
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator =
            viewmodels[modelClass] ?: viewmodels.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("model class not found!")
        return try {
            creator.get() as T
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }


}