package com.example.worldexplorer.ui.worldlist

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.ItemContinentBinding
import com.example.worldexplorer.databinding.ItemCountryBinding
import com.example.worldexplorer.model.data.Continent
import com.example.worldexplorer.model.data.Country
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class WorldListAdapter(groups: MutableList<Continent>?) :
    ExpandableRecyclerViewAdapter<WorldListAdapter.ContinentViewHolder, WorldListAdapter.CountryViewHolder>(
        groups
    ) {

    private var continentList: MutableList<Continent> = arrayListOf()

    fun updateContinentList(newList: MutableList<Continent>){
        continentList.clear()
        continentList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ContinentViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view: ItemContinentBinding = DataBindingUtil.inflate(inflater, R.layout.item_continent, parent, false)
        return ContinentViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view: ItemCountryBinding = DataBindingUtil.inflate(inflater, R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindChildViewHolder(holder: CountryViewHolder?, flatPosition: Int,
        group: ExpandableGroup<*>?, childIndex: Int) {
        holder?.onBind(group?.items?.get(childIndex) as Country)
    }

    override fun onBindGroupViewHolder(holder: ContinentViewHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        holder?.onBind(group as Continent)
    }



    class ContinentViewHolder(itemView: ItemContinentBinding) : GroupViewHolder(itemView.root){
        private val view = itemView
        private lateinit var continent: Continent

        fun onBind(continent: Continent){
            view.continent = continent
            this.continent = continent
        }
    }

    class CountryViewHolder(itemView: ItemCountryBinding) : ChildViewHolder(itemView.root) {
        private val view = itemView
        private lateinit var country: Country

        fun onBind(country: Country){
            view.country = country
            this.country = country
        }
    }
}