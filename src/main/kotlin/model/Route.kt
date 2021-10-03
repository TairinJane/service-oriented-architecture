package model

import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import util.Validator
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import kotlin.reflect.full.memberProperties

@Entity
@Table(name = "routes")
class Route(
    //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @NotBlank(message = "Route name should not be blank")
    var name: String,

    //Поле не может быть null
    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "coordinates_id", nullable = false)
    var coordinates: Coordinates,

    //Поле может быть null
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "from_id")
    var from: LocationFrom?,

    //Поле не может быть null
    @NotNull
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "to_id", nullable = false)
    var to: LocationTo,

    //Значение поля должно быть больше 1
    @NotNull
    @Min(value = 1, message = "Route distance should be greater than 1")
    var distance: Float
) {
    //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_generator")
    @SequenceGenerator(name = "route_generator", sequenceName = "route_seq")
    val id: Int = 1

    //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    val creationDate: Date? = Date()

    fun checkConstraints() {
        Validator.run {
            notNull(name, "Name")
            notBlank(name, "Name")
            notNull(coordinates, "Coordinates")
            coordinates.checkConstraints()
            notNull(to, "To")
            to.checkConstraints()
            if (notNull(distance, "distance")) min(distance, "distance", 1f)
            from?.checkConstraints()
        }
    }

    companion object {

        val allFields = listOf("name", "distance", "id") + Coordinates.allFields.map { "coordinates.$it" } + LocationFrom.allFields.map { "from.$it" } + LocationTo.allFields.map { "to.$it" }

    }

    override fun toString(): String {
        return "Route(name='$name', coordinates=$coordinates, from=$from, to=$to, distance=$distance, id=$id, creationDate=$creationDate)"
    }

}