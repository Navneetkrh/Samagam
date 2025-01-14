package com.ingray.samagam.Activity

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ingray.samagam.R

class LoginActivity : AppCompatActivity() {
    //Initalizing the variables
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var signup:TextView
    private lateinit var reset:TextView
    private lateinit var signin:Button
    private lateinit var auth: FirebaseAuth
    lateinit var sEmail:String
    lateinit var sPass:String
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        callVariablesById()
        val tent = intent
        val cEmail = tent.getStringExtra("email").toString()
        if(cEmail!="null"){
            email.setText(cEmail)
        }

        callOnClickListeners()

    }

    private fun callOnClickListeners() {



        signin.setOnClickListener{
            sEmail = email.text.toString()
            sPass = password.text.toString()
            var b:Boolean = checkTheConditions()
            if(b){
                auth.signInWithEmailAndPassword(sEmail, sPass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {

                            if(auth.currentUser?.isEmailVerified == true){
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(
                                    applicationContext,
                                    "Email not Verified",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
        reset.setOnClickListener{
            var intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        signup.setOnClickListener{
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkTheConditions():Boolean {
        if (TextUtils.isEmpty(email.text.toString())) {
            Toast.makeText(
                applicationContext,
                "Please enter email!!",
                Toast.LENGTH_LONG
            )
                .show()
            return false
        }

        if (TextUtils.isEmpty(password.text.toString())) {
            Toast.makeText(
                applicationContext,
                "Please enter password!!",
                Toast.LENGTH_LONG
            )
                .show()
            return false
        }
        return true
    }

    private fun callVariablesById() {
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPass)
        reset = findViewById(R.id.resetPass)
        signup = findViewById(R.id.redSignup)
        signin= findViewById(R.id.btnSignin)
        auth = Firebase.auth
    }
}
