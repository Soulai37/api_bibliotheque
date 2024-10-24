package com.example.booknook

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/Livres")
class LivreController{
    @GetMapping
    fun obtenirLivres()= listOf(
    Livres("978-1234567890", "Les Misérables", "Victor Hugo", "Lutte contre l'injustice", "Édition spéciale", 5),
    Livres("978-0987654321", "Pierre et Jean", "Guy de Maupassant", "Dilemme familial", "Édition classique", 3),
    Livres("978-1122334455", "1984", "George Orwell", "Totalitarisme et surveillance", "Édition moderne", 10),
    Livres("978-2233445566", "Le Libraire", "Gérard Bessette", "Quête identitaire", "Édition québécoise", 4)
    )

    @GetMapping(params=["nom"])
    fun chercherLivres(@RequestParam nom: String)= 
        Livres("978-1234567890", nom, "Victor Hugo", "Lutte contre l'injustice", "Édition spéciale", 5)
        

    @GetMapping("/{isbn}")
    fun obtenirLivresParISBN(@PathVariable isbn: String) = 
    Livres(isbn, "Les Misérables", "Victor Hugo", "Lutte contre l'injustice", "Édition spéciale", 5)
    
    
    @PostMapping
    fun creerLivres(@RequestBody livre: Livres): ResponseEntity<Livres> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)

    @PutMapping("/{id}")
    fun modifierLivre(@RequestBody livre: Livres): ResponseEntity<Livres> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)

    @DeleteMapping("/{id}")
    fun supprimerLivre(@RequestBody livre: Livres): ResponseEntity<Livres> = ResponseEntity(HttpStatus.NOT_IMPLEMENTED)
}