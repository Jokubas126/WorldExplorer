package com.example.worldexplorer.ui.worldlist

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.worldexplorer.R
import com.example.worldexplorer.model.data.Continent
import com.example.worldexplorer.model.data.CountryParcel
import com.example.worldexplorer.model.services.CountryApiService
import com.example.worldexplorer.util.CONTINENT_LIST
import com.example.worldexplorer.util.MAIN_LOCALE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class WorldListViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application

    private val _continent = MutableLiveData<MutableList<Continent>>()
    val continent: LiveData<MutableList<Continent>> = _continent

    private val continentList = mutableListOf<Continent>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()

    fun fetch() {
        _loading.value = true
        _error.value = false
        (CONTINENT_LIST).forEach { fetchContinent(it) }
        _loading.value = false
    }

    private fun fetchContinent(continentName: String) {
        disposable.add(
            countryApiService.getRegionCountryList(continentName.toLowerCase(MAIN_LOCALE)).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryParcel>>() {
                    override fun onSuccess(list: List<CountryParcel>) {
                        continentList.add(
                            Continent(
                                continentName,
                                getColor(continentName),
                                getDrawable(continentName),
                                list
                            )
                        )
                        continentList.sortBy { it.continentTitle }
                        _continent.value = continentList
                    }

                    override fun onError(e: Throwable) {
                        _loading.value = false
                        _error.value = true
                    }
                })
        )
    }

    private fun getDrawable(continentName: String): Bitmap {
        return BitmapFactory.decodeResource(
            app.resources,
            app.resources.getIdentifier(
                continentName.toLowerCase(MAIN_LOCALE),
                "drawable",
                app.packageName
            )
        )
    }

    private fun getColor(continentName: String): Int {
        return ContextCompat.getColor(
            app, app.resources.getIdentifier(
                continentName.toLowerCase(MAIN_LOCALE),
                "color",
                app.packageName
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}