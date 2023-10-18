package be.christiano.portfolio.app.data.local.utils

import androidx.room.TypeConverter
import be.christiano.portfolio.app.domain.enums.TechStack
import io.ktor.serialization.kotlinx.json.DefaultJson
import kotlinx.serialization.serializer

class Converters {

    @TypeConverter
    fun convertTechStacksToString(techStacks: List<TechStack>) : String {
        val new = techStacks.toTypedArray()
        return DefaultJson.encodeToString(serializer(new::class.java), new)
    }

    @TypeConverter
    fun convertStringToTechStacks(value: String): List<TechStack> {
        return DefaultJson.decodeFromString(value)
    }
}