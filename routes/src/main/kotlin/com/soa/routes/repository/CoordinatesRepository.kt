package com.soa.routes.repository

import com.soa.common.model.Coordinates
import org.springframework.data.repository.CrudRepository

interface CoordinatesRepository : CrudRepository<Coordinates, Int>