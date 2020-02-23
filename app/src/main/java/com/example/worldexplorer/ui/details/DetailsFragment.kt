package com.example.worldexplorer.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(), BorderingCountriesAdapter.CountryClickedListener {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var informationLayout: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        informationLayout =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return informationLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.fetch(arguments)

        recycler_view.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.country.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                informationLayout.country = it
                if (!it.borderingCountries.isNullOrEmpty()){
                    recycler_view.adapter = BorderingCountriesAdapter(it.borderingCountries, this)
                    bordering_countries_group.visibility = View.VISIBLE
                }
                details_information_layout.visibility = View.VISIBLE
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it != null)
                if (it){
                    progress_bar.visibility = View.VISIBLE
                    details_information_layout.visibility = View.GONE
                }
                else progress_bar.visibility = View.GONE
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null)
                if (it){
                    error_view.visibility = View.VISIBLE
                    details_information_layout.visibility = View.GONE
                }
                else error_view.visibility = View.GONE
        })
    }

    override fun onCountryClickedListener(view: View, countryCode: String) {
        viewModel.onCountryClicked(view, countryCode)
    }
}
