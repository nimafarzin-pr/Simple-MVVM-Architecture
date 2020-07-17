package com.example.mvvm.viewmodels

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.models.NicePlace
import com.example.mvvm.repositories.NicePlaceRepository


class MainActivityViewModel : ViewModel() {
    //we use MutableLiveData Because we want Change LiveData Again Because LiveData Cant
    //change Spontaneously
    private var mNicePlaces: MutableLiveData<MutableList<NicePlace>>? = null
    private var mRepo: NicePlaceRepository? = null
    private val mIsUpdating:MutableLiveData<Boolean> = MutableLiveData()
    fun init() {
        if (mNicePlaces != null) {
            return
        }
        mRepo = NicePlaceRepository().getInstance()
        mNicePlaces = mRepo!!.getNicePlaces()
    }

    fun addNewValue( nicePlace: NicePlace) {
        mIsUpdating.value = true
        class NullableVoidNullableVoidNullableVoidAsyncTask : AsyncTask<Void?, Void?, Void?>() {
            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                val currentPlaces = mNicePlaces!!.value!!
                currentPlaces.add(nicePlace)
                mNicePlaces!!.postValue(currentPlaces)
                mIsUpdating.postValue(false)
            }

            override fun doInBackground(vararg params: Void?): Void? {
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                return null
            }
        }

        NullableVoidNullableVoidNullableVoidAsyncTask().execute()
    }

    val nicePlaces: LiveData<MutableList<NicePlace>>?
        get() = mNicePlaces

    val isUpdating: LiveData<Boolean>
        get() = mIsUpdating

}