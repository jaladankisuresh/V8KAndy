package com.imnotout.kandyv8hook.NetworkIO

import com.imnotout.kandyv8hook.Models.*
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.FromJson

class JsonBuilder {
    companion object {
        val instance: Moshi by lazy {
            Moshi.Builder()
                .add(HotelJsonAdapter())
                .add(TheatreJsonAdapter())
                .add(RestaurantJsonAdapter())
                .add(RuntimeJsonAdapterFactory(IEstablishment::class.java, "type")
                        .registerSubtype(Hotel::class.java, EstablishmentType.HOTEL.value)
                        .registerSubtype(Restaurant::class.java, EstablishmentType.RESTAURANT.value)
                        .registerSubtype(Theatre::class.java, EstablishmentType.THEATRE.value)
                )
                /* Add the KotlinJsonAdapterFactory last to allow other installed Kotlin type factories to be used,
                since factories are called in order. */
                .add(KotlinJsonAdapterFactory())
                .build()
        }
    }
}
internal class HotelJsonAdapter {
    @FromJson
    fun hotelFromJson(hotelData: Hotel.Serializable): Hotel = hotelData.run {
        Hotel( Establishment(path, name, type, location, rating, comments), stars, roomsCount, isAttachedRestaurant )
    }
}
internal class TheatreJsonAdapter {
    @FromJson
    fun theatreFromJson(theatreData: Theatre.Serializable): Theatre = theatreData.run {
        Theatre( Establishment(path, name, type, location, rating, comments), screensCount, seatingCapacity,showsPerScreen )
    }
}
internal class RestaurantJsonAdapter {
    @FromJson
    fun restaurantFromJson(restaurantData: Restaurant.Serializable): Restaurant = restaurantData.run {
        Restaurant( Establishment(path, name, type, location, rating, comments), cuisineType, chefsCount, seatingCapacity )
    }
}


