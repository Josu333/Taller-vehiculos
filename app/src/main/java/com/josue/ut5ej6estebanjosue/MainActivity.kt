package com.josue.ut5ej6estebanjosue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.josue.ut5ej6estebanjosue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var usuarios = getUsus()
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAcceso.setOnClickListener{
            acceder()
        }

    }

     fun getUsus(): List<Usuario> {
         val us1 = Usuario("Rep1","123",1)
         val us2 = Usuario("Mec1","456",0)

         val usuarios = listOf(us1,us2)

         return usuarios
    }

    fun acceder (){

        var usu = binding.txtUsuario.text.toString()
        var pass = binding.txtContra.text.toString()

        if(usu.isEmpty() || pass.isEmpty()){

            Snackbar.make(binding.root,"El usuario y la contraseña no pueden estar vacios", Snackbar.LENGTH_SHORT).show()

        }
        else{
            var u = Usuario (usu,pass)
            var pos=0

            pos=usuarios.indexOf(u)

            if(pos !=-1){
                Toast.makeText(this,"Usuario validado. Entrando al sistema", Toast.LENGTH_LONG).show()

                var intent = Intent (this, ActivityRecycler::class.java)
                intent.putExtra("usu", usuarios[pos].perfil)
                startActivity(intent)

            }else{
                Snackbar.make(binding.root,"El usuario no existe en el sistema",Snackbar.LENGTH_SHORT).show()
            }

        }

    }


}