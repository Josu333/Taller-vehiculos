package com.josue.ut5ej6estebanjosue

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Vehiculo (val dni:String, val nombre:String,var email:String, val matricula:String, val modelo:String, var fechaEntrega:String="",
var observaciones:String="", var estado:Boolean=false): Parcelable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vehiculo

        if (matricula != other.matricula) return false

        return true
    }

    override fun hashCode(): Int {
        return matricula.hashCode()
    }

}

