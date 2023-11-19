package com.hernelio.appmysqlactivity

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.hernelio.appmysqlactivity.databinding.ActivityVehiculoCrudBinding

class VehiculoCrudActivity : AppCompatActivity() {
    lateinit var matricula: TextView
    lateinit var color: TextView
    lateinit var marca: TextView
    lateinit var npuertas: TextView
    lateinit var mensaje: TextView
    lateinit var codigo: EditText
    lateinit var cod: EditText
    private lateinit var spinnerCliente: Spinner
    private val idClienteList: MutableList<Int> = mutableListOf()

    private lateinit var binding: ActivityVehiculoCrudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityVehiculoCrudBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_vehiculo_crud)
        matricula=binding.Matricula
        color=binding.Color
        marca=binding.Marca
        npuertas=binding.Npuerta
        mensaje=binding.mensaje
        codigo=binding.Codigo
        spinnerCliente=binding.spinnerCliente

        // Inicializa la lista de IDs de clientes y el adaptador del Spinner
        var idClienteList: MutableList<String> = mutableListOf()
        val clienteAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, idClienteList)
        clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCliente.adapter = clienteAdapter

        // Realiza la consulta para obtener IDs de clientes desde el servidor
        val urlConsultaClientes = "http://192.168.20.63/recibir/consultacliente.php"
        val queue = Volley.newRequestQueue(this)
        val idClienteRequest = StringRequest(Request.Method.GET, urlConsultaClientes,
            { response ->
                // Procesa la respuesta como una cadena y divide los IDs
                val idsClientes: List<String> = response.split(",")
                // Agrega los IDs de clientes a la lista y notifica al adaptador
                idClienteList.addAll(idsClientes)
                clienteAdapter.notifyDataSetChanged()
            },
            { error ->
                // Maneja errores de la solicitud
                mensaje.text = "Error al obtener IDs de clientes: ${error.message}"
            })

        // Agrega la solicitud a la cola de solicitudes
        queue.add(idClienteRequest)


        setContentView(binding.root)

    }
    // Función para guardar datos del vehículo
    fun creaVehiculo(view: View) {
        // Obtener valores de los campos y el ID del cliente seleccionado
        val matriculavalue = matricula.text.toString()
        val colorvalue = color.text.toString()
        val marcavalue = marca.text.toString()
        val npuertasvalue = npuertas.text.toString()
        val id_cliente = spinnerCliente.selectedItem.toString()

        if(matriculavalue.length>0 &&
            colorvalue.length>0 &&
            marcavalue.length>0 &&
            npuertasvalue.length>0 ) {

            // Realizar la solicitud para guardar el vehículo en el servidor
            val urlGuardarVehiculo = "http://192.168.20.63/recibir/creavehiculo.php" +
                    "?matricula=$matriculavalue" +
                    "&color=$colorvalue" +
                    "&marca=$marcavalue" +
                    "&npuertas=$npuertasvalue" +
                    "&id_cliente=$id_cliente"

            val queue = Volley.newRequestQueue(this)
            val guardarVehiculoRequest = StringRequest(Request.Method.GET, urlGuardarVehiculo,
                { response ->
                    // Manejar la respuesta del servidor
                    mensaje.text = response
                    leerDatosVehiculo(view)
                },
                { error ->
                    // Manejar errores de la solicitud
                    mensaje.text = "Error al guardar el vehículo: ${error.message}"
                })

            // Agregar la solicitud a la cola de solicitudes
            queue.add(guardarVehiculoRequest)
        }
        else{
            mensaje.setText("¡¡¡¡¡INGRESAR DATOS DEL VEHICULO¡")
        }
        matricula.setText("")
        color.setText("")
        npuertas.setText("")
        marca.setText("")
        matricula.requestFocus()
    }
    // Función para leer datos del vehículo
    fun leerDatosVehiculo(view: View) {
        val url = "http://192.168.20.63/recibir/consultavehiculo.php"

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
    // Función para actualizar datos del vehículo
    fun actualizaCliente(view: View) {
        val idVehiculo = codigo.text.toString()
        val matriculavalue = matricula.text.toString()
        val colorvalue = color.text.toString()
        val marcavalue = marca.text.toString()
        val npuertasvalue = npuertas.text.toString()
        val id_cliente = spinnerCliente.selectedItem.toString()

        if (idVehiculo.isNotEmpty() && matriculavalue.isNotEmpty() && colorvalue.isNotEmpty() && marcavalue.isNotEmpty() && npuertasvalue.isNotEmpty()) {
            val url = "http://192.168.20.63/recibir/actualizavehiculo.php?id=$idVehiculo&matricula=$matriculavalue&color=$colorvalue&marca=$marcavalue&npuertas=$npuertasvalue&id_cliente=$id_cliente"

            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String> { response ->
                mensaje.text = response.toString()

            }, Response.ErrorListener { error ->
                mensaje.text = "Error al actualizar el cliente: $error"
            })

            queue.add(stringRequest)
        } else {
            mensaje.text = "Por favor, ingrese todos los campos."
        }
        matricula.setText("")
        marca.setText("")
        color.setText("")
        npuertas.setText("")
        matricula.requestFocus()
    }
}