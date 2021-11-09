package com.example.greenbook.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class ViewModelLagArrangement : ViewModel() {
    val latLng: MutableLiveData<LatLng> = MutableLiveData()
    val dato: MutableLiveData<String> = MutableLiveData()
    val tid: MutableLiveData<String> = MutableLiveData()

}