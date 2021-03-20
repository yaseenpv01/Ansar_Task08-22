package com.newyork.times.app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "article"
)
data class Article(

    @PrimaryKey
    @ColumnInfo(name ="id")
     var id: Long = 0,

    @ColumnInfo(name ="title")
     val title: String? = null,

    @ColumnInfo(name ="byline")
     val byline: String? = null,

    @ColumnInfo(name ="published_date")
     val published_date: String? = null,

    @ColumnInfo(name ="url")
     val url: String? = null,

    @SerializedName("abstract")
     val abstractt: String? = null,

    @ColumnInfo(name ="media")
     val media: List<Media>? = null

) : Serializable{

    companion object {
        const val TABLE_NAME = "article"
    }
}

data class Media(
    @SerializedName("media-metadata")
    var media_metadata: List<MediaMetaData>? ,

) : Serializable


data class MediaMetaData(
    @SerializedName("url")
    var url: String? = null,
): Serializable


data class URI(
    @SerializedName("url")
    val url: String? = null,
): Serializable
