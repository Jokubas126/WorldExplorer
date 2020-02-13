package com.example.worldexplorer.ui.worldlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldexplorer.R
import com.example.worldexplorer.model.data.Continent
import kotlinx.android.synthetic.main.fragment_world_list.*

class WorldListFragment : Fragment() {

    private lateinit var viewModel: WorldListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_world_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WorldListViewModel::class.java)

        activity?.let { viewModel.fetch(it) }

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler_view.setHasFixedSize(true)

        viewModel.continent.observe(viewLifecycleOwner, Observer {
            recycler_view.adapter = WorldListAdapter(it as MutableList<Continent>?)
        })
    }
}