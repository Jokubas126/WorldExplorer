package com.example.worldexplorer.ui.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.model.services.CountryApiService
import com.example.worldexplorer.util.KEY_COUNTRY_CODE
import com.example.worldexplorer.util.MAIN_LOCALE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel : ViewModel() {

    private val _country = MutableLiveData<Country>()
    val country: LiveData<Country> = _country

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val countryApiService = CountryApiService()

    private val countryDisposable = CompositeDisposable()
    private val borderingCountriesDisposable = CompositeDisposable()

    fun fetch(arguments: Bundle?) {
        updateUi(null)
        fetchCountry(arguments)
    }

    private fun fetchCountry(arguments: Bundle?) {
        if (arguments != null) {
            countryDisposable.add(
                countryApiService
                    .getCountryByCode(
                        arguments.getString(KEY_COUNTRY_CODE)!!.toLowerCase(
                            MAIN_LOCALE
                        )
                    )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            fetchBorderingCountries(it)
                        },
                        { updateUi(it) }
                    )
            )
        }
    }

    private fun fetchBorderingCountries(country: Country) {
        if (!country.borderCountryCodes.isNullOrEmpty()) {
            for (i in country.borderCountryCodes.indices) {
                borderingCountriesDisposable.add(
                    countryApiService.getCountryByCode(
                        country.borderCountryCodes[i].toLowerCase(MAIN_LOCALE)
                    )
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                country.borderingCountries.add(it)
                                if (country.borderingCountries.size == country.borderCountryCodes.size)
                                    updateUi(country)
                            },
                            { updateUi(it) }
                        )
                )
            }
        } else updateUi(country)

    }

    private fun updateUi(s: Any?) {
        when (s) {
            is Country -> {
                _country.value = s
                _loading.value = false
                _error.value = false
            }
            is Throwable -> {
                _loading.value = false
                _error.value = true
            }
            else -> {
                _loading.value = true
                _error.value = false
            }
        }
    }

    fun onCountryClicked(view: View, countryCode: String) {
        val action: NavDirections = DetailsFragmentDirections.actionDetailsFragment(countryCode)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onCleared() {
        super.onCleared()
        countryDisposable.clear()
    }

}