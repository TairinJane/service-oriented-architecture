package com.soa.routes.exceptions

class RouteNotFoundException(routeId: Int): NoSuchElementException("No route with id = $routeId")