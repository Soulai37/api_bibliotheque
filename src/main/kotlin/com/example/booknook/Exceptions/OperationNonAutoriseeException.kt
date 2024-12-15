package com.example.booknook

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

class OperationNonAutoriseeException(message: String? = null, cause: Throwable? = null) : ResponseStatusException(HttpStatus.FORBIDDEN, message, cause) {
}
