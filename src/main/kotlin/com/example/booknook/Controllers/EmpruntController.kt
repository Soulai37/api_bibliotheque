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
class EmpruntController{
    @GetMapping
    fun obtenirEmprunts()=listOf(
        Emprunt(1, Livres("978-1234567890", "Les Misérables", "Victor Hugo", 5), Utilisateurs(1, "Alice Dupont", false), LocalDate.of(2024, 1, 10), LocalDate.of(2024,2,10)),
        Emprunt(2, Livres("978-0987654321", "Pierre et Jean", "Guy de Maupassant", 3), Utilisateurs(2, "Jean Martin", false), LocalDate.of(2024,3,12), LocalDate.of(2024,4,12)),
        Emprunt(3, Livres("978-1122334455", "1984", "George Orwell", 10), Utilisateurs(3, "Sophie Bernard", false), LocalDate.of(2024,5,15), LocalDate.of(2024,6,15)),
        Emprunt(4, Livres("978-2233445566", "Le Librarire", "Gérard Bessette", 4), Utilisateurs(4, "David Leroy", true), LocalDate.of(2024,7,20), LocalDate.of(2024,8,20))             
    )
    @GetMapping("/{id}")
    fun chercherEmpruntsParId(@PathVariable id: Int)= listOf(
        Emprunt(id, Livres("978-1234567890", "Les Misérables", "Victor Hugo", 5), Utilisateurs(1, "Alice Dupont", false), LocalDate.of(2024, 1, 10), LocalDate.of(2024,2,10))   
    )
    @GetMapping(params=["nom"])
    fun chercherEmpruntsParNom(@RequestParam nom: String)= listOf(
        Emprunt(4, Livres("978-2233445566", "Le Librarire", "Gérard Bessette", 4), Utilisateurs(4, nom, true), LocalDate.of(2024,7,20), LocalDate.of(2024,8,20))
    )
    @PostMapping
    fun creerEmprunt(@RequestBody emprunt: Emprunt): ResponseEntity<Emprunt> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    @PutMapping("/{id}")
    fun modifierEmprunt(@RequestBody emprunt: Emprunt): ResponseEntity<Emprunt> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
    @DeleteMapping("/{id}")
    fun supprimerEmprunt(@RequestBody emprunt: Emprunt): ResponseEntity<Emprunt> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
}