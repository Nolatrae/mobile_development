package com.example.findnumber

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.findnumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    var minValue = 0
    var maxValue = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        minValue = intent.getIntExtra("min", 0)
        maxValue = intent.getIntExtra("max", 0)

        binding.textView.text = ((maxValue + minValue) / 2).toString()
    }

    fun onClick2(view: View)
    {
        if (maxValue - minValue <= 1)
        {
            binding.textView.text = "ПОБЭДАА $minValue"
            binding.textView.setTextColor(Color.GREEN)
        }
        else
        {
            if (view.id == R.id.button2)
            {
                maxValue = (maxValue + minValue) / 2
            }
            else if (view.id == R.id.button3)
            {
                minValue = (maxValue + minValue) / 2
            }
            binding.textView.text = "${((maxValue + minValue) / 2)}"
        }
    }
}
