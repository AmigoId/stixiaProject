package com.example.stixia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.clearFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stixia.databinding.FragmentB2MyBookBinding
import com.parse.GetCallback
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.SaveCallback

class b2FragmentMyBook : Fragment(), PoemAdapter.Listener {

    lateinit var bindingClass : FragmentB2MyBookBinding
    private val dataModel : ModulMassage by activityViewModels()
    val myAdapter = PoemAdapter(this)                   //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentB2MyBookBinding.inflate(layoutInflater)
        bindingClass.goMyText2.setOnClickListener{
            dataModel.messageToAc.value = "Open Text"
        }
        bindingClass.exitProfil.setImageResource(R.drawable.ic_baseline_exit_to_app_24)
        bindingClass.exitProfil.setOnClickListener{
            dataModel.messageToAc.value = "Go Exit"
        }
        var indexImageProfil : Int = 0
        init()


        //Log.e("","Получили: ${ParseUser.getCurrentUser().getInt("imageId")}")
        //val temp = ParseUser.getCurrentUser().getInt("imageId")
        //Log.e("","Получили: ${temp}")
        bindingClass.ImageProfil.setImageResource(ParseUser.getCurrentUser().getInt("imageId"))
        //bindingClass.ImageProfil.setImageResource(arrOfImage[1])
        bindingClass.AuthorEditName.setText(ParseUser.getCurrentUser().username.toString())
        bindingClass.AuthorEditBio.setText(ParseUser.getCurrentUser().get("bio").toString())

        bindingClass.setMod.setOnClickListener {
            if(AppCompatDelegate.getDefaultNightMode()!= AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        bindingClass.AuthorSave.setOnClickListener{

            if(bindingClass.AuthorEditName.text.toString() !="") {
                var status = false

                val query = ParseQuery.getQuery<ParseObject>("_User")
                query.orderByDescending("createdAt")
                query.findInBackground { it, e ->
                    if (e == null) {
                        for (i in 0..it.size - 1) {
                            if (bindingClass.AuthorEditName.text.toString() == it[i].get("username") && it[i].get("objectId") != ParseUser.getCurrentUser().objectId) {
                                Toast.makeText(
                                    getContext(),
                                    "Указанное имя занято!",
                                    Toast.LENGTH_LONG
                                ).show()
                                status = true
                                Log.d("myLog", "In занято")
                            }

                        }
                    } else {
                        Toast.makeText(getContext(), "Error ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
                if (!status) {
                   Log.d("myLog", "Всё как нада")
                    val query = ParseQuery.getQuery<ParseObject>("_User") // Configure Query with our query.
                    // adding a condition where our course name must be equal to the original course name
                    Log.d("myLog", "ParseUser.getCurrentUser().username:${ParseUser.getCurrentUser().username}")
                    query.whereEqualTo("objectId", ParseUser.getCurrentUser().objectId)
                    // in below method we are getting the unique id
                    // of the course which we have to make update.
                    query.getFirstInBackground(GetCallback<ParseObject>() { it, e ->
                        // inside done method we check
                        // if the error is null or not.
                        if (e == null) {
                            // if the error is null then we are getting
                            // our object id in below line.
                            var itID = it.objectId.toString()
                            // after getting our object id we will
                            // move towards updating our course.
                            // calling below method to update our course.
                            query.getInBackground(itID,GetCallback<ParseObject>() { object1,e ->
                                // in this method we are getting the
                                // object which we have to update.
                                if (e == null) {
                                    // in below line we are adding new data
                                    // to the object which we get from its id.
                                    // on below line we are adding our data
                                    // with their key value in our object.
                                    Log.d("myLog", "bindingClass.AuthorEditName.text.toString():${bindingClass.AuthorEditName.text.toString()}")
                                    object1.put("username", bindingClass.AuthorEditName.text.toString())
                                    object1.put("bio", bindingClass.AuthorEditBio.text.toString())
                                    object1.saveInBackground(SaveCallback { e ->
                                        if (e == null) {
                                            Toast.makeText(requireContext(), "Note saved successfully!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(requireContext(), "Oops - Try again later.", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                }else{
                                Log.d("myLog", "А теперь все плохо2")
                                Log.d("myLog", "${e.code}. ${e.message}")
                            }
                            })
                    }else {
                            Log.d("myLog", "А теперь все плохо1")
                            Log.d("myLog", "${e.code}. ${e.message}")
                        }
                    })
                }



            }
        }

        indexImageProfil = 0
        bindingClass.ImageProfil.setOnClickListener{
            ParseUser.getCurrentUser().put("imageId",arrOfImage[indexImageProfil])
            ParseUser.getCurrentUser().save()
            bindingClass.ImageProfil.setImageResource(arrOfImage[indexImageProfil])
            indexImageProfil = (indexImageProfil + 1) % arrOfImage.size
        }
        return bindingClass.root
    }

    private fun init(){
        bindingClass.myBookRc.layoutManager = GridLayoutManager(getContext(),1)
        bindingClass.myBookRc.adapter = myAdapter
        val query = ParseQuery.getQuery<ParseObject>("poems")
        query.whereEqualTo("idAuthor", ParseUser.getCurrentUser().objectId.toString())
        query.findInBackground { it, e ->
            if (e == null) {

                for (i in 0..it.size - 1) {
                    var temp = poem(it[i].objectId, it[i].get("imageId").toString().toInt(), it[i].get("name").toString(),
                        it[i].get("text").toString(), it[i].get("bio").toString(),it[i].get("countLike").toString().toInt(),
                        it[i].get("countView").toString().toInt(), it[i].get("idAuthor").toString())
                        Log.d("MyBook","ok")
                        myAdapter.addItem(temp)
                }
            } else {
                Log.e("myLog", "Error ")
            }
        }
    }


    companion object {
        fun newInstance() =
            b2FragmentMyBook()
    }

    override fun onClick(poem: poem) {
        dataModel.messageToAc.value = "Open TextView"
        dataModel.messageToFTextId.value = poem
    }
}
