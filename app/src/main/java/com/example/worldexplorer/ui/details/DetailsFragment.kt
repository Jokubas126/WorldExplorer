package com.example.worldexplorer.ui.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(), BorderingCountriesAdapter.CountryClickedListener, OnMapReadyCallback,
    TouchableSupportMapFragment.OnMapTouchListener {

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
                getMapReady()
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

    private fun getMapReady(){
        val mapFragment: TouchableSupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as TouchableSupportMapFragment
        mapFragment.getMapAsync(this)
        mapFragment.setOnTouchListener(this)

        details_top_information_layout.setOnTouchListener{ _: View, event: MotionEvent ->
            onTouchDownScrollableEnabled(event, true)
            return@setOnTouchListener true
        }

        recycler_view.addOnItemTouchListener(object: RecyclerView.SimpleOnItemTouchListener(){
            override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
                onTouchDownScrollableEnabled(event, true)
                return super.onInterceptTouchEvent(rv, event)
            }
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        val countryPosition = LatLng(informationLayout.country!!.globalPosition[0], informationLayout.country!!.globalPosition[1])
        map!!.addMarker(MarkerOptions().position(countryPosition))
        map.moveCamera(CameraUpdateFactory.newLatLng(countryPosition))
        map.setMaxZoomPreference(17.5f)
        map.moveCamera(CameraUpdateFactory.zoomTo(3.5f))
    }

    override fun onMapTouch(event: MotionEvent) {
        onTouchDownScrollableEnabled(event, false)
    }

    private fun onTouchDownScrollableEnabled(event: MotionEvent, enabled: Boolean){
        when (event.action){
            MotionEvent.ACTION_DOWN -> nested_scroll_view.scrollable = enabled
        }
    }
}
