package com.hernelio.appmysqlactivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.hernelio.appmysqlactivity.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var login: TextView
    lateinit var password: TextView
    lateinit var mensaje: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        login=binding.editTextUsername
        password=binding.editTextPassword
        mensaje=binding.mensaje

        setContentView(binding.root)


    }
    fun loginApp(view: View) {
        val username = login.text.toString()
        val pwd = password.text.toString()

        val url = "http://192.168.20.63/recibir/consultarusuario.php?username=$username&pwd=$pwd"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Procesa la respuesta del servidor
                if (response.trim() == "true") {
                    // Usuario y contraseña válidos
                    val intent = Intent(this, MenuAppActivity::class.java)
                    startActivity(intent)
                } else {
                    // Usuario o contraseña incorrectos
                    // Muestra un mensaje de error
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    mensaje.text = response.trim()
                }
            },
            { error ->
                // Maneja errores de la solicitud
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(stringRequest)
    }

    fun crearUsuario(view: View){
        val username = login.text.toString()
        val pwd = password.text.toString()

        if(login.text.toString().length>0 &&
            password.text.toString().length>0 ) {
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.20.63/recibir/crearusuario.php?username=$username&pwd=$pwd"
            val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener<String>
            { response ->
                mensaje.text = "**:" + response.toString()

            }, Response.ErrorListener { mensaje.setText("error") })
            queue.add(stringRequest)
        }
        else{
            mensaje.setText("¡¡¡¡¡INGRESAR DATOS DEL INQUILINO¡")

        }




    }

}