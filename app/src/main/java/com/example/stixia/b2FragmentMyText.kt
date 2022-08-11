package com.example.stixia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.stixia.databinding.FragmentB2MyTextBinding


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
        bindingClass = FragmentB2MyTextBinding.inflate(layoutInflater)
        bindingClass.goMyBook.setOnClickListener{
            dataModel.messageToAc.value = "Open Book"
        }



        return bindingClass.root
    }

    companion object {

    fun newInstance() =
        b2FragmentMyText()
        }
    }
