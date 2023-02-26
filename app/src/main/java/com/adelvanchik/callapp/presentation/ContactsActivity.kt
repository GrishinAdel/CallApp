package com.adelvanchik.callapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.callapp.databinding.ActivityContactsBinding
import com.adelvanchik.callapp.presentation.recycleview.ContactListAdapter


class ContactsActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProvider(this)[ContactViewModel::class.java]}

    private var _binding: ActivityContactsBinding? = null
    private val binding: ActivityContactsBinding
        get() = _binding ?: throw RuntimeException("ActivityContactsBinding == null")

    private lateinit var adapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        clickListener()
        observer()
    }

    private fun clickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = ContactListAdapter()
        binding.rvContacts.adapter = adapter
    }

    private fun observer() {
        vm.contactsList.observe(this) {
            adapter.submitList(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance(context: Context): Intent {
            return Intent(context, ContactsActivity::class.java)
        }
    }

}