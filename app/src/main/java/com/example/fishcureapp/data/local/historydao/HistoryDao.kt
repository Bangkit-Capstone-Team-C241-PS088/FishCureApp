package com.example.fishcureapp.data.local.historydao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fishcureapp.data.local.model.History


@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAllHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: History): Long

    @Query("SELECT * FROM history")
    suspend fun getAllHistorySuspend(): List<History>


}