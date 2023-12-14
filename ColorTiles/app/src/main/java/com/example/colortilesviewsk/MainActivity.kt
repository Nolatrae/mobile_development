package com.example.colortilesviewsk

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.colortilesviewsk.databinding.ActivityMainBinding
import android.graphics.drawable.ColorDrawable
import kotlin.random.Random

data class Coordinate(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var colorTiles: List<List<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        colorTiles = List(4) { x ->
            List(4) { y ->
                binding.root.findViewWithTag<View>("$x$y")
            }
        }

        colorTiles.forEach { row ->
            row.forEach { view ->
                flipTileColor(view)
            }
        }
    }

    private fun flipTileColor(view: View) {
        val firstColor = ContextCompat.getColor(this, R.color.white)
        val secondColor = ContextCompat.getColor(this, R.color.black)

        val drawable = view.background as ColorDrawable
        view.setBackgroundColor(if (drawable.color == firstColor) secondColor else firstColor)
    }

    private fun extractCoordinateFromString(s: String): Coordinate {
        return Coordinate(s[0].toString().toInt(), s[1].toString().toInt())
    }

    private fun adjustColors(coordinate: Coordinate) {
        (0..3).forEach {
            flipTileColor(colorTiles[coordinate.x][it])
            flipTileColor(colorTiles[it][coordinate.y])
        }
    }

    fun onTileClick(v: View) {
        val coordinate = extractCoordinateFromString(v.tag.toString())
        flipTileColor(v)
        adjustColors(coordinate)

        if (checkForVictory()) {
            displayVictoryMessage("Победа!")
        }
    }

    private fun displayVictoryMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkForVictory(): Boolean {
        val firstColor = (colorTiles[0][0].background as ColorDrawable).color
        return colorTiles.all { row -> row.all { (it.background as ColorDrawable).color == firstColor } }
    }
}
