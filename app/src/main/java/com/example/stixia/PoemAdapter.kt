package com.example.stixia

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stixia.databinding.ContentItemBinding
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.coroutines.flow.callbackFlow

class PoemAdapter(val listener: Listener): RecyclerView.Adapter <PoemAdapter.PoemHolder>() {   //класс присвоения
    val poemList = ArrayList<poem>()
    class PoemHolder(item: View): RecyclerView.ViewHolder(item) {
        val bind1 = ContentItemBinding.bind(item)
        fun bind2(poem1: poem, listener: Listener, author: Author){
            bind1.namePoem.text= poem1.name
            bind1.textPoem.text=poem1.text
            bind1.bioPoem.text=poem1.bio
            bind1.idImagePoem.setImageResource(poem1.imageId)
            bind1.imageIdAuthorPoem.setImageResource(author.imageId)
            bind1.nameAuthorPoem.text = author.login
            bind1.loading.visibility = View.GONE
            bind1.linearLayout.setOnClickListener {
                listener.onClick(poem1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemHolder {    //Создание шаблона
        val createView = LayoutInflater.from(parent.context).inflate(R.layout.content_item,parent,false)
        return PoemHolder(createView)
    }

    override fun onBindViewHolder(holder: PoemHolder, position: Int) {         //Инецинилизация элементов шаблона
        val query = ParseUser.getQuery();
        query.whereEqualTo("objectId", poemList[position].idAuthor)
        query.findInBackground { it, e ->
            if (e == null) {
                    var temp = Author(it[0].objectId, it[0].get("imageId").toString().toInt(), it[0].get("username").toString(),"","",0)
                    Log.d("MyBook","ok")
                    //authorList.add(temp)
                    holder.bind2(poemList[position], listener, temp)
            } else {
                Log.e("myLog", "Error ")
            }
        }




        //val author : Author = Author("",
            //  test.get("imageId").toString().toInt(),
          //  test.get("username").toString(),
            // "","",1
        //)


    }

    override fun getItemCount(): Int {                              //Узнаем количество элементов
        return poemList.size
    }

    fun addItem(poem1: poem){
        poemList.add(poem1)
        notifyDataSetChanged()
    }
    interface Listener{
        fun onClick(poem:poem)
    }
}