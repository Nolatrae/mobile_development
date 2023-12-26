package com.example.randommoviewithdatainjson

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.InputStreamReader
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var movies: MutableList<Movie>
    private lateinit var oname: TextView
    private lateinit var oyear: TextView
    private lateinit var orating: TextView
    private lateinit var ocritic: TextView
    private lateinit var olanguage: Spinner
    private lateinit var oviewarea: Spinner

    private lateinit var name: TextView
    private lateinit var year: TextView
    private lateinit var rating: TextView
    private lateinit var critic: TextView
    private lateinit var language: TextView
    private lateinit var viewarea: TextView

    private lateinit var event: TextView

    override fun onStart() {
        super.onStart()
        Log.d("mytag", "onStart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("mytag", "onStop()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllViews()
        invisibilityViews()
        mix()

        val pref = getPreferences(MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("name", "Egor")
        editor.apply()
    }

    private fun getAllViews() {
        oname = findViewById(R.id.output_name)
        oyear = findViewById(R.id.output_year)
        orating = findViewById(R.id.output_rating)
        ocritic = findViewById(R.id.output_critics_rating)
        olanguage = findViewById(R.id.output_language)
        oviewarea = findViewById(R.id.output_view_area)
        name = findViewById(R.id.name)
        year = findViewById(R.id.year)
        rating = findViewById(R.id.rating)
        critic = findViewById(R.id.critics_rating)
        language = findViewById(R.id.language)
        viewarea = findViewById(R.id.view_area)
        event = findViewById(R.id.event)
        movies = getMovies(R.raw.movies)
    }

    private fun invisibilityViews() {
        setViewsVisibility(
            listOf(
                oname, oyear, orating,
                ocritic, olanguage, oviewarea,
                name, year, rating, critic,
                language, viewarea
            ),
            View.GONE
        )
    }

    private fun visibilityViews() {
        setViewsVisibility(
            listOf(
                oname, oyear, orating,
                ocritic, olanguage, oviewarea,
                name, year, rating, critic,
                language, viewarea
            ),
            View.VISIBLE
        )
    }

    private fun setViewsVisibility(views: List<View>, visibility: Int) {
        views.forEach { it.visibility = visibility }
    }

    private fun getMovies(id: Int): MutableList<Movie> {
        return try {
            val gson = Gson()
            gson.fromJson(
                InputStreamReader(resources.openRawResource(id)),
                Movies::class.java
            ).movies.toMutableList()
        } catch (e: Exception) {
            Log.e("mytag", "Error reading JSON file", e)
            mutableListOf()
        }
    }

    private fun mix() {
        var countMovie = movies.size
        val random = Random
        for (i in 0 until countMovie) {
            val r = random.nextInt(countMovie)
            val buf = movies[movies.size - i - 1]
            movies[movies.size - i - 1] = movies[r]
            movies[r] = buf
            countMovie -= 1
        }
    }

    fun onNextClick(view: View) {
        if (movies.isNotEmpty()) {
            visibilityViews()
            event.visibility = View.GONE

            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, movies[0].language)
                .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
            val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, movies[0].viewing_area)
                .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

            oname.text = movies[0].name
            oyear.text = movies[0].year.toString()
            orating.text = movies[0].rating.toString()
            ocritic.text = movies[0].critics_rating.toString()
            olanguage.adapter = adapter
            oviewarea.adapter = adapter1
            movies.removeAt(0)
        } else {
            invisibilityViews()
            event.visibility = View.VISIBLE
            event.text = "Конец списка!"
        }
    }

    fun onClearClick(view: View) {
        movies = getMovies(R.raw.movies)
        mix()
        invisibilityViews()
        event.visibility = View.VISIBLE
        event.text = "Обновлённый список!"
    }
}
