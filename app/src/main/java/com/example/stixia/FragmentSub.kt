package com.example.stixia

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stixia.databinding.FragmentSubBinding
import com.example.stixia.databinding.FragmentTopBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser


class Fragment_Sub : Fragment(), PoemAdapter.Listener{
    private val dataModel : ModulMassage by activityViewModels()
    lateinit var bindingClass : FragmentSubBinding
    val myAdapter = PoemAdapter(this)                 //
    var poems = ArrayList<poem>()                  //
    var poemsIndexToPrint = 0
    lateinit var authors: List<Author>              //
    lateinit var authorsSub: List<AuthorSub>
    lateinit var authorPoem: List<AuthorPoem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentSubBinding.inflate(layoutInflater)
        init()
        return bindingClass.root
    }

    private fun init(){
        bindingClass.rvSub.layoutManager = GridLayoutManager(getContext(),2)
        bindingClass.rvSub.adapter = myAdapter

        val query = ParseQuery.getQuery<ParseObject>("authorSub")
        query.whereEqualTo("idAuthor", ParseUser.getCurrentUser().objectId.toString())
        query.orderByDescending("createdAt")
        var subs =  ArrayList<String>()

        query.findInBackground { it, e ->
            if (e == null) {
                Log.d("size of AuthorSub","${it.size}")
                for (i in 0..it.size - 1) {
                    subs.add(it[i].get("idBook").toString())
                }
                Log.d("size of subsList","${subs.size}")
                val query2 = ParseQuery.getQuery<ParseObject>("poems")
                for (i in 0..subs.size - 1) {
                    query2.whereEqualTo("idAuthor", subs[i])
                    query2.orderByDescending("createdAt")
                    query2.findInBackground { it2, e ->
                        Log.d("size of PoemsInAuthor","${it2.size}")
                        if (e == null) {
                            for (i in 0..it2.size - 1) {
                                var temp = poem(
                                    it2[i].objectId,
                                    it2[i].get("imageId").toString().toInt(),
                                    it2[i].get("name").toString(),
                                    it2[i].get("text").toString(),
                                    it2[i].get("bio").toString(),
                                    it2[i].get("countLike").toString().toInt(),
                                    it2[i].get("countView").toString().toInt(),
                                    it2[i].get("idAuthor").toString()
                                )
                                poems.add(temp)

                            }
                            Log.d("size of PoemsSize","${poems.size}")
                            var tempIndex = 0
                            for(i in poemsIndexToPrint..poems.size - 1) {
                                myAdapter.addItem(poems[i])
                                tempIndex ++
                            }
                            poemsIndexToPrint+=tempIndex
                        }

                    }

                    Log.d("myLog", it.size.toString() + " size")
                }
            }else {
                Log.d("myLog", "Error")
            }

        }

    }

    companion object {

        fun newInstance() =
            Fragment_Sub()

    }
    override  fun onClick(poem : poem){
            dataModel.messageToAc.value = "Open TextView"
            dataModel.messageToFTextId.value = poem
    }
    }
