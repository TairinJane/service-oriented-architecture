package com.soa.navigator.dto

import com.soa.common.model.LocationTo
import javax.xml.bind.annotation.*

@XmlRootElement(name = "locationTo")
@XmlAccessorType(XmlAccessType.FIELD)
class LocationToDTO(
    @XmlAttribute
    val id: Int,
    @XmlElement
    val x: Int,
    @XmlElement
    val y: Double,
    @XmlElement
    val z: Double,
)

fun LocationTo.toDTO() = LocationToDTO(
    id = id!!,
    x = x!!,
    y = y!!,
    z = z!!
)