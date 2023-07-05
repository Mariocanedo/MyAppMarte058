package com.example.myapp58.Model.Remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response

class MarsRepository {


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

    suspend fun fetchDataFromInternetCoroutines(){
        try {
            val response = retrofitClient.fetchMarsDataCoroutines()
            when (response.code()) {
                //  in 200..299 -> dataFromInternet.value = response.body()
                in 200..299 -> response.body().let{
                   // if (it != null) {
                   // insetar datos
                  //  }

                }
                in 300..301 -> Log.d("REPO", "${response.code()} --- ${response.errorBody()}")
                else -> Log.d("REPO", "${response.code()} --- ${response.errorBody().toString()}")
            }
        } catch (t: Throwable) {
            Log.e("REPO", "${t.message}")
        }
    }


}