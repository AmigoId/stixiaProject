package com.example.stixia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModulMassage  : ViewModel(){
    val messageToAc: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val messageFromPoemToAuthorId: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val messageToAcId: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
    val messageToFTextId: MutableLiveData<poem> by lazy{
        MutableLiveData<poem>()
    }
}