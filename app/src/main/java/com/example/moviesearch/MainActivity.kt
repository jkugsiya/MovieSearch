package com.example.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieText: EditText = findViewById(R.id.et_movie)
        val btnViewAll: Button = findViewById(R.id.btn_viewAll)
        val btnSearch: Button = findViewById(R.id.btn_search)
        val lv_movieList: ListView = findViewById(R.id.lv_movies)


        var db = DataBaseHelper(this)

        btnViewAll.setOnClickListener {
            var allMovies: List<MovieModel> = db.getAllMovies()

            var movieArrayAdapter: ArrayAdapter<MovieModel> = ArrayAdapter<MovieModel>(this, android.R.layout.simple_list_item_1, allMovies)
            lv_movieList.adapter = movieArrayAdapter
        }

        btnSearch.setOnClickListener {
            var searchedMovies: List<MovieModel> = db.searchMovie(movieText.text.toString())

            var movieArrayAdapter: ArrayAdapter<MovieModel> = ArrayAdapter<MovieModel>(this, android.R.layout.simple_list_item_1, searchedMovies)
            lv_movieList.adapter = movieArrayAdapter
        }

        if(getPreferences(MODE_PRIVATE).getBoolean("is_first_run", true)) {

            val movie1 = MovieModel(1, "Inception", "Leonardo", 2010, "Christopher Nolan")
            val movie2 =
                MovieModel(2, "Avatar: Special Edition", "Sam Worthington", 2010, "James Cameron")
            val movie3 = MovieModel(3, "Avatar", "Sam Worthington", 2009, "James Cameron")
            val movie4 = MovieModel(4, "The Father", "Olivia Colman", 2021, "Florian Zeller")
            val movie5 = MovieModel(5, "Parasite", "Kang Ho Song", 2020, "Bong Joon-Ho")
            val movie6 = MovieModel(6, "Titanic", "Leonardo", 1997, "James Cameron")

            db.addMovie(movie1)
            db.addMovie(movie2)
            db.addMovie(movie3)
            db.addMovie(movie4)
            db.addMovie(movie5)
            db.addMovie(movie6)
            getPreferences(MODE_PRIVATE).edit().putBoolean("is_first_run", false).commit()
        }

    }
}