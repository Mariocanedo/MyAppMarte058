package com.example.myapp58.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapp58.Model.Local.MarsDao
import com.example.myapp58.Model.Remote.MarsRealState
import com.example.myapp58.Model.Remote.RetrofitClient
import retrofit2.Call
import retrofit2.Response
/************ CUANDO QUIERO CREAR LA BASE DE DATOS INSERTO EL DAO EN CONSTRUCTOR*************/
class MarsRepository (private val marsDao: MarsDao) {


    // 1 llamar al método de conexion
    private val retrofitClient = RetrofitClient.getRetrofit()
    //2 HACE REFERENCIA AL POJO Y LA RESPUESTA QUE VAMOS A RECIBIR
    val dataFromInternet = MutableLiveData<List<MarsRealState>>()


    // vieja confiable
    fun fetchDataMars(): LiveData<List<MarsRealState>> {
        Log.d("REPO", "VIEJA CONFIABLE")
        retrofitClient.fetchMarsData().enqueue(object : retrofit2.Callback<List<MarsRealState>> {
            override fun onResponse(
                call: Call<List<MarsRealState>>,
                response: Response<List<MarsRealState>>
            ) {

                when (response.code()) {

                    in 200..299 -> dataFromInternet.value = response.body()
                    in 300..301 -> Log.d("REPO", "${response.code()} ---${response.errorBody()}")
                    else -> Log.d("E", "${response.code()} ---${response.errorBody()}")
                }


            }

            override fun onFailure(call: Call<List<MarsRealState>>, t: Throwable) {
                Log.e("Error", " ${t.message}")
            }

        })

        return dataFromInternet


    }


    // nueva Forma con coroutinas

    suspend fun fetchDataFromInternetCoroutines() {
        try {
            val response = retrofitClient.fetchMarsDataCoroutines()
            when (response.code()) {
                //  in 200..299 -> dataFromInternet.value = response.body()
                in 200..299 -> response?.body().let {
                    if (it != null) {
                        // esta insertando en la base de datos Luego que creo la base de datos
                        marsDao.inserTerrains(it)
                    }
                }

                in 300..301 -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
                else -> Log.d("REPO", "${response.code()} --- ${response.errorBody().toString()}")
            }
        } catch (t: Throwable) {
            Log.e("REPO", "${t.message}")
        }
    }


   /********************+IMPLEMENTACIÓN DE DAO, LUEGO QUE CREO BASE DE DATOS*********/
   /************ si quiero insertar sin base de datos no es necesario *********/


    fun  getTerrainByid(id:String) : LiveData<MarsRealState>{
       return getTerrainByid(id)
    }

    // listado de terrenos
     val lisTAllMars : LiveData<List<MarsRealState>> =marsDao.getAllTerrains()


    // insertar un terreno
    suspend fun inserTerrain(mars: MarsRealState) {
        marsDao.insertTerrain(mars)
    }

    // actualizar un terreno

    suspend fun updateTerrain(mars: MarsRealState) {
             marsDao.updateTerrain(mars)
    }

    // elimina terrenos
    suspend fun deleteAll() {
          marsDao.deleteAll()
    }
    //traer terreno por id

     fun getTerrain(id:Int):LiveData<MarsRealState>{
       return  marsDao.getMarsId(id)
}}







