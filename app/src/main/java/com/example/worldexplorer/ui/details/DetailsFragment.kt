package com.example.worldexplorer.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {

    private lateinit var viewModel:DetailsViewModel
    private lateinit var informationLayout: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        informationLayout = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return informationLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.fetch(arguments)

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.country.observe(viewLifecycleOwner, Observer {
            if (it != null)
                informationLayout.country = it
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it != null)
                progress_bar.visibility = it
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null)
                error_view.visibility = it
        })
    }
}
