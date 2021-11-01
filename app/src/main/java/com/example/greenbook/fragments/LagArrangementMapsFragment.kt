package com.example.greenbook.fragments

import android.content.Intent
import android.location.*
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.MyViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.lang.IndexOutOfBoundsException

class LagArrangementMapsFragment : Fragment() {

    private lateinit var sokeFelt:EditText
    private lateinit var godkjennKnapp:Button
    private var markerLokasjon: LatLng? = null
    val myViewModel: MyViewModel by navGraphViewModels(R.id.lagArrangementFragment)

    private val callback = OnMapReadyCallback { mMap ->
        if (markerLokasjon==null && myViewModel.latLng.value != null) {
            mMap.addMarker(MarkerOptions().position(LatLng(myViewModel.latLng.value!!.latitude,myViewModel.latLng.value!!.longitude )))
        }

        init(mMap)
        mMap.setOnMapClickListener { it ->
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(LatLng(it.latitude,it.longitude)))
            markerLokasjon= LatLng(it.latitude,it.longitude)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lag_arrangement_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.lag_arrangement_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        //------
        sokeFelt = view?.findViewById(R.id.maps_input_sook)
        godkjennKnapp=view?.findViewById(R.id.googleMaps_sendData)


        godkjennKnapp.setOnClickListener {
            if(markerLokasjon!=null) {
                myViewModel.latLng.value = markerLokasjon
            }
            else
                Toast.makeText(context, "Du må velge en lokasjon", Toast.LENGTH_SHORT).show()
        }

    }

    fun init (mMap:GoogleMap)  {
        sokeFelt.setOnEditorActionListener { _, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || keyEvent.action==KeyEvent.ACTION_DOWN
                || keyEvent.action==KeyEvent.KEYCODE_ENTER) {
                geoLocate(mMap)
                return@setOnEditorActionListener true
            }
                false
        }
    }

    private fun geoLocate(mMap:GoogleMap) {
        val searchString = sokeFelt.text.toString()
        val geoCoder = Geocoder(context)
        var list: List<Address>? = null
        try {
            list = geoCoder.getFromLocationName(searchString.toString(), 1)
        // TODO: 10/31/2021 kan hende denne er problemet
            //val noe = list[0].getAddressLine(0) // kan hende denne hjelper deg?
        }catch (e:IOException){
            Log.e(tag, "Finner ikke GeoCoder", e)
        }
        finally {
            try {
                if (list != null) {
                    Log.i("tag", list[0].toString())
                    var address = list[0]
                    setPoint(LatLng(address.latitude, address.longitude), mMap)
                }
            }catch (e:IndexOutOfBoundsException) {
                Toast.makeText(context, "Ugyldig verdi", Toast.LENGTH_SHORT).show()
                Log.e(tag, "Adresse er ugyldig", e)
            }
        }
    }

    private fun setPoint(latLng: LatLng,mMap: GoogleMap) {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(LatLng(latLng.latitude, latLng.longitude)))
            //floar kan endres for å bestemme hvor mye den skal zoome in
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            markerLokasjon=latLng

    }


}


