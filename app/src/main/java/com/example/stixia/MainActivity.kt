package com.example.stixia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

import com.example.stixia.databinding.ActivityMainBinding
import com.example.stixia.databinding.FragmentB2MyTextBinding
import com.parse.ParseObject
import com.parse.ParseUser

class MainActivity : AppCompatActivity() {
    private val dataModel : ModulMassage by viewModels()
    private var launcher: ActivityResultLauncher<Intent>? = null
    //var thisAuthor = Author(0,0,"0","0","0",0)
    var thisPoem = poem("sLfC6rdrwX",0,"0","0","0",0,0,"1")
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
                /*
                thisAuthor.id = intent.getIntExtra("id", 0)!!
                thisAuthor.imageId = intent.getIntExtra("imageId",0)!!
                thisAuthor.login = intent.getStringExtra("login")!!
                thisAuthor.password = intent.getStringExtra("password")!!
                thisAuthor.bio = intent.getStringExtra("bio")!!
                thisAuthor.countSub = intent.getIntExtra("countSub",0)!!
                 */


        /*Log.d("myLog","Получен ответ: ${thisAuthor.id}, ${thisAuthor.imageId}," +
                " ${thisAuthor.login},${thisAuthor.password}, ${thisAuthor.bio}, ${thisAuthor.countSub}\").")*/

        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Go Exit"){
                intent = Intent(this, login_activity::class.java)
                startActivity(intent)
                ParseUser.logOut()
                finish()
            }
        })


        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Open Book"){
                supportFragmentManager.beginTransaction().replace(R.id.spaceforTextAndAuthor, b2FragmentMyBook.newInstance()).commit()
            }
        })
        dataModel.messageToFTextId.observe(this,{
            bindingClass.mainLayout.closeDrawers()
        })
        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Open Text"){
                supportFragmentManager.beginTransaction().replace(R.id.spaceforTextAndAuthor, b2FragmentMyText.newInstance()).commit()
            }
        })
        dataModel.messageToAcId.observe(this,{
           thisPoem.idAuthor=it
        })
        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Open Author"){
                bindingClass.bnv.setSelectedItemId(R.id.autor)
            }
        })
        dataModel.messageToAc.observe(this,{
            if(it.toString()=="Open TextView"){
                bindingClass.bnv.setSelectedItemId(R.id.text)
            }
        })

    }
    /*public fun  getMyData():Author {
        return thisAuthor;
    }*/
    public fun  getPoem():poem {
        return thisPoem;
    }

}
