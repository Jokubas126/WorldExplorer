package com.example.worldexplorer.model.data

import com.google.gson.annotations.SerializedName

class Country {
    @SerializedName("flag")
    lateinit var flagUrlPath: String

    lateinit var name: String
    lateinit var capital: String
    var population: Int = 0
    var area: Int = 0
    val languages: List<Language> = listOf()
    val currencies: List<Currency> = listOf()
    val regionalBlocs: List<RegionalBloc> = listOf()

    /*@SerializedName("borders")
    val borderCountries: MutableList<CountryParcel> = mutableListOf()
    @SerializedName("latlng")
    val globalPosition: MutableList<Double> = mutableListOf()*/
}