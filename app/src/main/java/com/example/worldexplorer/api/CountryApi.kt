package com.example.worldexplorer.api

import com.example.worldexplorer.model.data.Country
import io.reactivex.Single

interface CountryApi {
    fun getCountries(): Single<List<Country>>
}