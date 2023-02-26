package com.adelvanchik.callapp.presentation

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adelvanchik.callapp.entity.Contact
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>>
        get() = _contactsList

    private val context by lazy {
        application.applicationContext
    }

    private val contentResolver by lazy {
        application.contentResolver
    }

    init {
        loadContactsInLiveData()
    }

    private fun loadContactsInLiveData() {

        viewModelScope.launch {
            val contactList = mutableListOf<Contact>()
            val phones = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")

            while (phones!!.moveToNext()) {
                val name =
                    phones.getString(phones.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactId =
                    phones.getLong(phones.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))

                val icon = loadContactPhoto(context.contentResolver, contactId)

                val phoneNumber =
                    phones.getString(phones.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))

                val contact = Contact(name = name, number = phoneNumber, icon = icon)
                contactList.add(contact)

            }
            _contactsList.postValue(contactList)
            phones.close()
        }
    }

    private fun loadContactPhoto(cr: ContentResolver, id: Long): Bitmap? {
        val uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id)
        val input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri)
            ?: return null
        return BitmapFactory.decodeStream(input)
    }
}