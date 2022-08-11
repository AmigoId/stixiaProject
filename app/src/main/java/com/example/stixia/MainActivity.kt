package com.example.stixia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels

import com.example.stixia.databinding.ActivityMainBinding
import com.example.stixia.databinding.FragmentB2MyTextBinding

class MainActivity : AppCompatActivity() {
    private val dataModel : ModulMassage by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var bindingClass : ActivityMainBinding
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportFragmentManager.beginTransaction().replace(R.id.spaceforTextAndAuthor, b2FragmentMyText.newInstance()).commit()
        bindingClass.bnv.setOnItemSelectedListener {
                item ->
            when (item.itemId) {
                R.id.text -> supportFragmentManager.beginTransaction().replace(R.id.bigFragment, FragmentText.newInstance()).commit()
                R.id.autor -> supportFragmentManager.beginTransaction().replace(R.id.bigFragment, FragmentAuthor.newInstance()).commit()
                R.id.top -> supportFragmentManager.beginTransaction().replace(R.id.bigFragment, FragmentTop.newInstance()).commit()
                R.id.sub -> supportFragmentManager.beginTransaction().replace(R.id.bigFragment, Fragment_Sub.newInstance()).commit()
                R.id.like -> supportFragmentManager.beginTransaction().replace(R.id.bigFragment, FragmentLike.newInstance()).commit()
            }
            true
        }
        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Open Book"){
                supportFragmentManager.beginTransaction().replace(R.id.spaceforTextAndAuthor, b2FragmentMyBook.newInstance()).commit()
            }
        })
        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Open Text"){
                supportFragmentManager.beginTransaction().replace(R.id.spaceforTextAndAuthor, b2FragmentMyText.newInstance()).commit()
            }
        })



    }
}