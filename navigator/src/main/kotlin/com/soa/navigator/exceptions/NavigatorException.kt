package com.soa.navigator.exceptions

class NavigatorException(message: String?, val code: Int = 500): Exception(message)