package model

import util.Validator
import javax.persistence.*
import javax.validation.constraints.NotNull
import kotlin.reflect.full.memberProperties

@Entity
@Table(name = "location_to")
class LocationTo(
    @NotNull
    var x: Int?,

    @NotNull
    var y: Double?,

    @NotNull
    var z: Double?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_to_generator")
    @SequenceGenerator(name = "location_to_generator", sequenceName = "location_to_seq")
    val id: Int = 1

    companion object {
        val allFields = listOf("x", "y", "z")
    }

    override fun toString(): String {
        return "LocationTo(x=$x, y=$y, z=$z, id=$id)"
    }

    fun checkConstraints() {
        Validator.run {
            notNull(x, "to.x")
            notNull(y, "to.y")
            notNull(z, "to.z")
        }
    }
}