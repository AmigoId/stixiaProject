package com.example.stixia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.stixia.databinding.FragmentB2MyBookBinding


class b2FragmentMyBook : Fragment() {
    lateinit var bindingClass : FragmentB2MyBookBinding
    private val dataModel : ModulMassage by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingClass = FragmentB2MyBookBinding.inflate(layoutInflater)
        bindingClass.imageView3.setImageResource(R.drawable.b8)
        bindingClass.goMyText.setOnClickListener{
            dataModel.messageToAc.value = "Open Text"
        }
        return bindingClass.root
    }

    companion object {

        fun newInstance() =
            b2FragmentMyBook()
            }
    }
