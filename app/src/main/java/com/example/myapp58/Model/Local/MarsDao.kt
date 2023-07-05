package com.example.myapp58.Model.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapp58.Model.Remote.MarsRealState

@Dao
interface MarsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertTerrain(mars: MarsRealState)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserTerrains(mars: List<MarsRealState>)



    @Update
    suspend fun updateTerrain(mars:MarsRealState)

    @Delete
    suspend fun  deleteTerrain(mars:MarsRealState)


    @Query("DELETE FROM mars_table")
    suspend fun deleteAll()


    // traer todos los terrenos

    @Query("SELECT * FROM mars_table ORDER BY id DESC")
    fun getAllTerrains(): LiveData<List<MarsRealState>>


    @Query("SELECT * FROM mars_table WHERE type=:type Limit 1")
    fun getMarsType( type: String): LiveData<MarsRealState>


    @Query("SELECT * FROM mars_table WHERE id=:id")
    fun getMarsId(id:Int): LiveData<MarsRealState>

}