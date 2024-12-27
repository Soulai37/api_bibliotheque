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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt


@RestController
@RequestMapping("/utilisateurs")
class UtilisateurController (private val utilisateursService: UtilisateursService) {
    @GetMapping
    fun obtenirUtilisateurs(): ResponseEntity<List<Utilisateurs>> =
        ResponseEntity.ok(utilisateursService.obtenirUtilisateurs())

    @GetMapping(params=["nom"])
    fun obtenirUtilisateurParNom(@RequestParam nom: String): ResponseEntity<Utilisateurs> =
        ResponseEntity.ok(utilisateursService.obtenirUtilisateurParNom(nom))

    @GetMapping("/{id}")
    fun obtenirUtilisateurParId(@PathVariable id: Int, @AuthenticationPrincipal jeton: Jwt): ResponseEntity<Utilisateurs> {
        val nouvelUtilisateur = utilisateursService.obtenirUtilisateurParId(id, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nouvelUtilisateur!!.id)
                .toUri()
        return ResponseEntity.ok(nouvelUtilisateur)    
    }
    
    @PostMapping
    fun creerUtilisateur(@RequestBody utilisateur: Utilisateurs, @AuthenticationPrincipal jeton: Jwt): ResponseEntity<Utilisateurs> {
        val nouvelUtilisateur = utilisateursService.ajouterUtilisateur(utilisateur, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nouvelUtilisateur!!.id)
                .toUri()
        return ResponseEntity.created(uri).body(nouvelUtilisateur)

    } 
    @PutMapping("/{id}")
    fun modifierUtilisateur(@PathVariable id: Int, @RequestBody utilisateur: Utilisateurs, @AuthenticationPrincipal jeton: Jwt): ResponseEntity<Utilisateurs> {
        val nouvelUtilisateur = utilisateursService.modifierUtilisateur(id, utilisateur, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nouvelUtilisateur!!.id)
                .toUri()
        return ResponseEntity.ok(nouvelUtilisateur)
    }
        
        
    @DeleteMapping("/{id}")
    fun supprimerUtilisateur(@PathVariable id: Int): ResponseEntity<Void> {
        utilisateursService.supprimerUtilisateur(id)
        return ResponseEntity.noContent().build()
    }
}
