package com.example.booknook

data class Utilisateurs(val id: Int, 
                  val nom: String,
                  val type: Boolean,
                  val livresFavoris: ArrayList<Livres> = ArrayList<Livres>()
                  )