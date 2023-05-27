package com.example.todolist.domain

data class Item(
    val name: String,
    val description: String,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}