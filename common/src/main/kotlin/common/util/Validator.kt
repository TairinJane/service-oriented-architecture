package common.util

class Validator {
    companion object {
        fun <T> notNull(field: T, fieldName: String): Boolean {
            if (field == null) throw IllegalArgumentException("Field '$fieldName' must be not null")
            return true
        }

        fun <T : Comparable<T>> min(field: T, fieldName: String, min: T): Boolean {
            if (field < min) throw IllegalArgumentException("Field '$fieldName' = $field must be greater than $min")
            return true
        }

        fun <T : Comparable<T>> max(field: T, fieldName: String, max: T): Boolean {
            if (field > max) throw IllegalArgumentException("Field '$fieldName' = $field must be less than $max")
            return true
        }

        fun notBlank(field: String, fieldName: String): Boolean {
            if (field.isBlank()) throw IllegalArgumentException("Field '$fieldName' must be not blank")
            return true
        }

        fun length(field: String, fieldName: String, min: Int = 0, max: Int?): Boolean {
            if (field.length < min) throw IllegalArgumentException("Field '$fieldName' must be longer than $min but was ${field.length} characters")
            if (max != null && field.length > max) throw IllegalArgumentException("Field '$fieldName' must be shorter than $max but was ${field.length} characters")
            return true
        }
    }
}