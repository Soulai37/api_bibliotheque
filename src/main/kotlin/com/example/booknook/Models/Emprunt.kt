package com.example.booknook

import java.util.Date

data class Emprunt(val id: Int,
                   val livre: Livres,
                   val utilisateur: Utilisateurs,
                   val date_emprunt: Date,
                   val date_retour: Date)