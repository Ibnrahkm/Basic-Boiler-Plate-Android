package com.ibt.ava.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibt.ava.service.model.ErrorModel
import com.ibt.ava.service.repository.BasicRepository
import com.ibt.ava.util.Resource
import com.ibt.ava.util.Url
import jnr.ffi.annotations.In
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject


/*
* the view model of main activity
* Here the all business logic for main activity are done
* */
class MainViewModel @Inject constructor(val repository: BasicRepository) : ViewModel() {


    public fun getHomeNews(): MutableLiveData<Resource> {
        return repository.getHomeNews()
    }

    public fun getSearchNews(query: String,page: Int, limit: Int): MutableLiveData<Resource> {
        return repository.getSearchNews(query,page, limit)
    }

    public fun getMultimediaNews(multimedia: Boolean,page: Int, limit: Int): MutableLiveData<Resource> {
        return repository.getMultimediaNews(multimedia,page,limit)
    }

    public fun getNewsByCategory(page: Int, limit: Int, cat: Int): MutableLiveData<Resource> {
        return repository.getNewsByCategory(page, limit, cat)
    }

    public fun getLatestNews(page: Int, limit: Int, latest: Boolean): MutableLiveData<Resource> {
        return repository.getLatestNews(page, limit, latest)
    }

    public fun getScoopNews(
        page: Int,
        limit: Int,
        specialtoava: Boolean
    ): MutableLiveData<Resource> {
        return repository.getScoopNews(page, limit, specialtoava)
    }

    public fun getNewsCategories(): MutableLiveData<Resource> {
        return repository.getCategories()
    }

    public fun getAllNotifications(): MutableLiveData<Resource> {
        return repository.getAllNotifications()
    }

    public fun getCurrentWeather(ip: String): MutableLiveData<Resource> {
        return repository.getCurrentWeather(ip)
    }

    public fun getAllWeather(ip: String, dates: String): MutableLiveData<Resource> {
        return repository.getAllWeather(ip, dates)
    }

    fun getIp(): MutableLiveData<Resource> {
        return repository.getPublicIP()
    }

    public fun getNewsDetails(id: String): MutableLiveData<Resource> {
        return repository.getNewsDetails(id)
    }
    fun getAuthorNews(id: String,token:String,page: Int, limit: Int): MutableLiveData<Resource> {
        return repository.getAuthorNews(id,token,page,limit)
    }

    fun getTagNews(id: String,token:String,page: Int, limit: Int): MutableLiveData<Resource> {
        return repository.getTagNews(id,token,page,limit)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun login(email: String, passowrd: String): MutableLiveData<Resource> {
        return repository.login(email, passowrd)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun loginSocial(email: String, name: String): MutableLiveData<Resource> {
        return repository.loginSocial(email, name)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun signup(email: String, passowrd: String,name: String): MutableLiveData<Resource> {
        return repository.signup(email, passowrd,name)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun verifyOtp(otp: String, userId: String): MutableLiveData<Resource> {
        return repository.verifyEmail(otp,userId)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun profile(auth: String): MutableLiveData<Resource> {
        return repository.profile(auth)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun update(auth: String, id: String, name: String, bio: String): MutableLiveData<Resource> {
        return repository.update(auth, id, name, bio)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun logout(auth: String): MutableLiveData<Resource> {
        return repository.logout(auth)
    }
    /**
     * get transaction list by public address of wallet
     */
    fun deleteAccount(auth: String,userId:String): MutableLiveData<Resource> {
        return repository.deleteAccount(auth,userId)
    }
    fun forgotPassword(email: String): MutableLiveData<Resource> {
        return repository.forgotPassword(email)
    }

    fun resetPassword(userId: String,password: String,confirmPassword:String): MutableLiveData<Resource> {
        return repository.resetPassword(userId,password,confirmPassword)
    }

    fun verifyForgotPasswordOtp(userId: String,otp:String): MutableLiveData<Resource> {
        return  repository.verifyForgotPasswordOtp(userId,otp)
    }

    fun regenerateOtp(userId: String): MutableLiveData<Resource> {
        return  repository.regenerateOtp(userId)
    }
    /**
     * get transaction list by public address of wallet
     */
    fun updateBookmark(auth: String, id: String, notes: String): MutableLiveData<Resource> {
        return repository.updateBookmark(auth, id, notes)
    }

    fun isBookMarked(auth: String, id: String): MutableLiveData<Resource> {
        return repository.isBookmarked(auth, id)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun deleteBookmark(auth: String, id: String): MutableLiveData<Resource> {
        return repository.deleteBookmark(auth, id)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun deleteAllBookmark(auth: String, id: String): MutableLiveData<Resource> {
        return repository.deleteAllBookmark(auth, id)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getBookmakredList(auth: String, id: String): MutableLiveData<Resource> {
        return repository.getBookmakredList(auth, id)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun createBookmark(
        auth: String,
        newsId: String,
        userId: String,
        notes: String
    ): MutableLiveData<Resource> {
        return repository.createBookmark(auth, newsId, userId, notes)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun createFollow(
        auth: String,
        id: String
    ): MutableLiveData<Resource> {
        return repository.createFollow(auth,id)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getChannelsByPodcasts(
        id: String,page: Int, limit: Int
    ): MutableLiveData<Resource> {
        return repository.getChannelsByPodcasts(id,page,limit)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getAllPodcasts(page: Int, limit: Int
    ): MutableLiveData<Resource> {
        return repository.getAllPodcasts(page, limit)
    }

      /**
     * get transaction list by public address of wallet
     */
      fun getAllStories(): MutableLiveData<Resource> {
        return repository.getAllStories()
    }




    /**
     * get transaction list by public address of wallet
     */
    fun deleteFollow(
        auth: String,
        id: String
    ): MutableLiveData<Resource> {
        return repository.deleteFollow(auth,id)
    }


    /**
     * get transaction list by public address of wallet
     */
    fun followList(
        auth: String,id:String
    ): MutableLiveData<Resource> {
        return repository.getFollowList(auth,id)
    }

    /**
     * get transaction list by public address of wallet
     */
    fun isAuthorFollowed(
        auth: String,id:String
    ): MutableLiveData<Resource> {
        return repository.isAuthorFollowed(auth,id)
    }
}