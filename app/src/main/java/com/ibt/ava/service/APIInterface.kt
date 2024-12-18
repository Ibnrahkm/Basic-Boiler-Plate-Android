package com.ibt.ava.service

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {


    @GET
    fun getFeaturedNews(
        @Url url: String, @Header("x-api-key") apiKey: String
    ): Call<String>

    @GET
    fun getMultimediaNews(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Query("multimedia") multimedia: Boolean,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Call<String>

    @GET
    fun getSearchNews(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Query("s") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<String>

    @GET
    fun getNewsByCategory(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("cat") cat: Int,
    ): Call<String>

    @GET
    fun getLatestNews(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("latest") latest: Boolean,
    ): Call<String>

    @GET
    fun getScoopNews(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("specialtoava") specialtoava: Boolean,
    ): Call<String>

    @GET
    fun getNewsCategories(
        @Url url: String, @Header("x-api-key") apiKey: String
    ): Call<String>

    @GET
    fun getNewsDetails(
        @Url url: String, @Header("x-api-key") apiKey: String
    ): Call<String>

    @GET
    fun getCurrentWeather(
        @Url url: String, @Header("x-api-key") apiKey: String, @Query("ipaddress") ip: String
    ): Call<String>

    @GET
    fun getIP(
        @Url url: String
    ): Call<String>


    @GET
    fun getAllWeather(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Query("ipaddress") ip: String,
        @Query("days") days: String,
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun login(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun loginSocial(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("email") email: String,
        @Field("name") name: String,
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun signup(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun verifyEmail(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("otp") otp: String,
        @Field("user_id") userId: String,
    ): Call<String>

    @POST
    fun logout(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String
    ): Call<String>

    @POST
    @FormUrlEncoded
    fun deleteAccount(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
        @Field("user_id") userId: String
    ): Call<String>

    @POST
    @FormUrlEncoded
    fun forgotPassword(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("email") email: String,
    ): Call<String>


    @POST
    @FormUrlEncoded
    fun resetPassword(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("user_id") userId: String,
        @Field("password_confirmation") passwordConfirmation: String,
        @Field("password") password: String
    ): Call<String>

    @POST
    @FormUrlEncoded
    fun verifyForgotPasswordOtp(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("user_id") userId: String,
        @Field("otp") otp: String,
    ): Call<String>


    @POST
    @FormUrlEncoded
    fun resendOtp(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Field("user_id") userId: String,
    ): Call<String>

    @POST
    fun profile(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun update(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
        @Field("name") name: String,
        @Field("bio") bio: String
    ): Call<String>

    @FormUrlEncoded
    @POST
    fun bookmark(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
        @Field("user_id") userId: String,
        @Field("news_id") newsId: String,
        @Field("note") note: String
    ): Call<String>

    @GET
    fun bookmarkList(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String
    ): Call<String>

    @Headers("Content-Type: application/json")
    @PUT
    fun bookmarkUpdate(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
        @Body data: String
    ): Call<String>

    @GET
    fun newsBookmarked(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
    ): Call<String>

    @DELETE
    fun deleteNewBookmark(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
    ): Call<String>

    @DELETE
    fun deleteAllBookmark(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
    ): Call<String>

    @GET
    fun followList(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String
    ): Call<String>


    @GET
    fun isAuthorFollowed(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String
    ): Call<String>

    @GET
    fun getNewsByAuthor(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("author_id") authorId: String
    ): Call<String>

    @GET
    fun getNewsByTag(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
        @Query("tag") tag: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<String>

    @DELETE
    fun deleteFollow(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
    ): Call<String>


    @POST
    fun addFollow(
        @Url url: String,
        @Header("x-api-key") apiKey: String,
        @Header("Authorization") auth: String,
    ): Call<String>


    @GET
    fun getStories(
        @Url url: String,
        @Header("x-api-key") apiKey: String
    ): Call<String>


    @GET
    fun getChannelsByPodcasts(
        @Url url: String, @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Header("x-api-key") apiKey: String
    ): Call<String>


    @GET
    fun getAllPodcasts(
        @Url url: String,
        @Header("x-api-key") apiKey: String, @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Call<String>

    @GET
    fun getAllNotification(
        @Url url: String,
        @Header("x-api-key") apiKey: String
    ): Call<String>
}