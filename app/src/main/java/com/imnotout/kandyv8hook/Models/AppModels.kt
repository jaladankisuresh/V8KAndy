package com.imnotout.kandyv8hook.Models

import com.squareup.moshi.Json
import java.io.Serializable

interface IParsable {
    val path: String
}
data class Main(
    override val path: String,
    val name: String,
    val description: String,
    @Json(name = "img_path") val imgPath: String,
    val establishments: List<IEstablishment>,
    val count: Int
) : IParsable

data class Launcher (
      override val path: String,
      val label: String
) : IParsable

interface IEstablishment : IParsable, Serializable {
    override val path: String
    val name: String
    val type: String?
    val location: String
    val rating: Int
    val comments: List<Comment>?
}

class Establishment (
    override val path : String,
    override val name : String,
    override val type : String?,
    override val location : String,
    override val rating : Int,
    override val comments : List<Comment>? = null
) : IEstablishment

data class Comment (
        override val path: String,
        val id : Int,
        val text : String
) : IParsable

enum class EstablishmentType (val value: String) {
    HOTEL("Hotel"),
    RESTAURANT("Restaurant"),
    THEATRE("Theatre")
}

data class Hotel(
        val x : Establishment,
        val stars: Int,
        val roomsCount: Int,
        val isAttachedRestaurant: Boolean) : IEstablishment by x {

    data class Serializable(
        val path : String,
        val name : String,
        val type : String?,
        val location : String,
        val rating : Int,
        val comments : List<Comment>? = null,
        val stars: Int,
        val roomsCount: Int,
        val isAttachedRestaurant: Boolean )
//    constructor(
//        path : String,
//        name : String,
//        type : String,
//        location : String,
//        rating : Int,
//        comments : List<Comment>? = null,
//        stars: Int,
//        roomsCount: Int) : this(
//            Establishment(path, name, type, location, rating, comments), stars, roomsCount
//    )
}

data class Theatre(
        val x : Establishment,
        val screensCount: Int,
        val seatingCapacity: Int,
        val showsPerScreen: Int) : IEstablishment by x {

        data class Serializable(
            val path : String,
            val name : String,
            val type : String?,
            val location : String,
            val rating : Int,
            val comments : List<Comment>? = null,
            val screensCount: Int,
            val seatingCapacity: Int,
            val showsPerScreen: Int)
}

data class Restaurant(
        val x : Establishment,
        val cuisineType: String,
        val chefsCount : Int,
        val seatingCapacity: Int) : IEstablishment by x {

        data class Serializable(
            val path : String,
            val name : String,
            val type : String?,
            val location : String,
            val rating : Int,
            val comments : List<Comment>? = null,
            @Json(name = "cuisineType") val cuisineType: String,
            @Json(name = "chefsCount")val chefsCount : Int,
            @Json(name = "seatingCapacity")val seatingCapacity: Int)
}


