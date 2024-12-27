package com.example.booknook
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class RequeteMalFormuleeException(message: String? = null, cause: Throwable? = null) : RuntimeException(message, cause)

class BadRequestException(message: String) : Exception(message)
 {
}