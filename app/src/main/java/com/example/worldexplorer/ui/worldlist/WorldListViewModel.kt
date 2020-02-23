package com.example.worldexplorer.ui.worldlist

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.worldexplorer.model.data.Continent
import com.example.worldexplorer.model.data.CountryParcel
import com.example.worldexplorer.model.services.CountryApiService
import com.example.worldexplorer.util.CONTINENT_LIST
import com.example.worldexplorer.util.MAIN_LOCALE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WorldListViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application

    var state: Parcelable? = null

    private val _continents = MutableLiveData<MutableList<Continent>>()
    val continents: LiveData<MutableList<Continent>> = _continents

    private val continentList = mutableListOf<Continent>()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()

    fun fetch() {
        if (continents.value.isNullOrEmpty()) {
            updateUi(null, null)
            (CONTINENT_LIST).forEach { fetchContinent(it) }
        }
    }

    private fun fetchContinent(continentName: String) {
        disposable.add(
            countryApiService.getRegionCountryList(continentName.toLowerCase(MAIN_LOCALE))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list -> updateUi(continentName, list) },
                    { e -> updateUi(continentName, e) }
                )
        )
    }

    private fun updateUi(continentName: String?, s: Any?) {
        when (s) {
            is List<*> -> {
                @Suppress("UNCHECKED_CAST")
                continentList.add(
                    Continent(
                        continentName,
                        getColor(continentName),
                        getDrawable(continentName),
                        s as List<CountryParcel>?
                    )
                )
                continentList.sortBy { it.continentTitle }
                _continents.value = continentList
                if (continents.value?.size == CONTINENT_LIST.size) {
                    _loading.value = false
                    _error.value = false
                }
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

    private fun getDrawable(continentName: String?): Bitmap {
        return BitmapFactory.decodeResource(
            app.resources,
            app.resources.getIdentifier(
                continentName?.toLowerCase(MAIN_LOCALE),
                "drawable",
                app.packageName
            )
        )
    }

    private fun getColor(continentName: String?): Int {
        return ContextCompat.getColor(
            app, app.resources.getIdentifier(
                continentName?.toLowerCase(MAIN_LOCALE),
                "color",
                app.packageName
            )
        )
    }

    fun onItemClicked(view: View, countryCode: String) {
        val action: NavDirections = WorldListFragmentDirections.actionDetailsFragment(countryCode)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}