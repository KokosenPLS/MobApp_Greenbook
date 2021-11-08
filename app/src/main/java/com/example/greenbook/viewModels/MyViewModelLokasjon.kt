package com.example.greenbook.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MyViewModelLokasjon: ViewModel(){
    val latLng: MutableLiveData<LatLng> = MutableLiveData()
}