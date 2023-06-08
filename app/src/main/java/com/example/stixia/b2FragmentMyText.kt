package com.example.stixia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.stixia.databinding.FragmentB2MyTextBinding
import com.parse.ParseObject
import com.parse.ParseUser
import com.parse.SaveCallback
import java.lang.Math
import java.lang.Math.abs
import java.lang.Math.random
import kotlin.random.Random


class b2FragmentMyText : Fragment() {
    lateinit var bindingClass : FragmentB2MyTextBinding
    private val dataModel : ModulMassage by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity: MainActivity? = activity as MainActivity?

        var indexOfImage : Int = Random.nextInt(arrOfImage.size)

        bindingClass = FragmentB2MyTextBinding.inflate(layoutInflater)


        bindingClass.imageView.setImageResource(arrOfImage[indexOfImage])
        bindingClass.goMyBook.setOnClickListener{
            dataModel.messageToAc.value = "Open Book"
        }
        bindingClass.imageR.setOnClickListener{
            indexOfImage = (indexOfImage+1) % arrOfImage.size
            bindingClass.imageView.setImageResource(arrOfImage[indexOfImage])
        }
        bindingClass.imageL.setOnClickListener{
            if (indexOfImage > 0)
            {indexOfImage--}
            else{
                indexOfImage = arrOfImage.size - 1
            }
            bindingClass.imageView.setImageResource(arrOfImage[indexOfImage])
        }

        bindingClass.creatPoem.setOnClickListener{
            val todo = ParseObject("poems")
            if (bindingClass.editName.text.toString() != "" && bindingClass.editText.text.toString() != "") {

                todo.put("imageId", arrOfImage[indexOfImage])
                todo.put("name", bindingClass.editName.text.toString())
                todo.put("text", bindingClass.editText.text.toString())
                todo.put("bio", bindingClass.editBio.text.toString())
                todo.put("countLike", 0)
                todo.put("countView", 0)
                todo.put("idAuthor", ParseUser.getCurrentUser().objectId.toString())



                todo.saveInBackground(SaveCallback { e ->
                    if (e == null) {
                        Toast.makeText(requireContext(), "Note saved successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Oops - Try again later.", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(requireContext(), "Please enter a title and description!", Toast.LENGTH_LONG)
                    .show();
            }

        }


        return bindingClass.root
    }

    companion object {

    fun newInstance() =
        b2FragmentMyText()
        }
    }
