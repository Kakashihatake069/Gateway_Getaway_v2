package com.example.gatewaygetaways.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gatewaygetaways.databinding.ActivityTypesOfLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class TypesOfLoginActivity : AppCompatActivity() {
    lateinit var typesOfLoginBinding: ActivityTypesOfLoginBinding
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var sharedPreferences: SharedPreferences
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       typesOfLoginBinding = ActivityTypesOfLoginBinding.inflate(layoutInflater)
        setContentView(typesOfLoginBinding.root)

            initview()
    }

    private fun initview() {
        auth = Firebase.auth
        sharedPreferences = getSharedPreferences("MySharePref", MODE_PRIVATE)


        typesOfLoginBinding.loutemaillogin.setOnClickListener {
            var i = Intent(this@TypesOfLoginActivity, MainActivity::class.java)
            startActivity(i)
        }

        // Initialize sign in options the client-id is copied form google-services.json file
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("336177260823-vpkor5jlvh6eieocpoij753m25pgsd52.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        // google login
        typesOfLoginBinding.btnGoogleSignin.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            // Start activity for result
            startActivityForResult(intent, 100)
        }
        if (sharedPreferences.getBoolean("isLogin", false) == true) {

            var i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === 100) {
            // When request code is equal to 100 initialize task
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                val s = "Google sign in successful"
                // Display Toast
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    val googleSignInAccount: GoogleSignInAccount =
                        signInAccountTask.getResult(ApiException::class.java)
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        val authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
                        // Check credential
                        auth.signInWithCredential(authCredential)
                            .addOnCompleteListener(this,
                                OnCompleteListener<AuthResult?> { task ->
                                    // Check condition
                                    if (task.isSuccessful) {
                                        // When task is successful redirect to profile activity display Toast
                                        startActivity(
                                            Intent(
                                                this@TypesOfLoginActivity, DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        )
                                        Toast.makeText(this, "Firebase authentication successful", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // When task is unsuccessful display Toast
                                        Toast.makeText(this, "Authentication Failed :", Toast.LENGTH_SHORT).show()
                                    }
                                })
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }
}