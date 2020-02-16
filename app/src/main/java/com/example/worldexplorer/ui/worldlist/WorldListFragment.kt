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
import kotlinx.android.synthetic.main.fragment_world_list.*

class WorldListFragment : Fragment(), WorldListAdapter.ItemClickListener {

    private lateinit var viewModel: WorldListViewModel

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

        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.continents.observe(viewLifecycleOwner, Observer {
            recycler_view.adapter = WorldListAdapter(it, this)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it)
                    progress_bar.visibility = View.VISIBLE
                else progress_bar.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it)
                    error_view.visibility = View.VISIBLE
                else error_view.visibility = View.GONE
            }
        })
    }

    override fun onItemClickListener(view: View, countryName: String) {
        viewModel.onItemClicked(view, countryName)
    }
}