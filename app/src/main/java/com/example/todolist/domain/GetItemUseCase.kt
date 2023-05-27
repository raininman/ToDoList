package com.example.todolist.domain

class GetItemUseCase(private val listRepository: ListRepository) {
    suspend fun getItem(itemId: Int):Item{
        return listRepository.getItem(itemId)
    }
}