package com.example.greanflag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser

class introActivity : baseActivity() {

   private var create:TextView?=null
    private var login: TextView?=null
//    private var  mAuth: FirebaseAuth?= null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)
        create = findViewById(R.id.tv_create_account)
        login = findViewById(R.id.tv_create_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

       create?.setOnClickListener {
           startActivity(Intent(this,accountActivity::class.java))
       }
        login ?.setOnClickListener {
            startActivity(Intent(this,loginActivity::class.java))
        }
//        mAuth = FirebaseAuth.getInstance()

    }
//    @Override
//    override fun onStart(){
//        super.onStart()
//        FirebaseUser user = mAuth.getCurrentUser()
//        if(user == null){
//
//        }
//
//    }
}