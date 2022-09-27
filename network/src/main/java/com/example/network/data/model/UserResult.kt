package com.example.network.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="tableUser")
data class UserResult(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @SerializedName("bairro")
    var bairro: String,
    @SerializedName("cep")
    var cep: String,
    @SerializedName("cidade")
    var cidade: String,
    @SerializedName("estado")
    var estado: String,
    @SerializedName("logradouro")
    var logradouro: String,
    @SerializedName("name")
    var name:String,
    @SerializedName("phone")
    var phone:String,
    @SerializedName("number")
    var number:String

) : Parcelable