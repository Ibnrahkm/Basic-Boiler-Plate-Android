package com.ibt.ava.dagger.module

import com.ibt.ava.service.APIInterface
import com.ibt.ava.util.Url
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * providing retrofit instance
 */
@Module
class RetrofitModule {
    /**
     * providing api interface
     */
    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }

    /**
     * providing retrofit client
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Url.BASE_IP_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

    }

    /**
     * providing http client
     */
    @Provides
    @Singleton
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().callTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES).addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request =
                    chain.request().newBuilder().addHeader("accept", "application/json").addHeader("Connection", "close")
                        .build()
                return chain.proceed(request)
            }

        }).addNetworkInterceptor(interceptor).build()
    }


    /**
     * providing logging interceptor
     */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}