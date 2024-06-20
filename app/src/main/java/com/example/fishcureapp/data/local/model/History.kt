package com.example.fishcureapp.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "history")
@Parcelize
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "disease_name")
    val disease_name: String? = null,

    @ColumnInfo(name = "akurasi")
    val akurasi: Double? = null,

    @ColumnInfo(name = "obat")
    val obat: String? = null,

    @ColumnInfo(name = "symptom")
    val symptom: String? = null,

    @ColumnInfo(name = "penanganan")
    val penanganan: String? = null,

    @ColumnInfo(name = "perawatan")
    val perawatan: String? = null,

    @ColumnInfo(name = "date")
    val date: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null
) : Parcelable