package com.ts.notification

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {

    private lateinit var email: TextView
    private lateinit var provider: TextView
    private lateinit var btnSignOut: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        email = findViewById<TextView>(R.id.tvEmail)
        provider = findViewById<TextView>(R.id.tvProvider)
        btnSignOut = findViewById(R.id.btnSignOut)

        val bundle = intent.extras
        val temail = bundle?.getString("email")
        val tprovider = bundle?.getString("provider")

        email.setText(temail)
        provider.setText(tprovider)


        val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",temail)
        prefs.putString("provider",tprovider)
        prefs.apply()
        setup(temail?: "", tprovider?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        /*
        btnSignOut.setOnClickListener() {
            val prefs: SharedPreferences.Editor = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }*/
    }
}