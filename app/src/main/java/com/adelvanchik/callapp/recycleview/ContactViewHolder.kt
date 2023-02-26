package com.adelvanchik.callapp.recycleview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adelvanchik.callapp.R

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvContactName: TextView = view.findViewById(R.id.tv_contact_name)
    val tvContactNumber: TextView = view.findViewById(R.id.tv_contact_number)
    val ivContactIcon: ImageView = view.findViewById(R.id.iv_contact_icon)
}