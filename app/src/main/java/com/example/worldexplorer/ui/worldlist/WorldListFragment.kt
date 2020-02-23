package com.example.worldexplorer.ui.worldlist

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldexplorer.R
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.synthetic.main.fragment_world_list.*

class WorldListFragment : Fragment(), WorldListAdapter.ItemClickListener {

    private lateinit var viewModel: WorldListViewModel

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: WorldListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_world_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorldListViewModel::class.java)
        viewModel.fetch()

        layoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = layoutManager

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.continents.observe(viewLifecycleOwner, Observer {
            adapter = WorldListAdapter(it, this)
            recycler_view.adapter = adapter
            recycler_view.visibility = View.VISIBLE
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it){
                    progress_bar.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                }

                else progress_bar.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it){
                    error_view.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                }
                else error_view.visibility = View.GONE
            }
        })
    }

    override fun onCountryClickListener(view: View, countryCode: String) {
        viewModel.onItemClicked(view, countryCode)
    }

    override fun onContinentClickListener(position: Int) {
        layoutManager.scrollToPositionWithOffset(position, 5)
    }

    override fun onPause() {
        super.onPause()
        viewModel.state = recycler_view.layoutManager?.onSaveInstanceState()
    }
}