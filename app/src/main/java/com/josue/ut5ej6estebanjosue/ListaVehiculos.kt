package com.josue.ut5ej6estebanjosue

import android.os.Parcelable
import android.widget.Toast
import kotlinx.parcelize.Parcelize


class ListaVehiculos   {

    companion object{

        var cochePrueba : Vehiculo = Vehiculo ("12345678A","Josue","josue123@gmail.com","0000ABC","Focus")
        var cochePrueba2 : Vehiculo = Vehiculo("00011122B", "Paula","pau465@gmail.com", "555ZZZ", "A3")
        var vehiculoList = mutableListOf(cochePrueba, cochePrueba2)

        fun anadirV (vehicu:Vehiculo){

            vehiculoList.add(vehicu)

        }

        fun buscarV(vehicu: Vehiculo): Boolean{
            var existe = true
            var pos = vehiculoList.indexOf(vehicu)
            if(pos == -1){
                existe = false
            }
            return existe
        }

        fun eliminar(pos:Int):Boolean{
            var borrado = false
            if(vehiculoList.size !=0){
                vehiculoList.removeAt(pos)
                borrado=true
            }

            return borrado
        }

    }


}