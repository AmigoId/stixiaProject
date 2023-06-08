package com.example.stixia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.stixia.databinding.ActivityLoginBinding
import com.parse.ParseObject
import com.parse.ParseUser
import kotlin.random.Random

////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
////////////Норм точка отката///////////////////////////////
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////

class login_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var bindingClass : ActivityLoginBinding
        bindingClass = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        bindingClass.goUnLogin.setOnClickListener {
            intent = Intent(this, UnLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        var random:Int = Random.nextInt(arrOfImage.size)

        bindingClass.okRegister.setOnClickListener{
            if (bindingClass.inLogin.text.toString() != "" && bindingClass.inPassword.text.toString() != "") {
                var user = ParseUser()
                user.setUsername(bindingClass.inLogin.text.toString())
                user.setPassword(bindingClass.inPassword.text.toString())
                user.put("imageId", arrOfImage[Random.nextInt(arrOfImage.size)])
                user.put("bio", "")
                user.put("countSub", 0)

                user.signUpInBackground {
                    if (it != null) {
                        Log.d("MainActivity", "${it.code}.${it.message}")
                    } else {
                        Log.d("MainActivity", "Object saved.")
                    }
                }
                
            } else {
                Toast.makeText(this, "Please enter a name and password!", Toast.LENGTH_LONG)
                    .show();

            }

        }


        bindingClass.okLogin.setOnClickListener{
            ParseUser.logInInBackground(
                bindingClass.inLogin.text.toString(),
                bindingClass.inPassword.text.toString()
            ) { user, e ->
                if (user != null) {
                    Log.d("","Login Successful")
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e("","${e.code}. ${e.message}")
                }
            }

                }
            }

        }





