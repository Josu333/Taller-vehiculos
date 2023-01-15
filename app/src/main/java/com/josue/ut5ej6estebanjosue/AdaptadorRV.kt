package com.josue.ut5ej6estebanjosue

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.josue.ut5ej6estebanjosue.databinding.ItemVehiculoBinding
import java.text.FieldPosition

class AdaptadorRV(private var listaV: MutableList<Vehiculo>, private val listener: Eventos ):
    RecyclerView.Adapter<AdaptadorRV.MiViewHolder>() {

    private lateinit var contexto: Context

    inner class MiViewHolder(view: View):RecyclerView.ViewHolder(view){
        //inflamos los items que compondran el recycler
        val binding = ItemVehiculoBinding.bind(view)

        fun setListener( posicion: Int){
            binding.root.setOnClickListener{
                listener.pulsacionCorta(posicion)
            }
            binding.root.setOnLongClickListener{
                listener.pulsacionLarga(posicion)
            }
        }

    }

    //me devuelve un dato de tipo ViewHolder; aquí lo creas
    //inflar en este contexto e indicar el diseño de cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiViewHolder {
        contexto = parent.context

        val layoutInflater = LayoutInflater.from(contexto)
        return MiViewHolder(layoutInflater.inflate(R.layout.item_vehiculo, parent, false))
    }

    //aqui me lo muestra; se indica con que elemento estoy trabajando
    override fun onBindViewHolder(holder: MiViewHolder, position: Int) {
        val vehiculo = listaV[position]

        with(holder){
            binding.dni.setText(vehiculo.dni)//java
            binding.nombre.setText(vehiculo.nombre)//kotlin
            binding.matri.setText(vehiculo.matricula)
            binding.modelo.setText(vehiculo.modelo)

            setListener(position)//pulsacionCorta_y_Larga

            if(vehiculo.estado==true){
                binding.itemVehiculo.setBackgroundColor(Color.GREEN)
            }else{
                binding.itemVehiculo.setBackgroundColor(Color.YELLOW)
            }

        }


    }

    override fun getItemCount()=listaV.size
    
}