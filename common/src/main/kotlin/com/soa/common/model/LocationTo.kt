package com.soa.common.model

import com.soa.common.util.Validator
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "location_to")
class LocationTo(
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_to_generator")
    @SequenceGenerator(name = "location_to_generator", sequenceName = "location_to_seq")
    var id: Int?,

    @NotNull
    var x: Int?,

    @NotNull
    var y: Double?,

    @NotNull
    var z: Double?,
) {

    companion object {
        val allFields = listOf("x", "y", "z", "id")
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

    fun ifAllNull(): Boolean {
        return x == null && y == null && z == null && id == null
    }
}