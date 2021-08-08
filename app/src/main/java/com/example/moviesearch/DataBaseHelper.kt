package com.example.moviesearch

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(
    context: Context?,
    name: String? = "movies.db",
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1
) : SQLiteOpenHelper(context, name, factory, version) {

    public final val MOVIE_TABLE: String = "MOVIE_TABLE"
    public final val COLUMN_MOVIE_ID: String = "ID"
    public final val COLUMN_MOVIE_NAME: String = "NAME"
    public final val COLUMN_MOVIE_ACTOR: String = "ACTOR"
    public final val COLUMN_MOVIE_YEAR: String = "YEAR"
    public final val COLUMN_MOVIE_DIRECTOR: String = "DIRECTOR"

    override fun onCreate(db: SQLiteDatabase?) {
        var createTableStatement: String =
            "CREATE TABLE $MOVIE_TABLE ($COLUMN_MOVIE_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_MOVIE_NAME TEXT, $COLUMN_MOVIE_ACTOR TEXT, $COLUMN_MOVIE_YEAR INTEGER, $COLUMN_MOVIE_DIRECTOR TEXT)"

        db?.execSQL(createTableStatement)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addMovie(movieModel: MovieModel): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_MOVIE_NAME, movieModel.name)
        cv.put(COLUMN_MOVIE_ACTOR, movieModel.actor)
        cv.put(COLUMN_MOVIE_YEAR, movieModel.year)
        cv.put(COLUMN_MOVIE_DIRECTOR, movieModel.directorName)

        val insert: Long = db.insert(MOVIE_TABLE, null , cv)

        return !insert.equals(-1)
    }

    fun getAllMovies(): List<MovieModel> {
        val returnList: ArrayList<MovieModel> = ArrayList()

        val queryString: String = "SELECT * FROM $MOVIE_TABLE"
        val db: SQLiteDatabase = this.readableDatabase

        val cursor: Cursor =  db.rawQuery(queryString, null)

        if(cursor.moveToFirst()) {
            do {
                var movieId: Int = cursor.getInt(0)
                var movieName: String = cursor.getString(1)
                var movieActor: String = cursor.getString(2)
                var movieReleaseYear: Int = cursor.getInt(3)
                var movieDirector: String = cursor.getString(4)

                var movie: MovieModel = MovieModel(movieId, movieName, movieActor, movieReleaseYear, movieDirector)
                returnList.add(movie)
            } while(cursor.moveToNext())
        }
        else {

        }

        cursor.close()
        db.close()
        return returnList
    }

    fun searchMovie(searchString: String): List<MovieModel> {
        val returnList: ArrayList<MovieModel> = ArrayList()
        val queryString = "SELECT * FROM $MOVIE_TABLE" +
                " WHERE ($COLUMN_MOVIE_NAME LIKE '%$searchString%' OR" +
                " $COLUMN_MOVIE_DIRECTOR LIKE '%$searchString%' OR" +
                " $COLUMN_MOVIE_ACTOR LIKE '%$searchString%' OR" +
                " $COLUMN_MOVIE_YEAR LIKE '%$searchString%')"

        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery(queryString, null)
        if(cursor.moveToFirst()) {
            do {
                var movieId: Int = cursor.getInt(0)
                var movieName: String = cursor.getString(1)
                var movieActor: String = cursor.getString(2)
                var movieReleaseYear: Int = cursor.getInt(3)
                var movieDirector: String = cursor.getString(4)

                var movie: MovieModel = MovieModel(movieId, movieName, movieActor, movieReleaseYear, movieDirector)
                returnList.add(movie)
            } while(cursor.moveToNext())
        }
        else {
            var noMovie: MovieModel = MovieModel(-1, queryString, "N/A", -1, "N/A")
            returnList.add(noMovie)
        }
        cursor.close()
        db.close()
        return returnList
    }
}