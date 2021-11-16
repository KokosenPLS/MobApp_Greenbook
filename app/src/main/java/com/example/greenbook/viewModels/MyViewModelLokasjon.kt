package com.example.greenbook.viewModels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng



class MyViewModelLokasjon: ViewModel() {
    val latLng: MutableLiveData<LatLng> = MutableLiveData()
    val uri: MutableLiveData<Uri> = MutableLiveData()
    val tid: MutableLiveData<String> = MutableLiveData()
    val dato: MutableLiveData<String> = MutableLiveData()
}