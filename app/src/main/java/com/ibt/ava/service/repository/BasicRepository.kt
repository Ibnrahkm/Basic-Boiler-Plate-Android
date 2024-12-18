package com.ibt.ava.service.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ibt.ava.service.APIInterface
import com.ibt.ava.service.model.*
import com.ibt.ava.util.Url
import com.ibt.ava.util.Resource
import com.ibt.ava.view.ui.fragment.ForgotPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import java.lang.Exception
import javax.inject.Inject

/**
 * Repository class
 */
class BasicRepository @Inject constructor(val apiInterface: APIInterface) {


    /**
     * get transaction list by public address of wallet
     */
    fun login(email: String, passowrd: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.login(Url.LOGIN, Url.API_KEY, email, passowrd)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun loginSocial(email: String, name: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.loginSocial(Url.LOGIN_SOCIAL, Url.API_KEY, email, name)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun signup(email: String, passowrd: String,name: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.signup(Url.SIGNUP, Url.API_KEY, email,passowrd,name)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun verifyEmail(otp: String, userId: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.verifyEmail(Url.VerifyOtp, Url.API_KEY,otp,userId)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun profile(auth: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.profile(Url.PROFILE, Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun update(auth: String, id: String, name: String, bio: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.update(String.format(Url.UPDATE, id), Url.API_KEY, auth, name, bio)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun updateBookmark(auth: String, id: String, notes: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.bookmarkUpdate(
            String.format(Url.BOOKMARK_UPDATE_OR_DELETE, id),
            Url.API_KEY,
            auth,
            notes
        )
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun deleteBookmark(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.deleteNewBookmark(
            String.format(Url.BOOKMARK_DELETE, id),
            Url.API_KEY,
            auth
        )
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }




    /**
     * get transaction list by public address of wallet
     */
    fun deleteAllBookmark(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.deleteNewBookmark(
            String.format(Url.BOOKMARKE_DELETE_ALL, id),
            Url.API_KEY,
            auth
        )
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun deleteFollow(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.deleteFollow(
            String.format(Url.AUTHOR_UNFOLLOW, id),
            Url.API_KEY,
            auth
        )
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }





    /**
     * get transaction list by public address of wallet
     */
    fun getBookmakredList(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.bookmarkList(String.format(Url.BOOKMARKS_BY_USER, id), Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getFollowList(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.followList(String.format(Url.AUTHOR_FOLLOW_LIST, id), Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun isAuthorFollowed(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.isAuthorFollowed(String.format(Url.AUTHOR_FOLLOWED, id), Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun isBookmarked(auth: String, id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.newsBookmarked(String.format(Url.BOOKMARKED, id), Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
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
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.bookmark(Url.BOOKMARK, Url.API_KEY, auth, userId, newsId, notes)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }



    /**
     * get transaction list by public address of wallet
     */
    fun createFollow(
        auth: String,
        id:String
    ): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.addFollow(String.format(Url.AUTHOR_FOLLOW,id), Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }



    /**
     * get transaction list by public address of wallet
     */
    fun getChannelsByPodcasts(
        id:String,page: Int, limit: Int
    ): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getChannelsByPodcasts(String.format(Url.CHANNELS_BY_PODCAST,id),page,limit, Url.API_KEY)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }



    /**
     * get transaction list by public address of wallet
     */
    fun getAllPodcasts(page: Int, limit: Int
    ): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getAllPodcasts(Url.ALL_PODCASTS, Url.API_KEY,page,limit)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }



    /**
     * get transaction list by public address of wallet
     */
    fun getAllStories(): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getStories(Url.STORIES, Url.API_KEY)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }




    /**
     * get transaction list by public address of wallet
     */
    fun getAllNotifications(): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getAllNotification(Url.NOTIFICATIONS, Url.API_KEY)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }



    /**
     * get transaction list by public address of wallet
     */
    fun logout(auth: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.logout(Url.LOGOUT, Url.API_KEY, auth)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun deleteAccount(auth: String,userId: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.deleteAccount(Url.DELETE_ACCOUNT, Url.API_KEY, auth,userId)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun forgotPassword(email: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.forgotPassword(Url.FORGOT_PASSWORD, Url.API_KEY,email)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun resetPassword(userId: String,password: String,confirmPassword:String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.resetPassword(Url.RESET_PASSWORD, Url.API_KEY,userId,password,confirmPassword)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun regenerateOtp(userId: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.resendOtp(Url.REGENERATE_OTP, Url.API_KEY,userId)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun verifyForgotPasswordOtp(userId: String,otp:String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.verifyForgotPasswordOtp(Url.VERIFY_FORGOT_PASSWORD_OTP, Url.API_KEY,userId,otp)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getHomeNews(): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getFeaturedNews(Url.HOME_NEWS_ENDPOINT, Url.API_KEY)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getSearchNews(query: String,page: Int, limit: Int): MutableLiveData<Resource> {
        Log.e("asdcvsdvsd",page.toString())
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getSearchNews(Url.SEARCH, Url.API_KEY, query,page, limit)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getCategories(): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getNewsCategories(Url.NEWS_CATEGORIES_API, Url.API_KEY)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getMultimediaNews(multimedia: Boolean,page: Int, limit: Int): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        var api = if (multimedia) {
            Url.MULTIMEDIA_VIDEO
        } else {
            Url.MULTIMEDIA_IMAGES
        }
        apiInterface.getMultimediaNews(api, Url.API_KEY, multimedia,page, limit)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getNewsByCategory(page: Int, limit: Int, cat: Int): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getNewsByCategory(Url.NEWS_API, Url.API_KEY, page, limit, cat)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getLatestNews(page: Int, limit: Int, latest: Boolean): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getLatestNews(Url.NEWS_API, Url.API_KEY, page, limit, latest)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getScoopNews(page: Int, limit: Int, specialtoava: Boolean): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getScoopNews(Url.NEWS_API, Url.API_KEY, page, limit, specialtoava)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }

    /**
     * get transaction list by public address of wallet
     */
    fun getNewsDetails(id: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getNewsDetails(String.format(Url.NEWS_DETAILS_ENDPOINT, id), Url.API_KEY)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getAuthorNews(id: String, token:String,page: Int, limit: Int): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getNewsByAuthor(Url.NEWS_API,Url.API_KEY,token,page,limit,id)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }



    /**
     * get transaction list by public address of wallet
     */
    fun getTagNews(id: String,token:String,page: Int, limit: Int): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getNewsByTag(Url.NEWS_API,Url.API_KEY,token,id,page,limit)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }




    /**
     * get transaction list by public address of wallet
     */
    fun getPublicIP(): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getIP(Url.IP_URL)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getCurrentWeather(ip: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getCurrentWeather(Url.HOME_CURRENT_WEATHER_ENDPOINT, Url.API_KEY, ip)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }


    /**
     * get transaction list by public address of wallet
     */
    fun getAllWeather(ip: String, dates: String): MutableLiveData<Resource> {
        val status: MutableLiveData<Resource> = MutableLiveData()
        status.value = Resource.loading(null)
        apiInterface.getAllWeather(Url.HOME_All_WEATHER_ENDPOINT, Url.API_KEY, ip, dates)
            .enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    status.postValue(
                        Resource.error<ErrorModel>(
                            ErrorModel(
                                500,
                                t.message!!.toString()
                            )
                        )
                    )

                }

                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        try {
                            status.postValue(Resource.success(response.body().toString()))
                        } catch (ex: Exception) {
                            Log.e("sdvsdvsdvsd", ex.message.toString())
                            ex.printStackTrace()
                            status.postValue(
                                Resource.error<ErrorModel>(
                                    ErrorModel(
                                        500,
                                        "Something went wrong!"
                                    )
                                )
                            )
                        }


                    } else {
                        status.postValue(
                            Resource.error<ErrorModel>(
                                ErrorModel(
                                    500,
                                    "Something went wrong!"
                                )
                            )
                        )
                    }
                }

            })

        return status
    }
}