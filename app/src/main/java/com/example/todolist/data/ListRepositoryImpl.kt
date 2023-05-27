package com.example.todolist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.todolist.domain.Item
import com.example.todolist.domain.ListRepository

class ListRepositoryImpl(
    application: Application
) : ListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()
    private val mapper = ShopListMapper()

    override suspend fun addItem(item: Item) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun deleteItem(item: Item) {
        shopListDao.deleteShopItem(item.id)
    }

    override suspend fun editItem(item: Item) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getItem(itemId: Int): Item {
        val dbModel = shopListDao.getShopItem(itemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getList(): LiveData<List<Item>> = shopListDao.getShopList().map(
    ) {
        mapper.mapListDbModelToListEntity(it)
    }
}