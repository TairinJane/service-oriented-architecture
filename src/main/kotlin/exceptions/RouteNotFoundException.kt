package com.example.exceptions

class RouteNotFoundException(routeId: Int): NoSuchElementException("No route with id = $routeId")