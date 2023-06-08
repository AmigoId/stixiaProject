package com.example.stixia
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stixia.databinding.FragmentLikeBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser

class FragmentLike : Fragment(),PoemAdapter.Listener {
    private val dataModel : ModulMassage by activityViewModels()
    lateinit var bindingClass : FragmentLikeBinding
    val myAdapter = PoemAdapter(this)
    lateinit var authors: List<Author>              //
    lateinit var authorLike: List<AuthorLike>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindingClass = FragmentLikeBinding.inflate(layoutInflater)

        init()

        return bindingClass.root
    }

    private fun init(){
        bindingClass.rv.layoutManager = GridLayoutManager(getContext(),2)
        bindingClass.rv.adapter = myAdapter

        val query = ParseQuery.getQuery<ParseObject>("authorLike")
        query.whereEqualTo("idAuthor", ParseUser.getCurrentUser().objectId.toString())
        var likes =  ArrayList<String>()

        query.findInBackground { it, e ->
            if (e == null) {
                Log.d("size of AuthorLike","${it.size}")
                for (i in 0..it.size - 1) {
                    likes.add(it[i].get("idLike").toString())
                }
                Log.d("size of likesList","${likes.size}")
                val query2 = ParseQuery.getQuery<ParseObject>("poems")
                for (i in 0..likes.size - 1) {
                    query2.whereEqualTo("objectId", likes[i])
                    query2.findInBackground { it2, e ->
                        Log.d("size of PoemsInLikes","${it2.size}")
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
                                    it2[i].get("idAuthor").toString())
                                myAdapter.addItem(temp)
                            }
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
            FragmentLike()
    }
    override  fun onClick(poem : poem){
        dataModel.messageToAc.value = "Open TextView"
        dataModel.messageToFTextId.value = poem
    }
}