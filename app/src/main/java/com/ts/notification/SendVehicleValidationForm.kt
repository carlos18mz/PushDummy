package com.ts.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.ts.notification.models.MessageSuccess
import com.ts.notification.models.Vehicle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendVehicleValidationForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_vehicle_validation_form)

        val AlertTypes = arrayOf("Vehiculo sospoechoso", "Intento de robo", "Accidente")
        val VehiclePlates = arrayOf("BOM-672", "AJSDB-23", "8DSF9-2")
        val Locations = arrayOf("AV. Proceres 185", "Av Los Héroes 287")

        val ddAlertType = findViewById<TextInputLayout>(R.id.CAFAlertType)
        val adapterAT = ArrayAdapter(this, R.layout.list_item, AlertTypes)
        (ddAlertType.editText as? AutoCompleteTextView)?.setAdapter(adapterAT)

        val ddVehiclePlate = findViewById<TextInputLayout>(R.id.CAFVehiclePlate)
        val adapterVP = ArrayAdapter(this, R.layout.list_item, VehiclePlates)
        (ddVehiclePlate.editText as? AutoCompleteTextView)?.setAdapter(adapterVP)

        val ddLocation = findViewById<TextInputLayout>(R.id.CAFLocation)
        val adapterL = ArrayAdapter(this, R.layout.list_item, Locations)
        (ddLocation.editText as? AutoCompleteTextView)?.setAdapter(adapterL)

        val btnCreateAlert = findViewById<MaterialButton>(R.id.btnCACreateAlert)
        val btnCancelAlert = findViewById<MaterialButton>(R.id.btnCACancel)
        val tvSuccess = findViewById<TextView>(R.id.tvCASuccess)

        btnCreateAlert.setOnClickListener(){
            val retroInstance = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val vehicle= Vehicle(ddVehiclePlate.editText?.text.toString(), ddAlertType.editText?.text.toString(), ddLocation.editText?.text.toString())
            val call = retroInstance.checkoutVehicleReport(vehicle)
            call.enqueue(object: Callback<MessageSuccess> {
                override fun onResponse(
                    call: Call<MessageSuccess>,
                    response: Response<MessageSuccess>
                ) {
                    tvSuccess.visibility = View.VISIBLE
                    val ms: MessageSuccess = response.body()!!
                    Toast.makeText(applicationContext,"is success "+ms.data, Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<MessageSuccess>, t: Throwable) {
                    println("is fail : "+t.stackTrace)
                }

            })

        }

        btnCancelAlert.setOnClickListener(){
            println("cancelAlert")
        }

    }
}