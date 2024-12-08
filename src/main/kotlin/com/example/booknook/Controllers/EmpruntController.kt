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
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import java.time.LocalDate

@RestController
@RequestMapping("/emprunts")
class EmpruntController (private val empruntService: EmpruntService){
    @GetMapping
    fun obtenirEmprunts():ResponseEntity<List<Emprunt>> =
        ResponseEntity.ok(empruntService.obtenirEmprunts())

    @GetMapping("/{id}")
    fun chercherEmpruntsParId(@PathVariable id: Int): ResponseEntity<Emprunt>{
        return ResponseEntity.ok(empruntService.obtenirEmpruntParId(id))
    }
    @GetMapping(params=["nomLivre"])
    fun obtenirEmpruntParNomLivre(@RequestParam nomLivre: String): ResponseEntity<Emprunt> =
        ResponseEntity.ok(empruntService.obtenirEmpruntParNomLivre(nomLivre))

    @GetMapping(params=["nomUtilisateur"])
    fun obtenirEmpruntsParNomUtilisateur(@RequestParam nomUtilisateur: String):ResponseEntity<List<Emprunt>> =
        ResponseEntity.ok(empruntService.obtenirEmpruntParNomUtilisateur(nomUtilisateur))

    @PostMapping
    fun creerEmprunt(@RequestBody emprunt: Emprunt): ResponseEntity<Emprunt> = 
        ResponseEntity.ok(empruntService.ajouterEmprunt(emprunt))

    @PutMapping("/{id}")
    fun modifierEmprunt(@PathVariable id: Int, @RequestBody emprunt: Emprunt): ResponseEntity<Emprunt> {
        return ResponseEntity.ok(empruntService.modifierEmprunt(id, emprunt))
    }
    @DeleteMapping("/{id}")
    fun supprimerUtilisateur(@PathVariable id: Int): ResponseEntity<Void> {
        empruntService.supprimerEmprunt(id)
        return ResponseEntity.noContent().build()
    }
}