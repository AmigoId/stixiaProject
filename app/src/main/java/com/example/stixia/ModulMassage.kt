package com.example.stixia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModulMassage  : ViewModel(){
    val messageToAc: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val messageToF1: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val messageToF2: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

}