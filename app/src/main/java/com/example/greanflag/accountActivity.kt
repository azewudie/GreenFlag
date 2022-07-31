package com.example.greanflag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
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
            Toast.makeText(this,"Loading...",Toast.LENGTH_LONG).show()
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
    private fun validateForm(email:String
                             ,password:String,rPassword:String):Boolean{

        return when{
            TextUtils.isEmpty(email)->{
                Toast.makeText(this,"Please Enter your Email",Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(password)->{
                Toast.makeText(this,"Please Enter your Password",Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(rPassword)->{
                Toast.makeText(this,"Please ReEnter your password",Toast.LENGTH_LONG).show()
                false
            }
            (inp_Password?.text?.toString() != inp_rPassword?.text?.toString())->{
                Toast.makeText(this,"Please ReEnter your password",Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                true;
            }

        }

    }
}