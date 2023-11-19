package com.hernelio.appmysqlactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.hernelio.appmysqlactivity.databinding.ActivityTrabajoCrudBinding

class TrabajoCrudActivity : AppCompatActivity() {
    lateinit var nombre: TextView
    lateinit var mensaje: TextView
    lateinit var codigo: EditText
    lateinit var cod: EditText
    private lateinit var binding: ActivityTrabajoCrudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityTrabajoCrudBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_trabajador_crud)
        nombre=binding.Nombre
        mensaje=binding.mensaje
        codigo=binding.Codigo
        // cod=binding.Cod
        setContentView(binding.root)
    }
    fun crearDatos(view: View){

        if(nombre.text.toString().length>0) {
            val url = "http://192.168.20.63/recibir/creatrabajo.php?" +
                    "nombre=${nombre.text.toString()}"

            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>
            { response ->
                mensaje.text = "**:" + response.toString()
                leerDatosTrabajo(view)
            }, Response.ErrorListener { mensaje.setText("error") })
            queue.add(stringRequest)



        }
        else{
            mensaje.setText("¡¡¡¡¡INGRESAR DATOS DEL INQUILINO¡")
            nombre.requestFocus()
        }
        nombre.setText("")
        nombre.requestFocus()



    }
    fun leerDatosTrabajo(view: View) {
        val url = "http://192.168.20.63/recibir/consultatrabajo.php"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>
        { response ->
            if (response.isEmpty()) {
                mensaje.text = "No se encontraron datos."
            } else {
                mensaje.text = response.toString()
            }
        }, Response.ErrorListener {
            mensaje.text = "Error al cargar los datos."
        })

        queue.add(stringRequest)
    }

    fun actualizaTrabajo(view: View) {
        val idCliente = codigo.text.toString()
        val nuevoNombre = nombre.text.toString()

        if (idCliente.isNotEmpty() && nuevoNombre.isNotEmpty() ) {
            val url = "http://192.168.20.63/recibir/actualizatrabajo.php?id=$idCliente&nombre=$nuevoNombre"

            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                mensaje.text = response.toString()
                leerDatosTrabajo(view)
            }, Response.ErrorListener { error ->
                mensaje.text = "Error al actualizar el cliente: $error"
            })

            queue.add(stringRequest)
        } else {
            mensaje.text = "Por favor, ingrese todos los campos."
        }
        nombre.setText("")
        nombre.requestFocus()
    }
    fun eliminaTrabajo(view: View) {
        val idCliente = codigo.text.toString()

        if (idCliente.isNotEmpty()) {
            val url = "http://192.168.20.63/recibir/eliminatrabajo.php?id=$idCliente"

            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                mensaje.text = response.toString()
                leerDatosTrabajo(view)
            }, Response.ErrorListener { error ->
                mensaje.text = "Error al eliminar el Trabajo: $error"
            })

            queue.add(stringRequest)
        } else {
            mensaje.text = "Por favor, ingrese el ID del Trabajo que desea eliminar."
        }
    }
}