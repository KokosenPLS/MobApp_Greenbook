package com.example.greenbook.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.greenbook.Database
import com.example.greenbook.R
import com.example.greenbook.dataObjekter.Arrangement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class LagArrangementFragment : Fragment(R.layout.fragment_lag_arrangement) {

    lateinit var tittelTF: EditText
    lateinit var beskrivelseTF: EditText
    lateinit var stedTF: EditText
    lateinit var tidTF: TextView
    lateinit var tidBtn: ImageButton
    lateinit var plasserTF: EditText
    lateinit var btnLag: Button
    lateinit var dateImage: ImageView
    lateinit var dateTV:TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        user = auth.currentUser!!
        database = Database()

    }

    // TODO: 10/27/2021 Truls: Gjør om beskrivelse til et popupvindu?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        datepicker()
        clockpicker()

        tittelTF = view?.findViewById(R.id.txt_tittel)
        beskrivelseTF = view?.findViewById(R.id.txt_lag_arrangement_beskrivelse)
        stedTF = view?.findViewById(R.id.txt_sted)
        plasserTF = view?.findViewById(R.id.txt_plasser)
        btnLag = view?.findViewById(R.id.btn_registrer)


        btnLag.setOnClickListener {

            if (tittelTF.text.isEmpty() || beskrivelseTF.text.isEmpty() || stedTF.text.isEmpty() || dateTV.text.isEmpty() || tidTF.text.isEmpty()) {
                if(tittelTF.text.isEmpty()) {
                    tittelTF.setError(resources.getString(R.string.glemt_felt_tittel))
                //tittelTF.setHintTextColor(resources.getColor(R.color.error))
                }
                if (beskrivelseTF.text.isEmpty()) {
                    beskrivelseTF.setError(resources.getString(R.string.glemt_felt_beskrivelse))
                //beskrivelseTF.setHintTextColor(resources.getColor(R.color.error))
                }
                if(stedTF.text.isEmpty()) {
                    stedTF.setError(resources.getString(R.string.glemt_felt_sted))
                //stedTF.setHintTextColor(resources.getColor(R.color.error))
                }
                if(dateTV.text.isEmpty()) {
                    dateTV.setError(resources.getString(R.string.glemt_felt_dato))
                //dateTV.setHintTextColor(resources.getColor(R.color.error))
                }
                if (tidTF.text.isEmpty()) {
                    tidTF.setError(resources.getString(R.string.glemt_felt_tid))
                //tidTF.setHintTextColor(resources.getColor(R.color.error))
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
                    plasserTF.text.toString().toInt()
                )
                val arrangementId = database.addArrangement(arr)
                Log.i("huge", arrangementId)
                val action =
                    LagArrangementFragmentDirections.actionLagArrangementFragmentToArrangementFragment(
                        arrangementId
                    )
                findNavController().navigate(action)
            }
        }
        

    }

    // TODO: 10/27/2021 Truls: metode for å gjøre det ryddigerer, usikker på om det funker å kalle de der med !!
    fun datepicker() {
        // TODO: Truls lettere om du også gjøre textview klikkbar
        dateImage = view?.findViewById(R.id.registrer_bruker_dp_icon)!!
        dateTV = view?.findViewById(R.id.registrer_bruker_datepicker)!!

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        dateImage.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                dateTV.setText(""+mDay+"/"+mMonth+"/"+mYear)
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
                tidTF.setText(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, false)

        tidBtn.setOnClickListener {
            mTimePicker.show()
        }
    }
}