package com.example.worldexplorer.ui.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.model.services.CountryApiService
import com.example.worldexplorer.util.KEY_COUNTRY_FULL_NAME
import com.example.worldexplorer.util.MAIN_LOCALE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel : ViewModel() {

    private val _country = MutableLiveData<Country>()
    val country: LiveData<Country> = _country

    private val _loading = MutableLiveData<Int>()
    val loading: LiveData<Int> = _loading

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int> = _error

    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()

    fun fetch(arguments: Bundle?){
        updateUi(null)
        if (arguments != null){
            disposable.add(
                countryApiService
                    .getCountry(arguments.getString(KEY_COUNTRY_FULL_NAME)!!.toLowerCase(MAIN_LOCALE))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {updateUi(it)},
                        {updateUi(it)}
                    )
            )
        }

    }

    private fun updateUi(s: Any?){
        when(s){
            is List<*> -> {
                _country.value = s[0] as Country?
                _loading.value = View.GONE
                _error.value = View.GONE
            }
            is Throwable -> {
                _loading.value = View.GONE
                _error.value = View.VISIBLE
            }
            else ->{
                _loading.value = View.VISIBLE
                _error.value = View.GONE
            }
        }
    }

}