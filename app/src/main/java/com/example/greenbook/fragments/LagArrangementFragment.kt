package com.example.greenbook.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.example.greenbook.dataObjekter.Innlegg
import com.example.greenbook.viewModels.MyViewModelLokasjon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class LagArrangementFragment : Fragment(R.layout.fragment_lag_arrangement) {
    private val args: SkrivInnleggFragmentArgs by navArgs()
    lateinit var tittelTF: EditText
    lateinit var beskrivelseTF: EditText
    lateinit var stedTF: EditText
    lateinit var tidTF: TextView
    lateinit var tidBtn: ImageView
    lateinit var plasserTF: EditText
    lateinit var btnLag: Button
    lateinit var dateImage: ImageView
    lateinit var dateTV:TextView
    lateinit var btnMaps:ImageView
    lateinit var mapsTf: TextView
    private lateinit var bildeTF:TextView
    private lateinit var btnBilde:ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: Database
    private lateinit var uri:Uri
    private val storage = FirebaseStorage.getInstance()
    private val pickerContent = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri = it
        bildeTF.text = uri.toString()
    }
    private val myViewModelLokasjon: MyViewModelLokasjon by navGraphViewModels(R.id.lagArrangementFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        user = auth.currentUser!!
        database = Database()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tittelTF = view.findViewById(R.id.txt_tittel)
        beskrivelseTF = view.findViewById(R.id.txt_lag_arrangement_beskrivelse)
        stedTF = view.findViewById(R.id.txt_sted)
        plasserTF = view.findViewById(R.id.txt_plasser)
        btnLag = view.findViewById(R.id.btn_registrer)
        btnMaps = view.findViewById(R.id.lag_arrangement_btnMap)
        mapsTf = view.findViewById(R.id.lag_arrangement_kartTekst)
        btnBilde = view.findViewById(R.id.lag_arrangement_btnBilde)
        bildeTF = view.findViewById(R.id.lag_arrangement_bilde)

        datepicker()
        clockpicker()
        if(myViewModelLokasjon.latLng.value!=null) {
            mapsTf.text= "lokasjon er valgt"
        }


        btnMaps.setOnClickListener {
            val action = LagArrangementFragmentDirections.actionLagArrangementFragmentToLagArrangementMapsFragment()
            findNavController().navigate(action)
        }

        btnBilde.setOnClickListener {
            pickerContent.launch("image/*")
        }

        btnLag.setOnClickListener {
            if (tittelTF.text.isEmpty() || beskrivelseTF.text.isEmpty() || stedTF.text.isEmpty() || dateTV.text.isEmpty() || tidTF.text.isEmpty()) {
                if (tittelTF.text.isEmpty()) {
                    tittelTF.error = resources.getString(R.string.glemt_felt_tittel)
                }
                if (beskrivelseTF.text.isEmpty()) {
                    beskrivelseTF.error = resources.getString(R.string.glemt_felt_beskrivelse)
                }
                if (stedTF.text.isEmpty()) {
                    stedTF.error = resources.getString(R.string.glemt_felt_sted)
                }
                if (dateTV.text.isEmpty()) {
                    dateTV.error = resources.getString(R.string.glemt_felt_dato)
                }
                if (tidTF.text.isEmpty()) {
                    tidTF.error = resources.getString(R.string.glemt_felt_tid)
                }
            } else {
                if (bildeTF.text.toString().isNotEmpty()) {
                    var bildeURL: String? = null
                    val path = "Arrangement/" + UUID.randomUUID() + ".png"
                    val arrangementRef = storage.getReference(path)
                    val uploadTask = arrangementRef.putFile(uri)
                    val getDownloadUriTask = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        arrangementRef.downloadUrl
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            bildeURL = task.result.toString()
                            val arr = Arrangement(
                                null,
                                user.uid,
                                tittelTF.text.toString(),
                                beskrivelseTF.text.toString(),
                                stedTF.text.toString(),
                                dateTV.text.toString(),
                                tidTF.text.toString(),
                                plasserTF.text.toString().toInt(),
                                myViewModelLokasjon.latLng.value?.latitude.toString(),
                                myViewModelLokasjon.latLng.value?.longitude.toString(),
                                bildeURL
                            )
                            val arrangementId = database.addArrangement(arr)
                            val action =
                                LagArrangementFragmentDirections.actionLagArrangementFragmentToArrangementFragment(
                                    arrangementId,
                                    arr.tittel!!
                                )
                            findNavController().navigate(action)
                        }
                    }
                } else {
                    val arr = Arrangement(
                        null,
                        user.uid,
                        tittelTF.text.toString(),
                        beskrivelseTF.text.toString(),
                        stedTF.text.toString(),
                        dateTV.text.toString(),
                        tidTF.text.toString(),
                        plasserTF.text.toString().toInt(),
                        myViewModelLokasjon.latLng.value?.latitude.toString(),
                        myViewModelLokasjon.latLng.value?.longitude.toString()
                    )
                    val arrangementId = database.addArrangement(arr)
                    val action =
                        LagArrangementFragmentDirections.actionLagArrangementFragmentToArrangementFragment(
                            arrangementId,
                            arr.tittel!!
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun datepicker() {
        dateImage = view?.findViewById(R.id.registrer_bruker_dp_icon)!!
        dateTV = view?.findViewById(R.id.registrer_bruker_datepicker)!!

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dateImage.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dateTV.text = "$mDay/$mMonth/$mYear"
            }, year, month, day)
            dpd.show()
        }
    }

    fun clockpicker() {
        tidTF = view?.findViewById(R.id.txt_lag_arrangement_tid)!!
        tidBtn = view?.findViewById(R.id.txt_lag_arrangement_tidKnapp)!!

        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                tidTF.text = String.format("%d : %d", hourOfDay, minute)
            }
        }, hour, minute, false)

        tidBtn.setOnClickListener {
            mTimePicker.show()
        }
    }
}