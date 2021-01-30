package com.example.dhgamesdf4.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import com.example.dhgamesdf4.R

class LoginActivity : AppCompatActivity() {

    private val btCreateAccount : AppCompatButton by lazy {
        findViewById(R.id.btCreateAccount)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupObservables()
    }

    fun setupObservables(){
        btCreateAccount.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}