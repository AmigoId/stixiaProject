package com.example.stixia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.parse.ParseUser

class splash_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (ParseUser.getCurrentUser() == null) {
                intent = Intent(this, login_activity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Log.d("","Login Successful")
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }, 500)

    }
}