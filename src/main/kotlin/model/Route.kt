package model

import jakarta.validation.constraints.NotBlank
import java.util.*
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
class Route(
    //Поле не может быть null, Строка не может быть пустой
    @NotNull
    @NotBlank(message = "Route name should not be blank")
    var name: String,

    //Поле не может быть null
    @NotNull
    @Embedded
    var coordinates: Coordinates,

    //Поле может быть null
    @Embedded
    var from: Location,

    //Поле не может быть null
    @NotNull
    @Embedded
    var to: Location,

    //Значение поля должно быть больше 1
    @Min(value = 1, message = "Route distance should be greater than 1")
    var distance: Float
) {
    //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Id
    @GeneratedValue
    val id: Int = 1

    //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    val creationDate: Date = Date()
}