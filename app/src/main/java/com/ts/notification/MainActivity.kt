package com.ts.notification

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
//import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.button.MaterialButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.microsoft.windowsazure.messaging.notificationhubs.NotificationHub
//import com.microsoft.windowsazure.messaging.NotificationHub
//import com.microsoft.windowsazure.messaging.notificationhubs.InstallationTemplate
//import com.microsoft.windowsazure.messaging.notificationhubs.NotificationHub

import com.ts.notification.databinding.ActivityMainBinding
import com.ts.notification.models.MessageSuccess
import com.ts.notification.models.Vehicle

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var plate: EditText
    private lateinit var brand: EditText
    private lateinit var model: EditText
    private lateinit var signupButton: MaterialButton
    private lateinit var loginButton: MaterialButton
    private lateinit var googleButton: MaterialButton
    private lateinit var reportButton: MaterialButton
    private lateinit var mainLayout: CoordinatorLayout
    private val GOOGLE_SIGN_IN = 100
    //private lateinit var mHub: NotificationHub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        signupButton = findViewById(R.id.signUpButton)
        loginButton = findViewById(R.id.logInButton)
        googleButton = findViewById(R.id.btnGoogle)
        mainLayout = findViewById(R.id.main_layout)

        plate = findViewById(R.id.etPlate)
        brand = findViewById(R.id.etBrand)
        model = findViewById(R.id.etModel)
        reportButton = findViewById(R.id.reportBtn)

        val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message","Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        FirebaseMessaging.getInstance().subscribeToTopic("test")
        notification()

        reportButton.setOnClickListener{
            val retroInstance = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            val vehicle= Vehicle(plate.text.toString(), brand.text.toString(), model.text.toString())
            val call = retroInstance.checkoutVehicleReport(vehicle)
            call.enqueue(object: Callback<MessageSuccess> {
                override fun onResponse(
                    call: Call<MessageSuccess>,
                    response: Response<MessageSuccess>
                ) {
                    val ms: MessageSuccess = response.body()!!
                    Toast.makeText(applicationContext,"is success "+ms.data,Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<MessageSuccess>, t: Throwable) {
                    println("is fail : "+t.stackTrace)
                }

            })
            Toast.makeText(this, "${plate.text} , ${brand.text} , ${model.text}", Toast.LENGTH_SHORT).show()
        }


        //Azure

        NotificationHub.setListener(CustomNotificationListener());
        NotificationHub.start(this.getApplication(), "notificationhubname", "Endpoint=sb://notificationhub18.servicebus.windows.net/;SharedAccessKeyName=DefaultListenSharedAccessSignature;SharedAccessKey=IbLXUPd/WpTp/d0ewSMNl1Kf/mDmY5nwBFBara5jiUg=");


        //setup
        setup()
        //session()

    }

    private fun notification() {
        FirebaseInstallations.getInstance().getToken(false).addOnCompleteListener{
            it.result!!.token.let {
                println("Token : ${it}")
                Toast.makeText(this,"Token : ${it}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mainLayout.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs: SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if(email != null && provider != null) {
            mainLayout.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
    }


    private fun setup() {
        signupButton.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful) {
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        } else {
                            showAlert(it.exception.toString())
                        }
                    }
            }
        }

        loginButton.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener{
                        if(it.isSuccessful) {
                            showHome(it.result?.user?.email ?:"", ProviderType.BASIC)
                        } else {

                            showAlert(it.exception.toString())
                        }
                    }
            }
        }

        googleButton.setOnClickListener {
            /*
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("#2323232323")
                .requestEmail()
                .build()
            var gb = Bundle()
            val googleCLient = GoogleSignIn.getClient(this, googleConf)
            startActivityForResult(googleCLient.signInIntent, GOOGLE_SIGN_IN)

             */
        }
    }

    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Ex : "+msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent: Intent = Intent(this,HomeActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}