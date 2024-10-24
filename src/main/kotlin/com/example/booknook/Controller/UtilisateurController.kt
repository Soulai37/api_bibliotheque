package com.example.booknook

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/Utilisateurs")
class UtilisateurController{
    @GetMapping
    fun obtenirUtilisateur()= listOf(
        Utilisateurs(2, "Vincent", false),
        Utilisateurs(3, "Marie", true),
        Utilisateurs(4, "Luc", false),
        Utilisateurs(5, "Sophie", true),


    )

    @GetMapping(params=["nom"])
    fun chercherUtilisateur(@RequestParam nom: String) =
    Utilisateurs(2, nom, false)
       

    @GetMapping("/{id}")
    fun obtenirUtilisateurParID(@PathVariable id: Int) =
    Utilisateurs(id, "Vincent", false)
    
    
    @PostMapping
    fun creerUtilisateur(@RequestBody utilisateur: Utilisateurs): ResponseEntity<Utilisateurs> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
}