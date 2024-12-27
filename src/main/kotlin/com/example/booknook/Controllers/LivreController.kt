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
import com.example.booknook.LivreService

@RestController
@RequestMapping("/livres")
class LivreController(private val livreService: LivreService) {

    @GetMapping
    fun obtenirLivres(): ResponseEntity<List<Livres>> = ResponseEntity.ok(livreService.obtenirLivres()) 


    @GetMapping(params=["nom"])
    fun obtenirLivreParNom(@RequestParam nom: String): ResponseEntity<Livres> = 
        ResponseEntity.ok(livreService.obtenirLivreParNom(nom))

    @GetMapping(params=["genre"])
    fun obtenirLivreParGenre(@RequestParam genre: String): ResponseEntity<List<Livres>> = 
        ResponseEntity.ok(livreService.obtenirLivreParGenre(genre))



    @GetMapping("/{isbn}")
    fun obtenirLivreParIsbn(@PathVariable isbn: String): ResponseEntity<Livres> {
        return ResponseEntity.ok(livreService.obtenirLivreParIsbn(isbn))
            
    }
    
    
    @PostMapping
    fun creerLivre(@RequestBody livres: Livres): ResponseEntity<Livres>{
        return ResponseEntity.ok(livreService.ajouterLivre(livres))
    }


    @PutMapping("/{isbn}")
    fun modifierLivre(@PathVariable isbn: String, @RequestBody livres: Livres): ResponseEntity<Livres> {
        return ResponseEntity.ok(livreService.modifierLivres(isbn, livres))
    }
        
        
    @DeleteMapping("/{isbn}")
    fun supprimerLivre(@PathVariable isbn: String): ResponseEntity<Void> {
        livreService.supprimerLivres(isbn)
        return ResponseEntity.noContent().build()
    }
}