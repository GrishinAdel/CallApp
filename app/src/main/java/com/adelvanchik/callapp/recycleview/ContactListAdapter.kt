package com.adelvanchik.callapp.recycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.adelvanchik.callapp.R
import com.adelvanchik.callapp.entity.Contact

class ContactListAdapter : ListAdapter<Contact, ContactViewHolder>(ContactDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_pattern_contact,
            parent,
            false
        )
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        with(holder) {
            val contact = getItem(position)
            with(holder) {
                tvContactName.text = contact.name
                tvContactNumber.text = contact.number
               if (contact.icon !=null) {
                   ivContactIcon.setImageBitmap(contact.icon)
                } else {
                   ivContactIcon.setImageResource(R.drawable.ic_contact_default)
               }
            }
        }
    }
}