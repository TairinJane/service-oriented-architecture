package com.soa.navigator.dto

import com.soa.common.model.LocationFrom
import javax.xml.bind.annotation.*

@XmlRootElement(name = "locationFrom")
@XmlAccessorType(XmlAccessType.FIELD)
class LocationFromDTO(
    @XmlAttribute
    val id: Int,
    @XmlElement
    val x: Float,
    @XmlElement
    val y: Float,
    @XmlElement
    val name: String
)

fun LocationFrom.toDTO() = LocationFromDTO(
    id = id!!,
    x = x!!,
    y = y!!,
    name = name!!
)