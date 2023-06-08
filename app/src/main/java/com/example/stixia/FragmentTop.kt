package com.example.stixia

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stixia.databinding.FragmentTopBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class FragmentTop : Fragment(), PoemAdapter.Listener {
    lateinit var bindingClass : FragmentTopBinding
    val myAdapter = PoemAdapter(this)                   //
    private val dataModel : ModulMassage by activityViewModels()

    lateinit var poems: List<poem>                  //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentTopBinding.inflate(layoutInflater)
        init()


        return bindingClass.root
    }

    companion object {

        fun newInstance() =
            FragmentTop()

    }

    private fun init(){
        bindingClass.rv.layoutManager = GridLayoutManager(getContext(),2)
        bindingClass.rv.adapter = myAdapter
        val query = ParseQuery.getQuery<ParseObject>("poems")
        query.orderByDescending("createdAt")
        query.findInBackground { it, e ->
            if (e == null) {
                for (i in 0..it.size - 1) {
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
                        Log.d("MyBook", "ok")
                        myAdapter.addItem(temp)
                    }
            } else {
                Log.e("myLog", "Error ")
            }
        }
    }

    override fun onClick(poem: poem) {
        dataModel.messageToAc.value = "Open TextView"
        dataModel.messageToFTextId.value = poem
    }

}