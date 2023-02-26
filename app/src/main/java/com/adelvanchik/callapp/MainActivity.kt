package com.adelvanchik.callapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.adelvanchik.callapp.databinding.ActivityMainBinding
import com.adelvanchik.callapp.recycleview.ContactListAdapter
import com.bumptech.glide.*
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


class MainActivity : AppCompatActivity() {

    private val vm by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")

    private lateinit var adapter: ContactListAdapter

    private var myLocationOnTheScreenIsTheFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
        clickListeners()
    }

    private fun observer() {
        vm.myMicWork.observe(this) { micWork ->
            val image = if (micWork) R.drawable.ic_mic_on else R.drawable.ic_mic_off
            binding.btnMic.setImageResource(image)
            val image2 = if (micWork) R.drawable.ic_mic_on_white_small
            else R.drawable.ic_mic_off_white_small
            if (myLocationOnTheScreenIsTheFirst) {
                binding.tvNameUser1.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, image2, 0)
            } else {
                binding.tvNameUser2.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, image2, 0)
            }
        }

        vm.secondMicWork.observe(this) { micWork ->
            val image = if (micWork) R.drawable.ic_mic_on_white_small
            else R.drawable.ic_mic_off_white_small
            if (myLocationOnTheScreenIsTheFirst) {
                binding.tvNameUser2.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, image, 0)
            } else {
                binding.tvNameUser1.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, image, 0)
            }
        }

        vm.myVideoCameraWork.observe(this) { videoCameraWork ->
            val image = if (videoCameraWork) R.drawable.ic_videocamera_on
            else R.drawable.ic_videocamera_off
            binding.btnVideoCamera.setImageResource(image)
        }

        vm.myPhotoInCall.observe(this) {
            binding.ivUser1.setImageResource(it)
            Glide.with(this).asBitmap().load(it).into(object : CustomTarget<Bitmap>(1, 1) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.layoutUser1.setImageDrawable(BitmapDrawable(resources, resource))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.layoutUser1.setImageDrawable(null)
                }
            })
        }

        vm.secondPhotoInCall.observe(this) {
            binding.ivUser2.setImageResource(it)
            Glide.with(this).asBitmap().load(it).into(object : CustomTarget<Bitmap>(1, 1) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.layoutUser2.setImageDrawable(BitmapDrawable(resources, resource))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.layoutUser2.setImageDrawable(null)
                }
            })
        }

        vm.myName.observe(this) {
            binding.tvNameUser1.text = resources.getString(it)
        }

        vm.secondName.observe(this) {
            binding.tvNameUser2.text = resources.getString(it)
        }

        vm.myLocationOnTheScreenIsTheFirst.observe(this) {
            myLocationOnTheScreenIsTheFirst = it
        }

    }

    private fun clickListeners() {
        with(binding) {
            btnMic.setOnClickListener {
                vm.changeMyMicMode()
            }
            btnVideoCamera.setOnClickListener {
                vm.changeMyVideoCameraMode()
            }
            btnPhoneDown.setOnClickListener {
                finishAffinity()
            }
            btnHand.setOnClickListener {
                AlertDialog.Builder(this@MainActivity)
                    .setMessage("привет")
                    .create().show()
            }
            btnMessage.setOnClickListener {
                val sendIntent = Intent(Intent.ACTION_VIEW)
                sendIntent.data = Uri.parse("sms: ")
                startActivity(sendIntent);
            }
            btnUsersOutline.setOnClickListener {
                checkPermissionInReadContacts()
            }
            btnChangeUsers.setOnClickListener {
                vm.changeMyLocationOnTheScreen()
            }
        }
    }

    private fun startContactActivity() {
        Toast.makeText(applicationContext, "Загрузка..", Toast.LENGTH_LONG).show()
        startActivity(ContactsActivity.newInstance(applicationContext))
    }

    private fun checkPermissionInReadContacts(){

        if (ActivityCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.READ_CONTACTS),
                MY_REQUEST_CODE)

        } else {
            startContactActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ActivityCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
        ) {
            startContactActivity()
        }
    }


    companion object {
        private const val MY_REQUEST_CODE = 0
    }


}