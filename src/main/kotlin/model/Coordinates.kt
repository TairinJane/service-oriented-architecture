package model

import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Embeddable
class Coordinates(
    //Максимальное значение поля: 327, Поле не может быть null
    @NotNull
    @Max(value = 327, message = "Coordinates x should be less than 327")
    var x: Float = 0f,

    //Значение поля должно быть больше -863
    @Min(value = -863, message = "Coordinates y should be greater than -863")
    var y: Float = 0f
) {
    //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @Id
    @GeneratedValue
    var id: Int = 0
}