package com.example.worldexplorer.ui.searchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldexplorer.R
import kotlinx.android.synthetic.main.fragment_search_list.*

class SearchListFragment : Fragment(), SearchListAdapter.SearchAdapterActionsListener {

    private lateinit var viewModel: SearchListViewModel
    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchListViewModel::class.java)
        viewModel.fetch(arguments)

        recycler_view.layoutManager = LinearLayoutManager(context)
        searchListAdapter = SearchListAdapter(this)
        recycler_view.adapter = searchListAdapter

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countryList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (viewModel.filterQuery.isNotEmpty()) {
                    searchListAdapter.updateCountryList(it, viewModel.filterQuery)
                }
                recycler_view.visibility = View.VISIBLE
                countries_not_found_view.visibility = View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it) {
                    progress_bar.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                    countries_not_found_view.visibility = View.GONE
                } else progress_bar.visibility = View.GONE
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it) {
                    error_view.visibility = View.VISIBLE
                    recycler_view.visibility = View.GONE
                    countries_not_found_view.visibility = View.GONE
                } else error_view.visibility = View.GONE
            }
        })
    }

    override fun onAdapterEmpty() {
        recycler_view.visibility = View.GONE
        countries_not_found_view.visibility = View.VISIBLE
    }

    override fun onCountryClicked(view: View, countryCode: String) {
        viewModel.onCountryClicked(view, countryCode)
    }

}
