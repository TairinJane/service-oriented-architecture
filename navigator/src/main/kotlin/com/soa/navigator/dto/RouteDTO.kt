package com.soa.navigator.dto

import com.soa.common.model.Route
import java.util.*
import javax.xml.bind.annotation.*

@XmlRootElement(name = "route")
@XmlAccessorType(XmlAccessType.FIELD)
class RouteDTO(
    @XmlAttribute
    val id: Int,
    @XmlElement
    val name: String,
    @XmlElement
    val coordinates: CoordinatesDTO,
    @XmlElement
    val from: LocationFromDTO?,
    @XmlElement
    val to: LocationToDTO,
    @XmlElement
    val distance: Float,
    @XmlElement
    val creationDate: Date
)

fun Route.toDTO() = RouteDTO(
    id = id!!,
    name = name!!,
    coordinates = coordinates!!.toDTO(),
    from = from!!.toDTO(),
    to = to!!.toDTO(),
    distance = distance!!,
    creationDate = creationDate!!
)