package com.example.booknook

data class Livres( val isbn: String?, 
                  val nom: String,
                  val auteur: String,
                  val resume : String,
                  val edition : String,
                  val quantite: Int
                  )