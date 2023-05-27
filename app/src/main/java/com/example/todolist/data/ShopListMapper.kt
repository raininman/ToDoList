package com.example.todolist.data

import com.example.todolist.domain.Item

class ShopListMapper constructor() {

    fun mapEntityToDbModel(item: Item) = ShopItemDbModel(
        id = item.id,
        name = item.name,
        description = item.description,
        enabled = item.enabled
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = Item(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        description = shopItemDbModel.description,
        enabled = shopItemDbModel.enabled
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}
