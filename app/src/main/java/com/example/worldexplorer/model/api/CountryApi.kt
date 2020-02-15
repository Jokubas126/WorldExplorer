package com.example.worldexplorer.model.api

import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.model.data.CountryParcel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("rest/v2/name/{name}?fullText=true")
    fun getCountry(@Path("name") name: String): Single<Country>

    @GET("rest/v2/region/{region}")
    fun getRegionCountryList(@Path("region") region: String): Single<List<CountryParcel>>
}