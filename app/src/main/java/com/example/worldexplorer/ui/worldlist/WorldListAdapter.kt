package com.example.worldexplorer.ui.worldlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.worldexplorer.R
import com.example.worldexplorer.model.data.Continent
import com.example.worldexplorer.model.data.CountryParcel
import com.example.worldexplorer.util.loadImage
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.item_continent.view.*
import kotlinx.android.synthetic.main.item_country.view.*

class WorldListAdapter(groups: MutableList<Continent>?, itemClickListener: ItemClickListener) :
    ExpandableRecyclerViewAdapter<WorldListAdapter.ContinentViewHolder, WorldListAdapter.CountryViewHolder>(
        groups
    ) {


    private val listener = itemClickListener

    interface ItemClickListener {
        fun onCountryClicked(view: View, countryCode: String)
        fun onContinentClicked(position: Int)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ContinentViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_continent, parent, false)
        return ContinentViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): CountryViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindChildViewHolder(
        holder: CountryViewHolder?, flatPosition: Int,
        group: ExpandableGroup<*>?, childIndex: Int
    ) {

        holder?.onBind(group?.items?.get(childIndex) as CountryParcel, listener)
    }

    override fun onBindGroupViewHolder(
        holder: ContinentViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        holder?.onBind(group as Continent, flatPosition, listener)
    }


    class ContinentViewHolder(itemView: View) : GroupViewHolder(itemView) {
        private val view = itemView
        private lateinit var continent: Continent
        private var groupPosition = 0
        private lateinit var listener: ItemClickListener

        fun onBind(continent: Continent, position: Int, listener: ItemClickListener) {
            groupPosition = position
            this.listener = listener
            view.continent_image_view.setImageBitmap(continent.bitmap)
            view.continent_name_view.text = continent.continentTitle
            view.continent_name_view.setTextColor(continent.titleColor)
            this.continent = continent
        }

        override fun expand() {
            super.expand()
            listener.onContinentClicked(groupPosition)
        }
    }

    class CountryViewHolder(itemView: View) : ChildViewHolder(itemView) {
        private val view = itemView
        private lateinit var country: CountryParcel

        fun onBind(country: CountryParcel, listener: ItemClickListener) {
            this.country = country

            loadImage(view.country_flag_view, country.flagPath)
            view.country_name_view.text = country.name
            view.country_capital_view.text = country.capital

            view.setOnClickListener { listener.onCountryClicked(it, country.code3) }
        }
    }
}