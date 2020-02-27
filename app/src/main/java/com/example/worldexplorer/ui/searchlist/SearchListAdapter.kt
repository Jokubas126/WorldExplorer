package com.example.worldexplorer.ui.searchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ItemCountryBinding
import com.example.worldexplorer.model.data.Country
import com.example.worldexplorer.util.MAIN_LOCALE

class SearchListAdapter(private val listener: SearchAdapterActionsListener) :
    RecyclerView.Adapter<SearchListAdapter.ViewHolder>(), Filterable {

    private var countryList: MutableList<Country> = mutableListOf()
    private var filteredCountryList: MutableList<Country> = mutableListOf()

    interface SearchAdapterActionsListener {
        fun onAdapterEmpty()
        fun onCountryClicked(view: View, countryCode: String)
    }

    fun updateCountryList(list: List<Country>, searchQuery: String) {
        countryList.clear()
        countryList.addAll(list)
        filter.filter(searchQuery)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemCountryBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(filteredCountryList[position], listener)
    }

    override fun getItemCount(): Int {
        return filteredCountryList.size
    }

    override fun getFilter(): Filter? {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<Country> = mutableListOf()

            if (constraint.isEmpty()) {
                filteredList.addAll(countryList)
            } else {
                val filterPattern = constraint.toString().toLowerCase(MAIN_LOCALE).trim()

                for (country in countryList) {
                    if (country.name.toLowerCase(MAIN_LOCALE).contains(filterPattern)
                        || country.capital.toLowerCase(MAIN_LOCALE).contains(filterPattern)
                        || country.code.toLowerCase(MAIN_LOCALE).contains(filterPattern)
                    ) {
                        filteredList.add(country)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredCountryList.clear()
            @Suppress("UNCHECKED_CAST")
            filteredCountryList.addAll(results.values as Collection<Country>)
            notifyDataSetChanged()
            if (filteredCountryList.isNullOrEmpty())
                listener.onAdapterEmpty()
        }
    }


    class ViewHolder(itemView: ItemCountryBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val view = itemView

        fun onBind(country: Country, listener: SearchAdapterActionsListener) {
            view.country = country
            view.root.setOnClickListener { listener.onCountryClicked(view.root, country.code) }
        }
    }
}
