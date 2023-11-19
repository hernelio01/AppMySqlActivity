package com.hernelio.appmysqlactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hernelio.appmysqlactivity.databinding.ActivityMenuAppBinding


class MenuAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMenuAppBinding.inflate(layoutInflater)
       // setContentView(R.layout.activity_menu_app)
        setContentView(binding.root)

        binding.btnOso.setOnClickListener {


            val intent = Intent(this, ClienteCrudActivity::class.java)
            // intent.putExtra("EXTRA_NAME", name)
            startActivity(intent)
            // }



        }
        binding.btnPerro.setOnClickListener {


            val intent = Intent(this, TrabajadorCrudActivity::class.java)
            // intent.putExtra("EXTRA_NAME", name)
            startActivity(intent)
            // }



        }
        binding.btnFlor.setOnClickListener {


            val intent = Intent(this, TrabajoCrudActivity::class.java)
            // intent.putExtra("EXTRA_NAME", name)
            startActivity(intent)
            // }



        }
        binding.btnVehiculo.setOnClickListener {


            val intent = Intent(this, VehiculoCrudActivity::class.java)
            // intent.putExtra("EXTRA_NAME", name)
            startActivity(intent)
            // }



        }
        binding.btnSalir.setOnClickListener {


            val intent = Intent(this, MainActivity::class.java)
            // intent.putExtra("EXTRA_NAME", name)
            startActivity(intent)
            // }



        }

    }
}