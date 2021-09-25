package model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import kotlin.reflect.full.memberProperties

@Entity
class Coordinates(
    //Максимальное значение поля: 327, Поле не может быть null
    @NotNull
    @Max(value = 327, message = "Coordinates x should be less than 327")
    var x: Float = 0f,

    //Значение поля должно быть больше -863
    @Min(value = -863, message = "Coordinates y should be greater than -863")
    var y: Float = 0f
) {
    @Id
    @GeneratedValue
    val id: Int = 1

    val allFields
        get() = this::class.memberProperties.map { it.name }.filter { it != "id" }
}