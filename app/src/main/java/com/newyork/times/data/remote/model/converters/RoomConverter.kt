package com.newyork.times.data.remote.model.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newyork.times.app.model.Media
import com.newyork.times.app.model.MediaMetaData
import com.newyork.times.app.model.URI
import java.util.*


class RoomObjectConverter{

    companion object {


        @JvmStatic
        @TypeConverter
        fun fromStringToMedia(value: String?): Media? {
            return Gson().fromJson(value, Media::class.java)
        }

        @JvmStatic
        @TypeConverter
        fun fromMediaToString(value: Media?): String? {
            return Gson().toJson(value)
        }


        @JvmStatic
        @TypeConverter
        fun fromStringToMediaMetaData(value: String?): MediaMetaData? {
            return Gson().fromJson(value, MediaMetaData::class.java)
        }

        @JvmStatic
        @TypeConverter
        fun fromMediaMetaDataToString(value: MediaMetaData?): String? {
            return Gson().toJson(value)
        }

        @JvmStatic
        @TypeConverter
        fun fromStringToURI(value: String?): URI? {
            return Gson().fromJson(value, URI::class.java)
        }

        @JvmStatic
        @TypeConverter
        fun fromURIToString(value: URI?): String? {
            return Gson().toJson(value)
        }


        @JvmStatic
        @TypeConverter
        fun storedStringToMediaList(data: String?): List<Media?>? {
            val gson = Gson()
            if (data == null) {
                return Collections.emptyList()
            }
            val listType =
                object : TypeToken<List<Media?>?>() {}.type
            return gson.fromJson<List<Media?>>(data, listType)
        }

        @JvmStatic
        @TypeConverter
        fun mediaListToStoredString(myObjects: List<Media?>?): String? {
            val gson = Gson()
            return gson.toJson(myObjects)
        }

    }

}