package com.example.worldexplorer.model.data

class Country {
    lateinit var flagUrlPath: String
    lateinit var name: String
    lateinit var capital: String
    var population: Int = 0
    var areaSize: Int = 0
    lateinit var languages: String
    lateinit var currencies: String
    lateinit var regionalBlocks: String

    val borderCountries: MutableList<CountryParcel> = mutableListOf()
    val globalPosition: MutableList<Int> = mutableListOf()
}