package com.example.adapt_layout_orientation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.lifecycle.viewmodel.CreationExtras

class MainActivity : AppCompatActivity(){

    private lateinit var pictures: IntArray
    private lateinit var spinner: Spinner
    private lateinit var image: ImageView
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pictures = intArrayOf(R.drawable.squarecat, R.drawable.car1, R.drawable.car2, R.drawable.car3)
        spinner = findViewById<Spinner>(R.id.pictures_list)
        image = findViewById<ImageView>(R.id.picture)
        spinner.adapter = ArrayAdapter.createFromResource(this, R.array.pictures, R.layout.item)
        image.setImageResource(pictures[spinner.selectedItemPosition])
    }

    fun nextPictureClick(v: View){
        image.setImageResource(pictures[spinner.selectedItemPosition])
    }


    override fun onSaveInstanceState(outState: Bundle) {

        outState?.run {
            putInt("KEY", spinner.selectedItemPosition)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val position = savedInstanceState.getInt("KEY")
        image.setImageResource(pictures[position])
    }

}