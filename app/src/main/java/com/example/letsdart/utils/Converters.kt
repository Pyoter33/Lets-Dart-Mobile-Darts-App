package com.example.letsdart.utils

import androidx.room.TypeConverter
import com.example.letsdart.models.general.Rules
import com.example.letsdart.models.general.Statistics
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
@ExperimentalSerializationApi
class Converters {
    @TypeConverter
    fun fromRules(value: Rules) = Json.encodeToString(value)

    @TypeConverter
    fun toRules(value: String) = Json.decodeFromString<Rules>(value)

    @TypeConverter
    fun fromStatistics(value: Statistics) = Json.encodeToString(value)

    @TypeConverter
    fun toStatistics(value: String) = Json.decodeFromString<Statistics>(value)

}