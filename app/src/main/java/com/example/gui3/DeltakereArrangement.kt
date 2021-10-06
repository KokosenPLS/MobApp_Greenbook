package com.example.gui3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView




class DeltakereArrangement : Fragment(), Deltaker_Adapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deltakere: ArrayList<DeltakerDisplay> = ArrayList()

        for (i in 1..50) {
            deltakere.add(
                DeltakerDisplay(
                    "https://picsum.photos/900/600?random&" + i,
                    "Truls Ombye Hafnor"+i
                )
            )
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerViewDeltakere)

        recyclerView?.layoutManager = LinearLayoutManager(view?.context)
        recyclerView?.adapter = Deltaker_Adapter(deltakere, this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.deltakere_layout, container, false)
    }

    companion object {
        fun newInstance() = DeltakereArrangement()
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }


}
