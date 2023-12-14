package com.example.randomfilmapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject

data class Film(
    val title: String,
    val director: String,
    val year: Int,
    val genres: List<String>,
    val actors: List<String>
)

class MainActivity : AppCompatActivity() {

    private lateinit var FilmsList: List<Film>
    private lateinit var moviesArray: Array<String>
    private lateinit var textViewMovie: TextView
    private var usedMovies: MutableSet<String> = mutableSetOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FilmsList = loadFilmsFromJson()
        textViewMovie = findViewById(R.id.textViewMovie)

        val buttonShowMovie: Button = findViewById(R.id.buttonShowMovie)
        val buttonReset: Button = findViewById(R.id.buttonReset)

        buttonShowMovie.setOnClickListener { showRandomMovie() }
        buttonReset.setOnClickListener { resetMovies() }
    }

    private fun showRandomMovie() {
        if (usedMovies.size == FilmsList.size) {
            textViewMovie.text = "Все фильмы просмотрены. Сбросьте список."
            return
        }

        val availableMovies = FilmsList.filter { !usedMovies.contains(it.title) }
        if (availableMovies.isEmpty()) {
            return
        }

        val randomMovie = availableMovies.random()
        usedMovies.add(randomMovie.title)

        val movieInfo = filmData(randomMovie)
        textViewMovie.text = movieInfo
    }

    private fun filmData(movie: Film): String {
        return """
        Название: ${movie.title}
        Год: ${movie.year}
        Жанры: ${movie.genres.joinToString(", ")}
        Режиссер: ${movie.director}
        Актеры: ${movie.actors.joinToString(", ")}
        """.trimIndent()
    }

    private fun resetMovies() {
        usedMovies.clear()
        textViewMovie.text = ""
    }

    private fun loadFilmsFromJson(): List<Film> {
        val inputStream = resources.openRawResource(R.raw.films)
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val moviesList = mutableListOf<Film>()

        val jsonRoot = JSONObject(jsonString)
        val moviesArray = jsonRoot.getJSONArray("films")

        for (i in 0 until moviesArray.length()) {
            val movieObject = moviesArray.getJSONObject(i)
            val title = movieObject.getString("title")
            val year = movieObject.getInt("year")
            val genres = jsonToList(movieObject.getJSONArray("genres"))
            val director = movieObject.getString("director")
            val actors = jsonToList(movieObject.getJSONArray("actors"))
            val film = Film(title, director, year, genres, actors)
            moviesList.add(film)
        }

        return moviesList
    }

    private fun jsonToList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}
