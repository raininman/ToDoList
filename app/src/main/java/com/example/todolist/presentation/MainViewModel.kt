package com.example.todolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.domain.DeleteItemUseCase
import com.example.todolist.domain.EditItemUseCase
import com.example.todolist.domain.GetListUseCase
import com.example.todolist.domain.Item
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = ListRepositoryImpl(application)

    private val getListUseCase = GetListUseCase(repository)
    private val deleteItemUseCase = DeleteItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    val list = getListUseCase.getList()

    fun deleteItem(item:Item){
        viewModelScope.launch {
            deleteItemUseCase.deleteItem(item)
        }
    }

    fun changeEnableState(item:Item){
        viewModelScope.launch {
            val newItem = item.copy(enabled = !item.enabled)
            editItemUseCase.editItem(newItem)
        }
    }

}