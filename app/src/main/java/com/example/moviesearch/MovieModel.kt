package com.example.moviesearch

class MovieModel {
    var id: Int = -1;
    var name: String = ""
    var actor: String = ""
    var year: Int = -1
    var directorName: String = ""

    constructor(id: Int, name: String, actor: String, year: Int, directorName: String) {
        this.id = id
        this.name = name
        this.actor = actor
        this.year = year
        this.directorName = directorName
    }

    override fun toString(): String {
        return "$name\n" +
                "starring: $actor\n" +
                "Year of Release: $year\n" +
                "Director: $directorName"
    }


}