package model

import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Embeddable
class Location(
    @NotNull
    var x: Float,

    @NotNull
    var y: Float,

    //Длина строки не должна быть больше 367, Поле может быть null
    @Size(max = 367, message = "Location name should be shorter than 367 characters")
    var name: String
) {
    @Id
    @GeneratedValue
    var id: Int = 0
}