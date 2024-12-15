package com.example.booknook

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import java.time.LocalDate
import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt

@RestController
@RequestMapping("/emprunts")
class EmpruntController (private val empruntService: EmpruntService){
    @GetMapping
    fun obtenirEmprunts():ResponseEntity<List<Emprunt>> =
        ResponseEntity.ok(empruntService.obtenirEmprunts())

    @GetMapping("/{id}")
    fun chercherEmpruntsParId(@PathVariable id: Int, @AuthenticationPrincipal jeton: Jwt): ResponseEntity<Emprunt>{
        val emprunt = empruntService.obtenirEmpruntParId(id, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprunt!!.id)
                .toUri()
        return ResponseEntity.created(uri).body(emprunt)
    }
    @GetMapping(params=["nomLivre"])
    fun obtenirEmpruntParNomLivre(@RequestParam nomLivre: String, @AuthenticationPrincipal jeton: Jwt): ResponseEntity<Emprunt> {
        val emprunt = empruntService.obtenirEmpruntParNomLivre(nomLivre, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprunt!!.id)
                .toUri()
        return ResponseEntity.created(uri).body(emprunt)
    }

    @GetMapping(params=["nomUtilisateur"])
    fun obtenirEmpruntsParNomUtilisateur(@RequestParam nomUtilisateur: String, @AuthenticationPrincipal jeton: Jwt):ResponseEntity<List<Emprunt>> {
        val emprunt = empruntService.obtenirEmpruntParNomUtilisateur(nomUtilisateur, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprunt[0].id)
                .toUri()
        return ResponseEntity.created(uri).body(emprunt)
    }
    @PostMapping
    fun creerEmprunt(@RequestBody emprunt: Emprunt): ResponseEntity<Emprunt> = 
        ResponseEntity.ok(empruntService.ajouterEmprunt(emprunt))

    @PutMapping("/{id}")
    fun modifierEmprunt(@PathVariable id: Int, @RequestBody emprunt: Emprunt, @AuthenticationPrincipal jeton: Jwt): ResponseEntity<Emprunt> {
        val emprunt = empruntService.modifierEmprunt(id, emprunt, jeton)
        val uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprunt!!.id)
                .toUri()
        return ResponseEntity.created(uri).body(emprunt)
    }
    @DeleteMapping("/{id}")
    fun supprimerUtilisateur(@PathVariable id: Int): ResponseEntity<Void> {
        empruntService.supprimerEmprunt(id)
        return ResponseEntity.noContent().build()
    }
}