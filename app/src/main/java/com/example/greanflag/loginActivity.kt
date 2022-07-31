package com.example.greanflag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class loginActivity : baseActivity() {
    private var login:ImageView?=null
    private var inp_email:TextInputEditText?=null
    private var inp_password:TextInputEditText?=null
    private var loginbtn : TextView?=null
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        login = findViewById(R.id.left_icon_login)
        inp_email = findViewById(R.id.input_email_login)
        inp_password =findViewById(R.id.input_password_login)
        loginbtn = findViewById(R.id.tv_btn_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        login?.setOnClickListener {
            startActivity(Intent(this,introActivity::class.java))
        }
        loginbtn?.setOnClickListener {
            loginRegisteredUser()
        }
        inp_email?.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.drawable.input_focus_border)
            if (!hasFocus) {
                inp_email?.error = validEmail();
                var tx:String= "Invalid Email Address"
                if(inp_email?.length()==0 ||inp_email?.text.toString() ==tx){
                    inp_email?.background = resources.getDrawable(R.drawable.input_blur_border)
                }
            }
        })
        inp_password?.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.drawable.input_focus_border)
            if (!hasFocus) {
                inp_password?.error = validPassword();
                var tx:String= "Minimum 8 Character password"
                if(inp_password?.length() ==0 || inp_password?.text.toString() == tx ||
                    inp_password?.text.toString() == "Must Contain one Upper-case Character"  ||
                    inp_password?.text.toString() == "Must Contain one Lower-case Character" ||
                    inp_password?.text.toString()=="Must Contain one special Character(@#\$%^&+=)"){
                    inp_password?.background = resources.getDrawable(R.drawable.input_blur_border)
                }



            }
        })
    }

    private fun loginRegisteredUser() {
        val email: String = inp_email?.text.toString().trim { it <= ' ' }
        val password: String = inp_password?.text.toString().trim { it <= ' ' }
        if (validateForm(email, password)) {
            Toast.makeText(this, "Request Send ...", Toast.LENGTH_LONG).show()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Login", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this,MainActivity::class.java))

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Login", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }


        }

    }

    private fun  validEmail():String?{
        val emailText = inp_email?.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Address"
        }
        return null


    }
    private fun  validPassword():String?{
        val passwordText = inp_password?.text.toString()
        if(passwordText.length <8){

            return "Minimum 8 Character password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex())){

            return "Must Contain one Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex())){

            return "Must Contain one Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex())){

            return "Must Contain one special Character(@#\$%^&+=)"
        }
        return null

    }
    private fun validateForm(
            email: String, password: String
        ): Boolean {

            return when {
                TextUtils.isEmpty(email) -> {
                    Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_LONG).show()
                    false
                }
                TextUtils.isEmpty(password) -> {
                    Toast.makeText(this, "Please Enter your Password", Toast.LENGTH_LONG).show()
                    false
                }

                else -> {
                    true;
                }

            }

        }
    }

