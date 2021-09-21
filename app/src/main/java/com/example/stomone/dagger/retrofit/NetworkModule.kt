package com.example.stomone.dagger.retrofit

import com.example.stomone.BuildConfig
import com.example.stomone.dagger.retrofit.repository.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private val baseURL = BuildConfig.baseURL

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceAuthorization(retrofit: Retrofit): RetrofitServiceInterfaceAuthorization {
        return retrofit.create(RetrofitServiceInterfaceAuthorization::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceContactInformation(retrofit: Retrofit): RetrofitServiceInterfaceContactInformation {
        return retrofit.create(RetrofitServiceInterfaceContactInformation::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceContracts(retrofit: Retrofit): RetrofitServiceInterfaceContracts {
        return retrofit.create(RetrofitServiceInterfaceContracts::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceVisitHistory(retrofit: Retrofit): RetrofitServiceInterfaceVisitHistory {
        return retrofit.create(RetrofitServiceInterfaceVisitHistory::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceXRays(retrofit: Retrofit): RetrofitServiceInterfaceXRays {
        return retrofit.create(RetrofitServiceInterfaceXRays::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfacePicturesVisit(retrofit: Retrofit): RetrofitServiceInterfacePicturesVisit {
        return retrofit.create(RetrofitServiceInterfacePicturesVisit::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceRadiationDose(retrofit: Retrofit): RetrofitServiceInterfaceRadiationDose {
        return retrofit.create(RetrofitServiceInterfaceRadiationDose::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceApplications(retrofit: Retrofit): RetrofitServiceInterfaceApplications {
        return retrofit.create(RetrofitServiceInterfaceApplications::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceSchedule(retrofit: Retrofit): RetrofitServiceInterfaceSchedule {
        return retrofit.create(RetrofitServiceInterfaceSchedule::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceNews(retrofit: Retrofit): RetrofitServiceInterfaceNews {
        return retrofit.create(RetrofitServiceInterfaceNews::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInterfaceNewsDescription(retrofit: Retrofit): RetrofitServiceInterfaceNewsDescription {
        return retrofit.create(RetrofitServiceInterfaceNewsDescription::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun getHttpClint(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                })
            .build()
    }

}