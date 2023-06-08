package com.example.stixia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.stixia.databinding.ActivityLoginBinding
import com.example.stixia.databinding.ActivityUnLoginBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class UnLoginActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityUnLoginBinding
    private val dataModel: ModulMassage by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        bindingClass = ActivityUnLoginBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        bindingClass.goToLogin.setOnClickListener {
            intent = Intent(this, login_activity::class.java)
            startActivity(intent)
            finish()
        }
        var indexPoem: Int = 0
        // val databaseHandler: DatabaseHandler = DatabaseHandler(requireContext())
        var poems: ArrayList<poem> = arrayListOf()
        if (poems.size == 0) {
            val query = ParseQuery.getQuery<ParseObject>("poems")
            query.orderByDescending("createdAt")
            query.findInBackground { it, e ->
                if (e == null) {
                    for (i in 0..it.size - 1) {
                        //Log.d("антидубль через main", "${poems.size}")
                        if (i < (it.size - 100)) {
                            break
                        }
                        var temp = poem(
                            it[i].objectId,
                            it[i].get("imageId").toString().toInt(),
                            it[i].get("name").toString(),
                            it[i].get("text").toString(),
                            it[i].get("bio").toString(),
                            it[i].get("countLike").toString().toInt(),
                            it[i].get("countView").toString().toInt(),
                            it[i].get("idAuthor").toString()
                        )
                        poems.add(temp)
                    }
                    indexPoem = 0           // MainVar
                    viewPoem(poems, indexPoem)
                    Log.d("myLog", poems.size.toString() + " " + indexPoem + " /size")
                } else {
                    Log.d("myLog", "Error ${e.message}")
                }
            }
        }
        bindingClass.imageR2.setOnClickListener {
            indexPoem = (indexPoem + 1) % poems.size
            viewPoem(poems, indexPoem)
        }

        bindingClass.imageL2.setOnClickListener {
            if (indexPoem > 0)
                indexPoem--
            else
                indexPoem = poems.size - 1
            viewPoem(poems, indexPoem)
        }
    }

    private fun viewPoem(poems: ArrayList<poem>, indexPoem: Int) {
        val query2 = ParseUser.getQuery()
        query2.whereEqualTo("objectId", poems[indexPoem].idAuthor)
        query2.findInBackground { it2, e ->
            Log.d("size of Author", "${it2.size}")
            if (e == null) {
                bindingClass.textChannelCountSub.text = it2[0].get("countSub").toString()
                bindingClass.textChannelImage3.setImageResource(
                    it2[0].get("imageId").toString().toInt()
                )
                bindingClass.UnLogCName.text = it2[0].get("username").toString()
                bindingClass.TextCoutnOfLike2.text = poems[indexPoem].countLike.toString()
                bindingClass.textcountOfView2.text = poems[indexPoem].countView.toString()
                bindingClass.textBio2.text = poems[indexPoem].bio
                bindingClass.imageView3.setImageResource(poems[indexPoem].imageId)
                bindingClass.textName2.text = poems[indexPoem].name
                bindingClass.textText2.text = poems[indexPoem].text

                Log.d("size of poems in list", "${poems.size}")
            }
            Log.d("myLog", it2.size.toString() + " size")
        }

    }
}

