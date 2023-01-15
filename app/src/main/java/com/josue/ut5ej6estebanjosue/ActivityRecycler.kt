package com.josue.ut5ej6estebanjosue

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.josue.ut5ej6estebanjosue.databinding.ActivityRecyclerBinding
import com.josue.ut5ej6estebanjosue.databinding.ItemVehiculoBinding

class ActivityRecycler : AppCompatActivity(), Eventos {
    lateinit var binding2: ActivityRecyclerBinding

    var perfil : Int = 0


    lateinit var linearLayout: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_recycler)
        binding2 = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding2.root)


        //Pasamos el perfil de usuario para habilitar unas opciones u otras
        var i = intent.extras!!
        this.perfil=i.getInt("usu")
        filtroUsuario(this.perfil)
        initRecycler()

        //Evento clic botón para agregar un vehiculo
        binding2.FAB.setOnClickListener {
            var intentIn = Intent (this, ActivityInsertar::class.java)
            resultadoInsertar.launch(intentIn)
        }

        /*binding2.FAB.setOnClickListener {
            accesoInsertar()
        }*/
    }

    //variable del 'launch' de nuestro intent que guardará la información en caso de añadirse correctamente un vehiculo
    val resultadoInsertar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        resultado->
        var datos:Intent? =resultado.data
        if(resultado.resultCode==Activity.RESULT_OK){

            if(datos?.getBooleanExtra("insertado", false)!!){
                binding2.recyclerInfo.adapter?.notifyDataSetChanged()
            }

        }else{
            Toast.makeText(this, "Se volvió sin añadir nigún vehiculo", Toast.LENGTH_LONG).show()
        }


    }

    private fun filtroUsuario(perfil: Int) {
        if(perfil==0){
            binding2.FAB.hide()
        }
    }

    fun initRecycler(){

        binding2.recyclerInfo.adapter = AdaptadorRV(ListaVehiculos.vehiculoList,this)
        linearLayout=LinearLayoutManager(this )//desplazamiento vertical
        binding2.recyclerInfo.layoutManager=linearLayout
        binding2.recyclerInfo.setHasFixedSize(true)

    }

    //Recepcionistas
    override fun pulsacionCorta( posicion:Int) {
        if(this.perfil==1){

            var vehicul: Vehiculo = ListaVehiculos.vehiculoList[posicion]
            if(vehicul.estado==true){
                if(ListaVehiculos.eliminar(posicion)){
                    Toast.makeText(this, "Vehiculo eliminado",
                        Toast.LENGTH_SHORT).show()
                    binding2.recyclerInfo.adapter?.notifyDataSetChanged()
                }

            }else{
                Toast.makeText(this, "Este vehiculo no  está listo",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Mecánicos
    override fun pulsacionLarga( posicion: Int): Boolean {
        if(this.perfil==0){
            if(ListaVehiculos.vehiculoList[posicion].estado==false){
                val alerta = AlertDialog.Builder(this)
                alerta.setTitle("Confirmar")
                alerta.setMessage("¿Desea confirmar que el vehiculo ya está listo?")
                alerta.setPositiveButton("Sí", { _,_-> ListaVehiculos.vehiculoList[posicion].estado=true
                enviarEmail(posicion)})
                alerta.setNegativeButton("No",{_,_-> })
                alerta.create().show()

            }else{
                Snackbar.make(binding2.root,"El vehículo ya se encuentra disponible",Snackbar.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun enviarEmail(posicion:Int) {
        var emailIntent = Intent (Intent.ACTION_SEND)
        emailIntent.setData( Uri.parse(ListaVehiculos.vehiculoList[posicion].email))
        emailIntent.setType("text/plain")

        emailIntent.putExtra(Intent.EXTRA_EMAIL,ListaVehiculos.vehiculoList[posicion].email)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Vehiculo disponible en taller J.E.O")
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Puede pasar a recoger su vehiculo con matricula " +
                "${ListaVehiculos.vehiculoList[posicion].matricula}." + " Gracias.")

        startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }

    /*
     private fun accesoInsertar(){

        var intentInsertar = Intent (this, ActivityInsertar::class.java)
        intent.putExtra("vehicles", listaVs)
        resultadoActividad.launch(intentInsertar)

    }
    var resultadoActividad =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            resultado ->
            if(resultado.resultCode == Activity.RESULT_OK){

            }
        }
        */

    /*override fun onBackPressed() {
        super.onBackPressed()
    }*/

}