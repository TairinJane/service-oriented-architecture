package model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import kotlin.reflect.full.memberProperties

@Entity
class Location(
    @NotNull
    var x: Float,

    @NotNull
    var y: Float,

    //Длина строки не должна быть больше 367, Поле может быть null
    @Size(max = 367, message = "Location name should be shorter than 367 characters")
    var name: String
) {
    @Id
    @GeneratedValue
    val id: Int = 1

    val allFields
        get() = this::class.memberProperties.map { it.name }.filter { it != "id" }
}