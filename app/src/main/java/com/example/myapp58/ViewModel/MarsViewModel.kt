package com.example.myapp58.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapp58.Model.Remote.MarsRealState
import com.example.myapp58.Model.Remote.MarsRepository

class MarsViewModel (application: Application): AndroidViewModel(application){


    // parte 1 comunicar con el repository

    private val repository : MarsRepository

    // parte 2
   var  liveDatafromInternet :LiveData<List<MarsRealState>>

    init {


       repository= MarsRepository()
        liveDatafromInternet = repository.fetchDataMars()

    }

}