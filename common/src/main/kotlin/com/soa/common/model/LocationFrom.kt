package com.soa.common.model

import com.soa.common.util.Validator
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "location_from")
class LocationFrom(
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @NotNull
    var x: Float?,

    @NotNull
    var y: Float?,

    //Длина строки не должна быть больше 367, Поле может быть null
    @Size(max = 367, message = "Location name should be shorter than 367 characters")
    var name: String?
): Serializable {

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