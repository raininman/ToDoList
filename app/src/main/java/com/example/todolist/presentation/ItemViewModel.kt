package com.example.todolist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ListRepositoryImpl
import com.example.todolist.domain.AddItemUseCase
import com.example.todolist.domain.EditItemUseCase
import com.example.todolist.domain.GetItemUseCase
import com.example.todolist.domain.Item
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ListRepositoryImpl(application)

    private val getItemUseCase = GetItemUseCase(repository)
    private val addItemUseCase = AddItemUseCase(repository)
    private val editItemUseCase = EditItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputDescription = MutableLiveData<Boolean>()
    val errorInputDescription: LiveData<Boolean>
        get() = _errorInputDescription

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item>
        get() = _item

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getItem(itemId: Int) {
        viewModelScope.launch {
            val item = getItemUseCase.getItem(itemId)
            _item.value = item
        }
    }

    fun addItem(inputName: String?, inputDescription: String?) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)
        val fieldsValid = validateInput(name, description)
        if (fieldsValid) {
            viewModelScope.launch {
                val item = Item(name, description, true)
                addItemUseCase.addItem(item)
                finishWork()
            }
        }
    }

    fun editItem(inputName: String?, inputDescription: String?) {
        val name = parseString(inputName)
        val description = parseString(inputDescription)
        val fieldsValid = validateInput(name, description)
        if (fieldsValid) {
            _item.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, description = description)
                    editItemUseCase.editItem(item)
                    finishWork()
                }
            }

        }

    }

    private fun parseString(input: String?): String {
        return input?.trim() ?: ""
    }

    private fun validateInput(name: String, description: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (description.isBlank()) {
            _errorInputDescription.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputDescription() {
        _errorInputDescription.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}