package com.example.booknook

import java.time.LocalDate

data class Emprunt(val id: Int,
                   val livre: Livres,
                   val utilisateur: Utilisateurs,
                   val date_emprunt: LocalDate,
                   val date_retour: LocalDate)