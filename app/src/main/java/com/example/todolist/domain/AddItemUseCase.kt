package com.example.todolist.domain

class AddItemUseCase(private val listRepository: ListRepository) {
    suspend fun addItem(item: Item){
        listRepository.addItem(item)
    }
}