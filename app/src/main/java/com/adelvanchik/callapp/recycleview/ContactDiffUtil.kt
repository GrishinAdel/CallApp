package com.adelvanchik.callapp.recycleview

import androidx.recyclerview.widget.DiffUtil
import com.adelvanchik.callapp.entity.Contact

class ContactDiffUtil: DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}