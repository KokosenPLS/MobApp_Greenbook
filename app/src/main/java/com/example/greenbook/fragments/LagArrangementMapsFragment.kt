package com.example.greenbook.fragments

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.greenbook.R
import com.example.greenbook.viewModels.MyViewModelLokasjon
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.ktx.widget.PlaceSelectionError
import com.google.android.libraries.places.ktx.widget.PlaceSelectionSuccess
import com.google.android.libraries.places.ktx.widget.placeSelectionEvents
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import kotlinx.coroutines.flow.collect
import com.google.android.libraries.places.api.Places





class LagArrangementMapsFragment : Fragment(R.layout.fragment_lag_arrangement_maps) {

    private var markerLokasjon: LatLng? = null
    private val myViewModelLokasjon: MyViewModelLokasjon by navGraphViewModels(R.id.lagArrangementFragment)
    private lateinit var btnVelLokasjon:Button

    @SuppressLint("RestrictedApi")
    private val callback = OnMapReadyCallback { mMap ->
        if (markerLokasjon==null && myViewModelLokasjon.latLng.value != null) {
            mMap.addMarker(MarkerOptions().position(LatLng(myViewModelLokasjon.latLng.value!!.latitude,myViewModelLokasjon.latLng.value!!.longitude )))
        }

        mMap.setOnMapClickListener { it ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)))
            markerLokasjon= LatLng(it.latitude,it.longitude)
        }

        btnVelLokasjon = view?.findViewById(R.id.btn_maps_velgLokasjon)!!
        btnVelLokasjon.setOnClickListener {
            myViewModelLokasjon.latLng.value = markerLokasjon
            findNavController().navigateUp()
        }

        Places.initialize(getApplicationContext(), "AIzaSyDo2As499vwKMIgnn0Xja27cn6aD0lCtjM")

        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        // Hva slags data skal retuneres
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        // Listen to place selection events
        lifecycleScope.launchWhenCreated {
            autocompleteFragment?.placeSelectionEvents()?.collect { event ->
                when (event) {
                    is PlaceSelectionSuccess ->
                        setPoint(event.place.latLng,mMap)
                    is PlaceSelectionError ->
                        Log.i("tag", "Funker ikke: ")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.lag_arrangement_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }


    private fun setPoint(latLng: LatLng,mMap: GoogleMap) {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(LatLng(latLng.latitude, latLng.longitude)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            markerLokasjon=latLng
    }
}


