package com.example.myapp58.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp58.Model.Local.MarsDataBase
import com.example.myapp58.Model.Remote.MarsRealState
import com.example.myapp58.Model.MarsRepository
import kotlinx.coroutines.launch

class MarsViewModel (application: Application): AndroidViewModel(application) {


    // parte 1 comunicar con el repository

    private val repository: MarsRepository

    // parte 2
     lateinit var liveDatafromInternet: LiveData<List<MarsRealState>>

    // para mostrar todas las tareas   parte 2

    val allTerrains: LiveData<List<MarsRealState>>

    init {

        //parte 1 funciona sin el dao
       // repository= MarsRepository()
       // liveDatafromInternet = repository.fetchDataMars()


        // parte 2
       val MarsDao = MarsDataBase.getDataBase(application).getMarsDao()
       repository = MarsRepository(MarsDao)

        // parte 2 con corrutinas y viewmodel

         viewModelScope.launch {

          repository.fetchDataFromInternetCoroutines()
      }
        // cierre parte 2

        allTerrains = repository.lisTAllMars

        /**********parte 1***********************/
        liveDatafromInternet = repository.dataFromInternet

    }



    //******************************Estos métodos son con el dao****************/
    // funcion para seleccionar
    // guardar la seleccion en una mutableLiveDATA

    private var selectedMarsTerrains: MutableLiveData<MarsRealState> = MutableLiveData()

    fun selected(mars: MarsRealState) {
        selectedMarsTerrains.value = mars
    }

    fun selectedItem(): LiveData<MarsRealState> = selectedMarsTerrains


    fun inserTerrain(mars: MarsRealState) = viewModelScope.launch {

        repository.inserTerrain(mars)
    }


    fun updateTerrain(mars: MarsRealState) = viewModelScope.launch {
        repository.updateTerrain(mars)
    }

   fun getTaskById(id:Int): LiveData<MarsRealState>{
        return  repository.getTerrain(id)
    }
}

