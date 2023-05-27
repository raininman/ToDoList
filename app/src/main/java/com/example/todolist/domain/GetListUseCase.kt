package com.example.todolist.domain

import androidx.lifecycle.LiveData

class GetListUseCase(private val listRepository: ListRepository) {
    fun getList():LiveData<List<Item>>{
        return listRepository.getList()
    }
}