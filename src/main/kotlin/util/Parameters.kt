package util

enum class SortType {
    ASC, DESC
}

val ALLOWED_FIELDS = mapOf(
    "name" to true,
    "coordinates" to true,
    "location" to true,
    "distance" to true,
    "creationDate" to true,
    "sort" to false,
    "limit" to false,
    "offset" to false
)