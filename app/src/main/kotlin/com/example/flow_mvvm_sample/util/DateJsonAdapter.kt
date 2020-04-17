package com.example.flow_mvvm_sample.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.text.SimpleDateFormat
import java.util.*

class DateJsonAdapter : JsonAdapter<Date>() {
    private val dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private val sdFormat = SimpleDateFormat(dateFormat, Locale.JAPAN)

    @Synchronized
    @Throws(Exception::class)
    override fun fromJson(reader: JsonReader): Date? {
        val string = reader.nextString()
        return sdFormat.parse(string)
    }

    @Synchronized
    @Throws(Exception::class)
    override fun toJson(writer: JsonWriter, value: Date?) {
        value ?: return
        writer.value(sdFormat.format(value))
    }

}