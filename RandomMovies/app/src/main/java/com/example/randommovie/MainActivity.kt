package com.example.randommovie

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var textMoviesView: TextView
    private lateinit var moviesBar: TextView
    private lateinit var movies: MutableList<String>
    private var countMovies = 0
    private var countShownMovies = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textMoviesView = findViewById(R.id.textMoviesView)
        moviesBar = findViewById(R.id.moviesBar)

        movies = resources.getStringArray(R.array.movies).toMutableList()
        countMovies = movies.size
    }

    @SuppressLint("SetTextI18n")
    fun test(view: View) {
        textMoviesView.text = if (movies.isEmpty()) {
            resources.getString(R.string.moviesOver)
        } else {
            val randomMovieIndex = Random.nextInt(movies.size)
            val movie = movies[randomMovieIndex]
            countShownMovies += 1
            movies.removeAt(randomMovieIndex)
            movie
        }

        updateMoviesBar()
    }

    @SuppressLint("SetTextI18n")
    fun reset(view: View) {
        movies = resources.getStringArray(R.array.movies).toMutableList()
        countShownMovies = 0
        textMoviesView.text = resources.getString(R.string.listShowMoviesReloaded)
        updateMoviesBar()
    }

    private fun updateMoviesBar() {
        moviesBar.text = resources.getString(R.string.listShowMoviesReloaded) +
                " ${countShownMovies.toString()}/${countMovies.toString()}"
    }
}
