package com.example.todolist.domain

import androidx.lifecycle.LiveData

interface ListRepository {
    suspend fun addItem(item: Item)
    suspend fun deleteItem(item:Item)
    suspend fun editItem(item:Item)
    suspend fun getItem(itemId: Int):Item
    fun getList():LiveData<List<Item>>
}