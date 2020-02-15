package com.example.worldexplorer.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldexplorer.model.data.CountryParcel

class DetailsViewModel : ViewModel() {

    private val _country = MutableLiveData<CountryParcel>()
    val country: LiveData<CountryParcel> = _country

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

}