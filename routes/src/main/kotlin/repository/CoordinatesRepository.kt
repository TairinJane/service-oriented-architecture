package com.example.repository

import common.model.Coordinates
import org.springframework.data.repository.CrudRepository

interface CoordinatesRepository : CrudRepository<Coordinates, Int>