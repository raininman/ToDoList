package com.example.todolist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.todolist.R
import com.example.todolist.domain.Item

class ListAdapter :
    androidx.recyclerview.widget.ListAdapter<Item, ListItemViewHolder>(ItemDiffCallback()) {

    var onItemLongClickListener:((Item)-> Unit)? =null
    var onItemClickListener:((Item)-> Unit)? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val layout = when(viewType){
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            else -> throw RuntimeException("Unknown viewType: $viewType")

        }
        val view =  LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ListItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ListItemViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.tvName.text = item.name
        viewHolder.tvDescription.text = item.description
        viewHolder.view.setOnLongClickListener {
            onItemLongClickListener?.invoke(item)
            true
        }
        viewHolder.view.setOnClickListener {
            onItemClickListener?.invoke(item)
            true
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return if(item.enabled){
            VIEW_TYPE_ENABLED
        }else{
            VIEW_TYPE_DISABLED
        }
    }



    companion object{
        const val VIEW_TYPE_ENABLED=1
        const val VIEW_TYPE_DISABLED=0
        const val MAX_POOL_SIZE=15
    }
}