package com.example.worldexplorer.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ItemBorderingCountryBinding
import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.model.data.CountryParcel

class BorderingCountriesAdapter(private val countryList: MutableList<Country>, onCountryClickedListener: CountryClickedListener) :
    RecyclerView.Adapter<BorderingCountriesAdapter.ViewHolder>() {

    private val listener = onCountryClickedListener

    interface CountryClickedListener {
        fun onCountryClickedListener(view: View, countryName: String)
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemBorderingCountryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_bordering_country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(countryList[position], listener)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    class ViewHolder(itemView: ItemBorderingCountryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val itemCountryBinding: ItemBorderingCountryBinding = itemView

        fun onBind(country: Country, listener: CountryClickedListener) {
            itemCountryBinding.country = country

            itemView.setOnClickListener {
                listener.onCountryClickedListener(it, country.name)
            }
        }
    }
}