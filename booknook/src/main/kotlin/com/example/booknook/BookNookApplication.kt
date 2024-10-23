package com.example.booknook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookNookApplication

fun main(args: Array<String>) {
	runApplication<BookNookApplication>(*args)
}
