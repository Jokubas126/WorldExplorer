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
        fun onItemClickListener(view: View, countryCode: String)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ContinentViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_continent, parent, false)
        return ContinentViewHolder(view)
    }

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_country, parent, false)
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
        holder?.onBind(group as Continent)
    }


    class ContinentViewHolder(itemView: View) : GroupViewHolder(itemView) {
        private val view = itemView
        private lateinit var continent: Continent

        fun onBind(continent: Continent) {

            view.continent_image_view.setImageBitmap(continent.bitmap)
            view.continent_name_view.text = continent.continentTitle
            view.continent_name_view.setTextColor(continent.titleColor)
            this.continent = continent
        }
    }

    class CountryViewHolder(itemView: View) : ChildViewHolder(itemView) {
        private val view = itemView
        private lateinit var country: CountryParcel

        fun onBind(country: CountryParcel, listener: ItemClickListener) {
            this.country = country

            loadImage(view.country_flag_view, country.flagPath)
            view.country_name_view.text = country.name

            view.setOnClickListener {
                listener.onItemClickListener(it, country.code)
            }
        }
    }
}