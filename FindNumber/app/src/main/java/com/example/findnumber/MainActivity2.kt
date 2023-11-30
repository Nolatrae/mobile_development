package com.example.findnumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.findnumber.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClick(view: View) {
        val minValue = binding.editTextNumberSigned.text.toString().toInt()
        val maxValue = binding.editTextNumberSigned2.text.toString().toInt()

        if (minValue >= maxValue)
        {
            Toast.makeText(this, "ай ай ай,у вас неправильно", Toast.LENGTH_LONG).show()
        }
        else
        {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("min", minValue)
            intent.putExtra("max", maxValue)
            startActivity(intent)
        }
    }
}
