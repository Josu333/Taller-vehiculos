package com.josue.ut5ej6estebanjosue

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.josue.ut5ej6estebanjosue.databinding.ActivityInsertarBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern

class ActivityInsertar : AppCompatActivity() {
    lateinit var binding: ActivityInsertarBinding

    //lateinit var listaVecs : ListaVehiculos
    lateinit var vehicu: Vehiculo
    private var i = Intent()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegis.setOnClickListener {
            validarDatosVehiculo()
        }
        binding.btnCancelar.setOnClickListener {
            finish()
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validarDatosVehiculo() {
        var dni = binding.editDni.text.toString()
        var nombreApellidos = binding.editNombre.text.toString()
        var email = binding.editEmail.text.toString()
        var matricula = binding.editMatricula.text.toString()
        var modelo = binding.editModelo.text.toString()
        var fechaEntrada = binding.editFecha.text.toString()
        var observaciones = binding.editObservaciones.text.toString()

        var boolDni: Boolean = esDniValido()
        var boolNombre: Boolean = esNombreApellidosValido()
        var boolEmail: Boolean = esCorreoValido()
        var boolFecha: Boolean = comprobarFecha()

        //Comprobamos los campos para ver si todos están correctos
        if (boolDni && boolNombre && boolEmail && !matricula.isEmpty() && !modelo.isEmpty() &&
            boolFecha && !observaciones.isEmpty())
         {
            Snackbar.make(binding.root, "Datos validados", Snackbar.LENGTH_SHORT).show()
            //Proseguimos con la función de añadir vehiculo, en la que hay que controlar que no exista ya uno con misma matricula
            vehicu = Vehiculo(
                dni,
                nombreApellidos,
                email,
                matricula,
                modelo,
                fechaEntrada,
                observaciones
            )
            var existeV = ListaVehiculos.buscarV(vehicu)
            if (!existeV) {
                ListaVehiculos.anadirV(vehicu)
                Snackbar.make(binding.root, "Vehiculo añadido", Snackbar.LENGTH_SHORT).show()
                back()
            } else {
                Snackbar.make(
                    binding.root,
                    "El vehiculo ya existe en el sistema",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        } else {
           var msn= Toast.makeText(
                this,
                "Algún dato no es válido. Comprubelos para proseguir.",
                Toast.LENGTH_SHORT
            )
            msn.setGravity(Gravity.CENTER,0,0)
            msn.show()
        }

    }

    private fun back() {

        setResult(Activity.RESULT_OK, i)
        i.putExtra("insertado", true)
        finish()
    }

    fun esCorreoValido(): Boolean {
        var correcto = false
        var correo = binding.editEmail.text.toString()
        if (correo.isEmpty()) {
            Toast.makeText(this, "El campo email no puede estar vacío", Toast.LENGTH_SHORT).show()
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                binding.editEmail.setError("Correo no válido")
                return false
            } else {
                binding.editEmail.setError(null)
                return true
            }
        }
        return correcto
    }

    //Método con un patrón para controlar que sólo se introducen letras
    fun esNombreApellidosValido(): Boolean {
        var nombreApe = binding.editNombre.text.toString()
        var valido = true
        var patron: Pattern = Pattern.compile("^[a-zA-Z]+$")
        var nombre: Boolean = patron.matcher(nombreApe).matches()

        if(nombreApe.isEmpty()){
            Toast.makeText(this, "El campo nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
        }else {
            if (!nombre) {
                binding.editNombre.setError("Debe escribir un nombre completo válido")
                valido = false
            }
        }
        return valido
    }

    /*Método para comprobar que el formato de la fecha sea correcto y
    que sea igual o posterior a la actual*/
    @RequiresApi(Build.VERSION_CODES.O)
    fun comprobarFecha(): Boolean {
        var campoFecha = binding.editFecha.text.toString()
        var valida = false
        val formato = SimpleDateFormat("dd/MM/yyyy")
        var fechaActual = formato.format(Date())



        if(campoFecha.isEmpty()){
            binding.editFecha.setError( "El campo fecha no puede estar vacío")
        }else {
            try {
                //var campoFecha2: String? = formato.format(campoFecha)

                var fecha1 = formato.parse(campoFecha)
                var fecha2 = formato.parse(fechaActual)
                if (fecha1.compareTo(fecha2) == 0 || fecha1.compareTo(fecha2) > 0 ) {
                    valida = true
                } else {
                    binding.editFecha.setError("Fecha no válida")
                }


            }catch (e: Exception){
                binding.editFecha.setError("Error de formato de fecha")
            }


        }

        return valida
    }


    //Método con una variable tipo 'Patrón' para controlar que cumple precisamente con un patrón
    //la información introducida en ese campo (técnicamente es un patrón de 8 númeñros y una letra)
    fun esDniValido(): Boolean {
        var campoDNI = binding.editDni.text.toString()
        var valido = true
        var patron2: Pattern = Pattern.compile("[0-9]{7,8}[A-Z a-z]")
        var DNI = patron2.matcher(campoDNI).matches()

        if(campoDNI.isEmpty()){
            Toast.makeText(this, "El campo DNI no puede estar vacío", Toast.LENGTH_SHORT).show()
        }else {
            if (!DNI) {
                binding.editDni.setError("El formato de DNI no es correcto")
                valido = false
            }
        }
        return valido
    }

    


}