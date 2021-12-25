package com.soa.navigator.dto

import com.soa.common.model.Coordinates
import javax.xml.bind.annotation.*

@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
class CoordinatesDTO(
    @XmlAttribute
    val id: Int,
    @XmlElement
    val x: Float,
    @XmlElement
    val y: Float
)

fun Coordinates.toDTO() = CoordinatesDTO(
    id = id!!,
    x = x!!,
    y = y!!
)