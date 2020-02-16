package com.example.worldexplorer.model.services

import com.example.worldexplorer.model.api.CountryApi
import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.model.data.CountryParcel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryApiService {

    private val baseUrl: String = "https://restcountries.eu/"

    private val api: CountryApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CountryApi::class.java)

    fun getRegionCountryList(regionName: String): Single<List<CountryParcel>> {
        return api.getRegionCountryList(regionName)
    }

    fun getCountryByFullName(name: String): Single<List<Country>>{
        return api.getCountryByFullName(name)
    }

    fun getCountryByCode(code: String): Single<Country>{
        return api.getCountryByCode(code)
    }
}