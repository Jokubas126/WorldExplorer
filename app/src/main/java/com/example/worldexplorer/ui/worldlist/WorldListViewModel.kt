package com.example.worldexplorer.ui.worldlist

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.worldexplorer.R
import com.example.worldexplorer.model.data.Continent
import com.example.worldexplorer.model.data.Country

class WorldListViewModel : ViewModel() {

    private val _continent = MutableLiveData<List<Continent>>()
    val continent: LiveData<List<Continent>> = _continent

    fun fetch(activity: Activity){
        _continent.value = prepareContinentList(activity)

    }

    @Suppress("DEPRECATION")
    private fun prepareContinentList(activity: Activity): List<Continent>? {
        val list : MutableList<Continent> = mutableListOf()
        val europeList: MutableList<Country> = mutableListOf()
        val africaList: MutableList<Country> = mutableListOf()
        val americaList: MutableList<Country> = mutableListOf()
        val asiaList: MutableList<Country> = mutableListOf()
        val oceaniaList: MutableList<Country> = mutableListOf()
        list.add(Continent("Europe", activity.resources.getColor(R.color.colorEurope) , BitmapFactory.decodeResource(activity.resources, R.drawable.europe), europeList))
        list.add(Continent("Africa", activity.resources.getColor(R.color.colorAfrica), BitmapFactory.decodeResource(activity.resources, R.drawable.africa), africaList))
        list.add(Continent("America", activity.resources.getColor(R.color.colorAmerica), BitmapFactory.decodeResource(activity.resources, R.drawable.america), americaList))
        list.add(Continent("Asia", activity.resources.getColor(R.color.colorAsia), BitmapFactory.decodeResource(activity.resources, R.drawable.asia), asiaList))
        list.add(Continent("Oceania", activity.resources.getColor(R.color.colorOceania), BitmapFactory.decodeResource(activity.resources, R.drawable.oceania), oceaniaList))
        return list
    }
}