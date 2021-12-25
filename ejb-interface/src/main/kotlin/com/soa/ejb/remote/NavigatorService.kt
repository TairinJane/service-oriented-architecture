package com.soa.ejb.remote

import com.soa.common.model.Route
import javax.ejb.Remote

@Remote
interface NavigatorService {
    fun findShortestRouteBetween(fromId: Int, toId: Int): Route?
    fun newRouteBetween(fromId: Int, toId: Int, distance: Float): Route
    fun ping(): String
}