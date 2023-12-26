import java.text.SimpleDateFormat
import java.util.*

data class App(
    val name: String,
    val category: String,
    val rating: Double,
    val reviews: Int,
    val size: String,
    val installs: Int,
    val type: String,
    val price: Boolean,
    val contentRating: String,
    val genres: String,
    val lastUpdated: String,
    val currentVer: Int,
    val androidVer: Int
) {
    fun toJson(): Map<String, Any> = mapOf(
        "name" to name,
        "category" to category,
        "rating" to rating,
        "reviews" to reviews,
        "size" to size,
        "installs" to installs,
        "type" to type,
        "price" to price,
        "contentRating" to contentRating,
        "genres" to genres,
        "lastUpdated" to lastUpdated,
        "currentVer" to currentVer,
        "androidVer" to androidVer
    )

    companion object {
        fun fromCsvLine(line: String): App {
            val columns = line.parseCsvLine()
            return App(
                name = columns.getOrNull(0) ?: "",
                category = categoryTranslation[columns.getOrNull(1)] ?: "",
                rating = columns.getOrNull(2)?.toDoubleOrNull() ?: 0.0,
                reviews = columns.getOrNull(3)?.toIntOrNull() ?: 0,
                size = columns.getOrNull(4) ?: "",
                installs = columns.getOrNull(5)?.installsToNumber() ?: 0,
                type = columns.getOrNull(6) ?: "",
                price = columns.getOrNull(7)?.priceToBoolean() ?: false,
                contentRating = columns.getOrNull(8) ?: "",
                genres = columns.getOrNull(9) ?: "",
                lastUpdated = columns.getOrNull(10)?.convertDateToISO8601() ?: "",
                currentVer = columns.getOrNull(11)?.androidVersionToApi() ?: 1,
                androidVer = columns.getOrNull(12)?.androidVersionToApi() ?: 1
            )
        }
    }
}

fun String.parseCsvLine(): List<String> {
    val result = mutableListOf<String>()
    var currentColumn = StringBuilder()
    var insideQuotes = false

    for (char in this) {
        when {
            char == ',' && !insideQuotes -> {
                result.add(currentColumn.toString().trim())
                currentColumn = StringBuilder()
            }

            char == '"' -> insideQuotes = !insideQuotes
            else -> currentColumn.append(char)
        }
    }

    result.add(currentColumn.toString().trim())
    return result
}

fun String.androidVersionToApi(): Int {
    val versionRegex = """(\d+\.\d+)""".toRegex()
    val matchResult = versionRegex.find(this)

    return matchResult?.groupValues?.getOrNull(1)?.toDoubleOrNull()?.let {
        when {
            it in 1.0..1.5 -> 3
            it in 1.5..1.6 -> 4
            it in 2.0..2.1 -> 7
            it in 2.2..2.3 -> 8
            it in 2.3..3.0 -> 10
            it in 3.0..4.0 -> 11
            it in 4.0..4.1 -> 14
            it in 4.1..4.4 -> 16
            it in 4.4..5.0 -> 19
            it in 5.0..5.1 -> 21
            it in 6.0..7.0 -> 23
            it in 7.0..7.1 -> 24
            it in 8.0..8.1 -> 26
            it in 9.0..10.0 -> 28
            it in 10.0..11.0 -> 29
            it in 11.0..12.0 -> 31
            it in 13.0..14.0 -> 33
            it >= 14 -> 34
            else -> 1
        }
    } ?: 1
}

fun String.installsToNumber(): Int {
    val numberPattern = Regex("\\d+")
    val numberString = numberPattern.findAll(this).joinToString("") { it.value }
    return numberString.toIntOrNull() ?: 0
}

fun String.priceToBoolean(): Boolean = this.toFloatOrNull() == 0.0f

fun String.convertDateToISO8601(): String {
    val inputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}
