package model

import util.Validator
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import kotlin.reflect.full.memberProperties

@Entity
@Table(name = "coordinates")
class Coordinates(
    //Максимальное значение поля: 327, Поле не может быть null
    @NotNull
    @Max(value = 327, message = "Coordinates x should be less than 327")
    var x: Float?,

    //Значение поля должно быть больше -863
    @Min(value = -863, message = "Coordinates y should be greater than -863")
    var y: Float?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordinates_generator")
    @SequenceGenerator(name = "coordinates_generator", sequenceName = "coordinates_seq")
    var id: Int = 1

    companion object {
        val allFields = listOf("x", "y", "id")
    }

    override fun toString(): String {
        return "Coordinates(x=$x, y=$y, id=$id)"
    }

    fun checkConstraints() {
        Validator.run {
            if (notNull(x, "coordinates.x")) max(x!!, "coordinates.x", 327f)
            if (notNull(y, "coordinates.y")) min(y!!, "coordinates.y", -863f)
        }
    }

    constructor(x: Float?, y: Float?, id: Int) : this(x, y) {
        this.id = id
    }
}