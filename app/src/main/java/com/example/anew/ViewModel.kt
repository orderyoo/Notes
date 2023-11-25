package com.example.anew

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    lateinit var tempGroup: Group

    val data = MutableLiveData<List<Group>>()

    fun getListFromDB(){
        viewModelScope.launch {
            data.value = repository.getList()
        }
    }

    fun setDefaultItem(){
        tempGroup = Group(name =  "Название", info = "Текст")
    }
    fun setSelectedItem(group : Group){
        tempGroup = group
    }
    fun setName(name : String){
        tempGroup.name = name
    }
    fun setInfo(info: String){
        tempGroup.info = info
    }
    fun save(){
        viewModelScope.launch {
            Dependencies.repository.upsertData(tempGroup)
        }
    }
    fun delete(){
        viewModelScope.launch {
            Dependencies.repository.delData(tempGroup)
        }
    }
}


