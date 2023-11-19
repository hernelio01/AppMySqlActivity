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

import com.hernelio.appmysqlactivity.databinding.ActivityTrabajadorCrudBinding

class TrabajadorCrudActivity : AppCompatActivity() {
    lateinit var nombre: TextView
    lateinit var apellido: TextView
    lateinit var celular: TextView
    lateinit var ident: TextView
    lateinit var mensaje: TextView
    lateinit var codigo: EditText
    lateinit var cod: EditText

    private lateinit var binding: ActivityTrabajadorCrudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityTrabajadorCrudBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_trabajador_crud)
        nombre=binding.Nombre
        apellido=binding.Apellido
        celular=binding.Celular
        ident=binding.Ident
        mensaje=binding.mensaje
        codigo=binding.Codigo
        // cod=binding.Cod
        setContentView(binding.root)
    }
    fun crearDatos(view: View){
        // val url ="http://192.168.229.213/recibir/creatrabajador.php?nombre="+nombre.text.toString()
        if(nombre.text.toString().length>0 &&
            apellido.text.toString().length>0 &&
            celular.text.toString().length>0 &&
            ident.text.toString().length>0 ) {
            val url = "http://192.168.20.63/recibir/creatrabajador.php?" +
                    "nombre=${nombre.text.toString()}" +
                    "&apellido=${apellido.text.toString()}" +
                    "&identificacion=${ident.text.toString()}" +
                    "&celular=${celular.text.toString()}"
            //val url ="http://192.168.229.213/aplicacion/consulta.php";
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>
            { response ->
                mensaje.text = "**:" + response.toString()
                leerDatosTrabajador(view)
            }, Response.ErrorListener { mensaje.setText("error") })
            queue.add(stringRequest)



        }
        else{
            mensaje.setText("¡¡¡¡¡INGRESAR DATOS DEL INQUILINO¡")
            nombre.requestFocus()
        }
        nombre.setText("")
        apellido.setText("")
        celular.setText("")
        ident.setText("")
        nombre.requestFocus()



    }
    fun leerDatosTrabajador(view: View) {
        val url = "http://192.168.20.63/recibir/consultatrabajador.php"

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

    fun actualizaTrabajador(view: View) {
        val idCliente = codigo.text.toString()
        val nuevoNombre = nombre.text.toString()
        val nuevoApellido = apellido.text.toString()
        val nuevaIdentificacion = ident.text.toString()
        val nuevoCelular = celular.text.toString()

        if (idCliente.isNotEmpty() && nuevoNombre.isNotEmpty() && nuevoApellido.isNotEmpty() && nuevaIdentificacion.isNotEmpty() && nuevoCelular.isNotEmpty()) {
            val url = "http://192.168.20.63/recibir/actualizatrabajador.php?id=$idCliente&nombre=$nuevoNombre&apellido=$nuevoApellido&identificacion=$nuevaIdentificacion&celular=$nuevoCelular"

            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                mensaje.text = response.toString()
                leerDatosTrabajador(view)
            }, Response.ErrorListener { error ->
                mensaje.text = "Error al actualizar el cliente: $error"
            })

            queue.add(stringRequest)
        } else {
            mensaje.text = "Por favor, ingrese todos los campos."
        }
        nombre.setText("")
        apellido.setText("")
        celular.setText("")
        ident.setText("")
        nombre.requestFocus()
    }
    fun eliminaTrabajador(view: View) {
        val idCliente = codigo.text.toString()

        if (idCliente.isNotEmpty()) {
            val url = "http://192.168.20.63/recibir/eliminatrabajador.php?id=$idCliente"

            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                mensaje.text = response.toString()
                leerDatosTrabajador(view)
            }, Response.ErrorListener { error ->
                mensaje.text = "Error al eliminar el cliente: $error"
            })

            queue.add(stringRequest)
        } else {
            mensaje.text = "Por favor, ingrese el ID del cliente que desea eliminar."
        }
    }
}