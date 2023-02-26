package com.adelvanchik.callapp

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _myMicWork = MutableLiveData<Boolean>()
    val myMicWork: LiveData<Boolean>
        get() = _myMicWork

    private val _myVideoCameraWork = MutableLiveData<Boolean>()
    val myVideoCameraWork: LiveData<Boolean>
        get() = _myVideoCameraWork

    private val _secondMicWork = MutableLiveData<Boolean>()
    val secondMicWork: LiveData<Boolean>
        get() = _secondMicWork

    private val _myLocationOnTheScreenIsTheFirst = MutableLiveData<Boolean>()
    val myLocationOnTheScreenIsTheFirst: LiveData<Boolean>
        get() = _myLocationOnTheScreenIsTheFirst

    private val _myPhotoInCall = MutableLiveData<Int>()
    val myPhotoInCall: LiveData<Int>
        get() = _myPhotoInCall

    private val _secondPhotoInCall = MutableLiveData<Int>()
    val secondPhotoInCall: LiveData<Int>
        get() = _secondPhotoInCall

    private val _myName = MutableLiveData<Int>()
    val myName: LiveData<Int>
        get() = _myName


    private val _secondName = MutableLiveData<Int>()
    val secondName: LiveData<Int>
        get() = _secondName


    init {
        _myMicWork.value = false
        _myVideoCameraWork.value = false
        _secondMicWork.value = false
        _myLocationOnTheScreenIsTheFirst.value = true
        _myPhotoInCall.value = R.drawable.user_image_1
        _secondPhotoInCall.value = R.drawable.user_image_2
        _myName.value = R.string.you
        _secondName.value = R.string.name_user_2
    }

    fun changeMyMicMode() {
        _myMicWork.value = myMicWork.value != true
    }

    fun changeMyVideoCameraMode() {
        _myVideoCameraWork.value = myVideoCameraWork.value != true
    }

    fun changeMyLocationOnTheScreen() {
        _myLocationOnTheScreenIsTheFirst.value = myLocationOnTheScreenIsTheFirst.value != true

        val imageInt = myPhotoInCall.value
        _myPhotoInCall.value = secondPhotoInCall.value
        _secondPhotoInCall.value = imageInt

        val stringId = myName.value
        _myName.value = secondName.value
        _secondName.value = stringId

        _myMicWork.value = myMicWork.value
        _secondMicWork.value = secondMicWork.value
    }

}