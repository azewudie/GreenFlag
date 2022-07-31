package com.example.greanflag

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class accountActivity : baseActivity() {
    private var intro: ImageView?=null
    private var inp_email:TextInputEditText?=null
    private var inp_Password: TextInputEditText?=null
    private var inp_rPassword:TextInputEditText?=null
    private var create_acount:TextView?=null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        inp_email = findViewById(R.id.input_email)
        inp_Password =findViewById(R.id.input_password)
        inp_rPassword =findViewById(R.id.input__repeat_password)
        intro = findViewById(R.id.left_icon)
        create_acount =findViewById(R.id.tv_create_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        intro?.setOnClickListener {
            startActivity(Intent(this,introActivity::class.java))
        }
        create_acount?.setOnClickListener {
            registerUser()
        }
        inp_email?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.drawable.input_focus_border)
            if (!hasFocus) {
                inp_email?.error = validEmail();
                var tx:String= "Invalid Email Address"
                if(inp_email?.length()==0 ||inp_email?.text.toString() ==tx){
                    inp_email?.background = resources.getDrawable(R.drawable.input_blur_border)
                }

            }
        })



        inp_Password?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.drawable.input_focus_border)
            if (!hasFocus) {
                inp_Password?.error = validPassword();
                var tx:String= "Minimum 8 Character password"
                if(inp_Password?.length() ==0 || inp_Password?.text.toString() == tx ||
                    inp_Password?.text.toString() == "Must Contain one Upper-case Character"  ||
                    inp_Password?.text.toString() == "Must Contain one Lower-case Character" ||
                    inp_Password?.text.toString()=="Must Contain one special Character(@#\$%^&+=)"){
                    inp_Password?.background = resources.getDrawable(R.drawable.input_blur_border)
                }


            }
        })
        inp_rPassword?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            v.background = resources.getDrawable(R.drawable.input_focus_border)
            if (!hasFocus) {
                inp_rPassword?.error = validrPassword();
//                setHelperTextColor(ColorStateList textColors)
                var tx:String= "Minimum 8 Character password"
                if(inp_rPassword?.length() ==0 || inp_rPassword?.text.toString() == tx ||
                    inp_rPassword?.text.toString() == "Must Contain one Upper-case Character"  ||
                    inp_rPassword?.text.toString() == "Must Contain one Lower-case Character" ||
                    inp_rPassword?.text.toString()=="Must Contain one special Character(@#\$%^&+=)"){
                    inp_rPassword?.background = resources.getDrawable(R.drawable.input_blur_border)
                }


            }
        })


    }

    private fun registerUser(){
        val email:String = inp_email?.text.toString().trim{it <= ' '}
        val password:String = inp_Password?.text.toString().trim{it <= ' '}
        val rPassword:String = inp_rPassword?.text.toString().trim{it <= ' '}

        if(validateForm(email,password,rPassword)){
            /**
             * test
             */
//            Toast.makeText(this,"Now we can register a new user.",Toast.LENGTH_LONG).show()
            Toast.makeText(this,"Request Send...",Toast.LENGTH_LONG).show()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    Toast.makeText(
                        this, "You have seccesfully " +
                                "registered the email address $registeredEmail",
                        Toast.LENGTH_LONG
                    ).show()
                    FirebaseAuth.getInstance().signOut()
                    finish()
                } else {
                    Toast.makeText(
                        this, task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
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
        val passwordText = inp_Password?.text.toString()
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
    private fun  validrPassword():String?{
        val passwordText = inp_rPassword?.text.toString()
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
    private fun validateForm(email:String
                             ,password:String,rPassword:String):Boolean{

        return when{
            TextUtils.isEmpty(email)->{
                inp_email?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        inp_email?.background = resources.getDrawable(R.drawable.input_blur_border)
                    }
                })
                Toast.makeText(this,"Please Enter your Email",Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(password)->{
                inp_Password?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        inp_Password?.background = resources.getDrawable(R.drawable.input_blur_border)
                    }
                })
                Toast.makeText(this,"Please Enter your Password",Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(rPassword)->{
                inp_rPassword?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        inp_rPassword?.background = resources.getDrawable(R.drawable.input_blur_border)
                    }
                })
                Toast.makeText(this,"Please ReEnter your password",Toast.LENGTH_LONG).show()
                false
            }
            (inp_Password?.text?.toString() != inp_rPassword?.text?.toString())->{
                inp_rPassword?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        inp_rPassword?.background = resources.getDrawable(R.drawable.input_blur_border)
                    }
                })
                inp_Password?.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                    if (!hasFocus) {
                        inp_Password?.background = resources.getDrawable(R.drawable.input_blur_border)
                    }
                })
                Toast.makeText(this,"Please ReEnter your password",Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true;
            }

        }

    }
}