package com.example.todolist.domain

class EditItemUseCase(private val listRepository: ListRepository) {
    suspend fun editItem(item:Item){
        listRepository.editItem(item)
    }
}