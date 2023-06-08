package com.example.stixia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stixia.databinding.FragmentAuthorBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import com.parse.SaveCallback


var author = Author("", 0, "UnKnow", "UnKnow", "UnKnow", 0)

class FragmentAuthor : Fragment(), PoemAdapter.Listener {
    private val dataModel2 : ModulMassage by viewModels()

    val dataModel: ModulMassage by activityViewModels()

    lateinit var bindingClass: FragmentAuthorBinding
     var poems: ArrayList<poem> = ArrayList()
    val myAdapter = PoemAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var authorGetId : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentAuthorBinding.inflate(layoutInflater)
        val myDataFromActivity: poem? = (activity as MainActivity?)!!.getPoem()
        var authorGetId = myDataFromActivity!!.idAuthor
        val query2 = ParseUser.getQuery()
        query2.whereEqualTo("objectId", authorGetId)
        query2.findInBackground { it, e ->
            if (e == null) {
                bindingClass.profilName.text = it[0].get("username").toString()
                bindingClass.ProfilImage.setImageResource(it[0].get("imageId").toString().toInt())
                val text = "Ценителей: " + it[0].get("countSub").toString().toInt()
                bindingClass.ProfilCountOfSub.text = text
                bindingClass.ProfilBio.text = it[0].get("bio").toString()
                Log.e("loading", "ok")
            } else {
                Log.e("myLog", "Error ")
            }
        }


        bindingClass.ProfilSub.setOnClickListener {
            var key2: Int = 0
            val query = ParseQuery.getQuery<ParseObject>("authorSub")
            query.whereEqualTo("idAuthor", ParseUser.getCurrentUser().objectId)
            query.findInBackground { it, e ->
                if (e == null) {
                    if (it.size != 0) {
                        for (i in 0..it.size - 1) {
                            if (authorGetId == it[i].get("idBook")) {
                                key2 = 1
                                Log.d("cheker key", "${key2}")
                                Toast.makeText(
                                    requireContext(),
                                    "Подписка уже оформлена!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                break
                            }
                        }
                    }
                    Log.d("Size AuthorSub in book", it.size.toString() + " size")
                    if (key2 == 0) {
                        val todo = ParseObject("authorSub")
                        todo.put("idAuthor", ParseUser.getCurrentUser().objectId)
                        todo.put("idBook", authorGetId)
                        todo.saveInBackground(SaveCallback { e ->
                            if (e == null) {
                                Log.d("loading book", "ok")
                                val query = ParseQuery.getQuery<ParseObject>("author")
                                query.whereEqualTo("idAuthor", authorGetId)
                                query.get(authorGetId).put("countSub", ++author.countSub)

                                bindingClass.ProfilCountOfSub.text = (++author.countSub).toString()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Что то пошло не так.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("AuthorError", "${e.message}")
                            }
                        })
                    }
                } else {
                    Log.d("myLog", "Error ${e.message}")
                }
            }
        }
        init()

        return bindingClass.root
    }

    private fun init(){
        bindingClass.authorRv.layoutManager = GridLayoutManager(getContext(),1)
        val myDataFromActivity: poem? = (activity as MainActivity?)!!.getPoem()
        var authorGetId = myDataFromActivity!!.idAuthor
        bindingClass.authorRv.adapter = myAdapter
        val query = ParseQuery.getQuery<ParseObject>("poems")
        query.whereEqualTo("idAuthor", authorGetId)
        query.findInBackground { it, e ->
            if (e == null) {
                Log.e("myLog", "authorGetId:${authorGetId}")
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

    //private fun init() {
        //bindingClass.authorRv.layoutManager = GridLayoutManager(getContext(),2)
       // bindingClass.authorRv.adapter = myAdapter
       // val query = ParseQuery.getQuery<ParseObject>("poems")
       // query.whereEqualTo("idAuthor", authorGetId)
      //  query.findInBackground { it, e ->
     //       if (e == null) {
     //           for (i in 0..it.size - 1) {
     //               var temp = poem(
      //                  it[i].objectId,
      //                  it[i].get("imageId").toString().toInt(),
      //                  it[i].get("name").toString(),
      //                  it[i].get("text").toString(),
      //                  it[i].get("bio").toString(),
      //                  it[i].get("countLike").toString().toInt(),
      //                  it[i].get("countView").toString().toInt(),
     //                   it[i].get("idAuthor").toString())
     //               poems.add(temp)
      //          }
      //          Log.d("myLog", it.size.toString() + "size poems in Author")
       //         for(i in 0..poems.size - 1)
       //             myAdapter.addItem(poems[i])
      //      } else {
      //          Log.d("myLog", "Error")
       //     }

      //  }
    //}


    companion object {
        fun newInstance() =
            FragmentAuthor()
    }

    override fun onClick(poem: poem) {
        dataModel.messageToAc.value = "Open TextView"
        dataModel.messageToFTextId.value = poem
    }
}
