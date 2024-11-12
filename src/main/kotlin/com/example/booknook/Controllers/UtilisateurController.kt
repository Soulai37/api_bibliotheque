package com.example.booknook

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/utilisateurs")
class UtilisateurController (private val utilisateursService: UtilisateursService) {
    @GetMapping
    fun obtenirUtilisateurs(): ResponseEntity<List<Utilisateurs>> =
        ResponseEntity.ok(utilisateursService.obtenirUtilisateurs())
    /*fun obtenirUtilisateur()= listOf(
        Utilisateurs(2, "Vincent", false),
        Utilisateurs(3, "Marie", true),
        Utilisateurs(4, "Luc", false),
        Utilisateurs(5, "Sophie", true),


    )*/

    @GetMapping(params=["nom"])
    fun obtenirUtilisateurParNom(@RequestParam nom: String): ResponseEntity<Utilisateurs> =
        ResponseEntity.ok(utilisateursService.obtenirUtilisateurParNom(nom))
    /*fun chercherUtilisateur(@RequestParam nom: String) =
    Utilisateurs(2, nom, false)*/
       

    @GetMapping("/{id}")
    fun obtenirUtilisateurParId(@PathVariable id: Int): ResponseEntity<Utilisateurs> =
        ResponseEntity.ok(utilisateursService.obtenirUtilisateurParId(id))
    /*fun obtenirUtilisateurParID(@PathVariable id: Int) =
    Utilisateurs(id, "Vincent", false)*/
    
    
    @PostMapping
    fun creerUtilisateur(@RequestBody utilisateur: Utilisateurs): ResponseEntity<Utilisateurs> = 
        ResponseEntity.ok(utilisateursService.ajouterUtilisateur(utilisateur))

    @PutMapping("/{id}")
    fun modifierUtilisateur(@PathVariable id: Int, @RequestBody utilisateur: Utilisateurs): ResponseEntity<Utilisateurs> = 
        ResponseEntity.ok(utilisateursService.modifierUtilisateur(id, utilisateur))
        
    @DeleteMapping("/{id}")
    fun supprimerUtilisateur(@PathVariable id: Int): ResponseEntity<Void> {
        utilisateursService.supprimerUtilisateur(id)
        return ResponseEntity.noContent().build()
    }
}