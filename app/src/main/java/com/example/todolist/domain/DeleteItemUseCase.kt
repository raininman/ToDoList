package com.example.todolist.domain

class DeleteItemUseCase(private val listRepository: ListRepository) {
    suspend fun deleteItem(item:Item){
        listRepository.deleteItem(item)
    }
}