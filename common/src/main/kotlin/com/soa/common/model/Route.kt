package com.soa.common.model

import com.soa.common.util.Validator
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
@Table(name = "routes")
class Route(
    //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_generator")
    @SequenceGenerator(name = "route_generator", sequenceName = "route_seq")
    var id: Int?,

    //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @NotBlank(message = "Route name should not be blank")
    var name: String?,

    //Поле не может быть null
    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "coordinates_id", nullable = false)
    var coordinates: Coordinates?,

    //Поле может быть null
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "from_id")
    var from: LocationFrom?,

    //Поле не может быть null
    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "to_id", nullable = false)
    var to: LocationTo?,

    //Значение поля должно быть больше 1
    @NotNull
    @Min(value = 1, message = "Route distance should be greater than 1")
    var distance: Float?,

    //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false, updatable = false)
    var creationDate: Date?
): Serializable {

    fun checkConstraints() {
        Validator.run {
            notNull(name, "Name")
            notBlank(name!!, "Name")
            notNull(coordinates, "Coordinates")
            coordinates!!.checkConstraints()
            notNull(to, "To")
            to!!.checkConstraints()
            if (notNull(distance, "distance")) min(distance!!, "distance", 1f)
            from?.checkConstraints()
        }
    }

    fun ifAllNull(): Boolean {
        return name == null && (coordinates == null || coordinates?.ifAllNull() == true) &&
                (from == null || from?.ifAllNull() == true) && (to == null || to?.ifAllNull() == true) &&
                distance == null && id == null
    }

    companion object {

        val allFields = listOf(
            "name",
            "distance",
            "id"
        ) + Coordinates.allFields.map { "coordinates.$it" } + LocationFrom.allFields.map { "from.$it" } + LocationTo.allFields.map { "to.$it" }

    }

    override fun toString(): String {
        return "Route(name=$name, coordinates=$coordinates, from=$from, to=$to, distance=$distance, id=$id, creationDate=$creationDate)"
    }
}