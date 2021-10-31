package common.model

import common.util.Validator
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "location_from")
class LocationFrom(
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_from_generator")
    @SequenceGenerator(name = "location_from_generator", sequenceName = "location_from_seq")
    var id: Int?,

    @NotNull
    var x: Float?,

    @NotNull
    var y: Float?,

    //Длина строки не должна быть больше 367, Поле может быть null
    @Size(max = 367, message = "Location name should be shorter than 367 characters")
    var name: String?
) {

    companion object {
        val allFields = listOf("x", "y", "name", "id")
    }

    override fun toString(): String {
        return "LocationFrom(x=$x, y=$y, name='$name', id=$id)"
    }

    fun checkConstraints() {
        Validator.run {
            notNull(x, "from.x")
            notNull(y, "from.y")
            if (name != null) length(name!!, "from.name", max = 367)
        }
    }

    fun ifAllNull(): Boolean {
        return x == null && y == null && name == null && id == null
    }
}