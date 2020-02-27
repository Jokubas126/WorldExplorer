package com.example.worldexplorer.ui.searchlist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.model.services.CountryApiService
import com.example.worldexplorer.ui.worldlist.WorldListFragmentDirections
import com.example.worldexplorer.util.KEY_SEARCH
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchListViewModel : ViewModel() {

    private val _countryList: MutableLiveData<List<Country>> = MutableLiveData()
    val countryList: LiveData<List<Country>> = _countryList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()

    lateinit var filterQuery: String

    fun fetch(arguments: Bundle?) {
        if (arguments != null){
            updateUi(null)
            filterQuery = arguments.getString(KEY_SEARCH)!!
            fetchCountries()
        }
    }

    private fun fetchCountries() {
        disposable.add(
            countryApiService.getAllCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { updateUi(it)},
                    { updateUi(it)}
                )
        )
    }

    private fun updateUi(s: Any?) {
        when (s) {
            is List<*> -> {
                _countryList.value =  s.filterIsInstance<Country>()
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

    fun onCountryClicked(view: View, countryCode: String){
        val action: NavDirections = SearchListFragmentDirections.actionDetailsFragment(countryCode)
        Navigation.findNavController(view).navigate(action)
    }
}